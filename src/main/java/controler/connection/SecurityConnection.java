package controler.connection;

import io.socket.client.Ack;
import io.socket.emitter.Emitter;
import controler.message.MessageType;
import controler.message.send.MessageSendInfo;
import controler.message.send.MessageSendKey;
import org.json.JSONException;
import org.json.JSONObject;
import utils.StringGenerator;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

class SecurityConnection {

    private static PublicKey keyPubServ;
    private static SecretKey keySymetric;
    private static boolean secure = false;
    private static String ivString = new StringGenerator(16).nextString();
//    private static byte[] iv = ivString.getBytes();
    private static byte[] iv = "azertyuiopqsdfgh".getBytes();
    private static IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

    SecurityConnection() {
        System.out.println("ivString: "+ivString);
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(256);
            keySymetric = generator.generateKey();
            System.out.println("Key size: "+keySymetric.getEncoded().length*8);
            System.out.println("Sym key: "+new String(Base64.getEncoder().encode(keySymetric.getEncoded())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    void securiseSocket(SocketIO socket){
        MessageSendInfo infoMsg = new MessageSendInfo();
        System.out.println("Asking public key");
        socket.emitNonSecure(MessageType.KEY_ASK, null);
        System.out.println("Waiting public key...");
        socket.once(MessageType.KEY_RECEIVE.getEvent(), new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    System.out.println("Public key reveived");
                    setSecureServKey(new JSONObject((String)args[0]));
                    MessageSendKey keySend = new MessageSendKey(new String(Base64.getEncoder().encode(keySymetric.getEncoded())), ivString);
                    System.out.println("Sending symetrical key");
                    socket.emitAck(MessageType.KEY_SEND, keySend, new Ack() {
                        @Override
                        public void call(Object... args) {
                            if (args[0].equals("ok")){
//                                System.out.println("KCA");
                                secure = true;
                                System.out.println("Sending user information");
                                socket.emit(MessageType.SEND_INFO, infoMsg);
                            }else{
                                System.err.println(args[0]);
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setSecureServKey(JSONObject keyPub){
        try {
            String key = keyPub.getString("key");
            key = key.replaceAll("(-+BEGIN PUBLIC KEY-+\\s?\\n|-+END PUBLIC KEY-+|\\n)","");
//            System.out.println("serv pub key: "+key);
            byte[] rsaKey = Base64.getDecoder().decode(key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(rsaKey);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            keyPubServ = factory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | JSONException e) {
            e.printStackTrace();
        }
    }
    private byte[] encryptSym(String message){
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySymetric, ivParameterSpec);
            return cipher.doFinal(message.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    byte[] encryptMessage(String message){
        if (!isComplete())
            return encryptWithServKey(message.getBytes());
        return encryptSym(message);
    }

    private byte[] encryptWithServKey(byte[] bytes){
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keyPubServ);
//            System.out.println("message send: "+new String(bytes));
//            System.out.println("message crypt send: "+new String(Base64.getEncoder().encode(cipher.doFinal(bytes))));
            return cipher.doFinal(bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isComplete(){
        return keySymetric != null && keyPubServ != null && secure;
    }

    String decrypt(String message){
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySymetric, ivParameterSpec);
            String decrypted = new String(cipher.doFinal(Base64.getDecoder().decode(message)));
            System.out.println(decrypted);
            return decrypted;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return null;
        }
    }
}

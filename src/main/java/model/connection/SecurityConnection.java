package model.connection;

import io.socket.emitter.Emitter;
import model.message.send.MessageAskKey;
import model.message.send.MessageSendKey;
import model.message.MessageType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

class SecurityConnection {

    private static PublicKey keyPubServ;
    private static SecretKey keySymetric;



    SecurityConnection() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(128);
            keySymetric = generator.generateKey();
            System.out.println("Clé symétrique: "+new String(Base64.getEncoder().encode(keySymetric.getEncoded())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    void securiseSocket(SocketIO socket){
        MessageAskKey keyAsk = new MessageAskKey();
        socket.emit(MessageType.KEY_ASK, keyAsk);
        socket.once(MessageType.KEY_RECEIVE.getEvent(), new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    setSecureServKey(new JSONObject((String)args[0]));
                    MessageSendKey keySend = new MessageSendKey();
                    socket.emit(MessageType.KEY_SEND, keySend);
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
            System.out.println("clé pub serveur = "+key);
            byte[] rsaKey = Base64.getDecoder().decode(key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(rsaKey);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            keyPubServ = factory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | JSONException e) {
            e.printStackTrace();
        }
    }

    String encryptMessage(String message){
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keyPubServ);
            byte[] kbyte = cipher.doFinal(keySymetric.getEncoded());
            String a = new String(kbyte);
            String temp = "";
            JSONArray array = new JSONArray(kbyte);
            JSONObject object = new JSONObject();
            object.put("tab", array);
            return object.toString();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    boolean isComplete(){
        return keySymetric != null && keyPubServ != null;
    }
}

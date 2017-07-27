package controler.connection;

import org.json.JSONException;
import org.json.JSONObject;
import sun.security.rsa.RSAKeyPairGenerator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SecurityConnection {

    private static KeyPair keyClient;
    private static PublicKey keyPubServ;
    private static SecurityConnection instance = null;

    public static SecurityConnection getInstance(){
        if (instance == null)
            instance = new SecurityConnection();
        return instance;
    }

    private SecurityConnection() {
        RSAKeyPairGenerator rsaKeyPairGenerator = new RSAKeyPairGenerator();
        rsaKeyPairGenerator.initialize(2048, new SecureRandom());
        keyClient = rsaKeyPairGenerator.generateKeyPair();
    }

    public void setSecureServKey(JSONObject keyPub){
        try {
            String key = keyPub.getString("key");
            key = key.replaceAll("(-+BEGIN PUBLIC KEY-+\\s?\\n|-+END PUBLIC KEY-+|\\n)","");
            System.out.printf(key);
            byte[] rsaKey = Base64.getDecoder().decode(key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(rsaKey);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            keyPubServ = factory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | JSONException e) {
            e.printStackTrace();
        }
    }

    public String encryptMessage(String message){
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keyPubServ);
            return new String(cipher.doFinal(message.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "";
    }
}

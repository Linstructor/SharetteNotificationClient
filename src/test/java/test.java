import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Created by tristan on 25/07/17.
 */
public class test {
    public static void main(String[] args) {
        try {
            String key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmP7I4/924NRmZvgzPq7L" +
                    "a7ILs+IXvjH7cILJACdAP1UhpnUDbXMOGnrg60PoaHn6yZqeIn0WaWKnRrX2iThS" +
                    "YwNiPcQbWN9Ufjpd1BffK3CmPDy+8t1I/tRn8Hr8pejiu7Wpq8mepQBnKM420Etj" +
                    "+G3PCsuJj8slQnzFcYceedJmh7OOsE7WquoY7RUGXI1dvlc8cx19bD75AHNy3gHW" +
                    "W3z9dCQvCdbG86m5gEbBXXijSjXUeoIs53dn0fYcYW6JOYnO8qzEHIjTcDNp3Epf" +
                    "ztqqa9ViIoLMhmTF0SSycMeIXPpyBDT1lfRqpLsHQO1yeXcCrEJXcNiif8azjoBL" +
                    "sQIDAS/R";

//            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
//            generator.initialize(2048);
//            KeyPair rsaKey = generator.genKeyPair();
//            System.out.println(new String(Base64.getEncoder().encode(rsaKey.getPublic().getEncoded())));

            byte[] rsaKey = Base64.getDecoder().decode(key);
            System.out.println(new String(rsaKey));

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(rsaKey);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            System.out.println(new String(Base64.getEncoder().encode(factory.generatePublic(keySpec).getEncoded())));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}

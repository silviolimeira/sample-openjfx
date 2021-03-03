import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

// refs
// https://www.baeldung.com/java-aes-encryption-decryption

// compile
// javac Encode.java
// execute class
// java Encode password salt

public class Encode {

    public static void main(String[] args) {
        System.out.println("Encode password salt");
        try {
            SecretKey secret = getKeyFromPassword(args[0], args[1]);
            final byte[] encoded = secret.getEncoded();
            String base64 = Base64.getEncoder().encodeToString(encoded);
            System.out.println(base64);
            System.out.println(decrypt(args[0], args[1], args[2]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SecretKey getKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 64);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");

        return secret;
    }

    public static String decrypt(String password, String salt, String property) throws GeneralSecurityException, IOException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");

        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 64));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(salt.getBytes(), 64));
        return new String(pbeCipher.doFinal(Base64.getDecoder().decode(property)), "UTF-8");
    }

}

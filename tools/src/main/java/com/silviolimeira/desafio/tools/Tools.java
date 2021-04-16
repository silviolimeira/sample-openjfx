package com.silviolimeira.desafio.tools;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tools {

    private final static Logger LOGGER = Logger.getLogger(Tools.class.getName());

    public void hello() {
        //System.out.println("Hello");
        LOGGER.log(Level.INFO, "Hello");
    }

    public static SecretKey getKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 64);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
        return secret;
    }

    public static void main(String[] args) {
        try {
            SecretKey secret = getKeyFromPassword("y6m9d9q9", "Ceswovdj@74@2021");
            final byte[] encoded = secret.getEncoded();
            String base64 = Base64.getEncoder().encodeToString(encoded);
            System.out.println(base64);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

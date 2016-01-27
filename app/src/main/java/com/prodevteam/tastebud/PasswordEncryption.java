package com.prodevteam.tastebud;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Yazin Yousif on 27-Jan-16.
 */

public class PasswordEncryption {

    public static String ENCRYPT(String password) {

        try {
            /*
            In cryptography, MD5 (Message-Digest algorithm 5) is a widely-used cryptographic hash
            function with a 128-bit hash value. As an Internet standard, MD5 has been employed in a
            wide variety of security applications.
             */

            MessageDigest MD = MessageDigest.getInstance("MD5");
            byte[] passwordBytes= password.getBytes();

            MD.reset();
            byte[] digested = MD.digest(passwordBytes);

            StringBuffer encryptedPassword = new StringBuffer();
            for (int i = 0; i < digested.length; i++) {
                encryptedPassword.append(Integer.toHexString(0xff & digested[i]));
            }

            return encryptedPassword.toString().toLowerCase();

        } catch (NoSuchAlgorithmException ex) {
            return null;

        }
    }
}

package com.prodevteam.tastebud;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Yazin Yousif on 27-Jan-16.
 */

public class PasswordEncryption {

    public static String ENCRYPT(String PASSWORD) {

        try {

            MessageDigest MD = MessageDigest.getInstance("MD5");
            byte[] passwordBytes= PASSWORD.getBytes();

            MD.reset();
            byte[] digested = MD.digest(passwordBytes);

            StringBuffer ENCRYPTED_PASSWORD = new StringBuffer();
            for (int i = 0; i < digested.length; i++) {
                ENCRYPTED_PASSWORD.append(Integer.toHexString(0xff & digested[i]));
            }

            return ENCRYPTED_PASSWORD.toString();

        } catch (NoSuchAlgorithmException ex) {
            return null;

        }
    }
}

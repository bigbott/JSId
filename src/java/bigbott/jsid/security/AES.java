/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 *
 * @author Owner
 */
public class AES {
    private static String algorithm = "AES";
    private static Key key;
    private static KeyGenerator keyGenerator;
    private static Cipher cipher;

    public static String encrypt(String jwt) throws Exception {
        if (key == null || cipher == null) {
            setUp();
        }
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(jwt.getBytes("UTF-8")));
    }

    private static void setUp() {
        try {
            cipher = Cipher.getInstance(algorithm);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (keyGenerator != null) {
            key = keyGenerator.generateKey();
            return;
        }
        try {
            keyGenerator = KeyGenerator.getInstance(algorithm);
            keyGenerator.init(128);
            key = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String decrypt(String encryptedJWT) throws Exception {
        if (key == null || cipher == null) {
            setUp();
        }
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedJWT)));
    }

    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 5; i++) {
            //           String encrypted = encrypt("{\"expires\": \"1590856832128\", \"csrf\": \""+RandomStringGenerator.generate(5)+"\", \"data\": \"1\"}");
            String json = "{\"a\": \"" + 6 + "\"}";
            System.out.println(json);
            String encrypted = encrypt(json);
            //String encrypted = encrypt("{\"a\": \""+RandomStringGenerator.generate(7)+"\"}");
//            String encrypted = encrypt("{\"id\": \"1\", \"csrf\": "+RandomStringGenerator.generate(5)+"}");
//            {"expires":"1590856832128","csrf":"hmvqc","data":"1"}
            System.out.println(encrypted);
            System.out.println(System.currentTimeMillis());
            System.out.println("decrypt 1000 times");
            for (int j = 0; j < 1000; j++) {
                //System.out.println(decrypt(encrypted));
                decrypt(encrypted);
            }
            System.out.println(System.currentTimeMillis());
        }

    }

}

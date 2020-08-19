/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aes256app;

/**
 *
 * @author Haris Tanwir
 */
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.apache.logging.log4j.Logger;

public class AES256App {

    /**
     * @param args the command line arguments
     */
    private static String password = "Pr0d@d0b37@Rg371N7eR@c71234)(*&^";
    private static String salt = "4492e63385745158";

    public static void main(String[] args) {

        Logger logger = FlowLogger.getLogger(AES256App.class.getName());

        String originalString = "97430355952";
        String encryptedString = AES256App.encrypt(originalString, password, salt);
        logger.info(encryptedString);
        String decryptedString = AES256App.decrypt(encryptedString, password, salt);
        logger.info(decryptedString);
    }

    public static String encrypt(String strToEncrypt, String password, String salt) {
        try {
            byte[] iv = salt.getBytes("UTF-8");
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeySpec secretKey = new SecretKeySpec(password.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return DatatypeConverter.printBase64Binary(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String password, String salt) {
        try {
            byte[] iv = salt.getBytes("UTF-8");
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeySpec secretKey = new SecretKeySpec(password.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}

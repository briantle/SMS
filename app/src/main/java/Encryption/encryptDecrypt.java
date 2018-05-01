package Encryption;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class encryptDecrypt {
    public static byte[] encryptSMS(String secretKey, String message)
    {
        try
        {
           byte[] returnArray;

           Key key = generateKey(secretKey);
           Cipher cipher = Cipher.getInstance("AES");


           cipher.init(Cipher.ENCRYPT_MODE, key);

           returnArray = cipher.doFinal(message.getBytes());
           return returnArray;
        } catch (Exception e) {
            e.printStackTrace();
            byte[] returnArray = null;
            return returnArray;
        }
    }

    public static byte[] decrypt(String secretKey, byte[] encryptedSMS)
        throws Exception
    {
        Key key = generateKey(secretKey);
        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.DECRYPT_MODE,key);

        byte[] decValue = cipher.doFinal(encryptedSMS);
        return decValue;
    }

    private static Key generateKey(String secretKey) throws Exception
    {
        Key key = new SecretKeySpec(secretKey.getBytes(),"AES");
        return key;

    }
}

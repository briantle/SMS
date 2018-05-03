package com.sms.activities;

import org.junit.Test;

import static Encryption.encryptDecrypt.decrypt;
import static Encryption.encryptDecrypt.encryptSMS;
import static junit.framework.TestCase.fail;
import static com.sms.activities.MessageActivity.byte2hex;
import static com.sms.activities.ReceiveMessage.hex2byte;

public class DecryptTest {
    @Test
    public void decrypt_isWorking() throws Exception{

        String key = "1234123412341234";

        String testStr = "Is this working?";
        System.out.println("String: " + testStr);

        String encryptedStr = encryptSMS(key, testStr).toString();
        System.out.println("Encrypted String: " + encryptedStr);
        String encryptedTester = byte2hex(encryptSMS(key, testStr));
        byte[] decryptedTester = hex2byte(encryptedTester.getBytes());
        String decryptedStr = new String(decrypt(key, decryptedTester));
        System.out.println("Decrypted String: " + decryptedStr);

        if (!decryptedStr.equals(testStr))
            fail();
    }
}
package com.sms.activities;

import org.junit.Test;
import static Encryption.encryptDecrypt.encryptSMS;
import static junit.framework.TestCase.fail;


public class EncryptTest {
    @Test
    public void encrypt_isWorking() throws Exception{
        String testStr = "Is this working?";
        if (encryptSMS("1234123412341234", testStr).toString().equals(testStr))
            fail();
    }
}

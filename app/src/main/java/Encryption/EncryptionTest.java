package Encryption;

public class EncryptionTest {

    public static void main(String[] args) {

        String str = "Hello World! This is a test string.";
        //System.out.println(str);

        String encryptStr = Encryption.encrypt(str);
        //System.out.println(encryptStr);

        String decryptStr = Encryption.decrypt(encryptStr);
        //System.out.println(decryptStr);

        Writer.write("Original string: " + str + "\n");
        Writer.write("Encrypted string: " + encryptStr + "\n");
        Writer.write("Decrypted string: " + decryptStr + "\n");
    }
}

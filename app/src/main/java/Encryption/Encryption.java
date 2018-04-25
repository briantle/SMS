package Encryption;


public class Encryption {

    private static final int[] chain = { 2342, 432, 4324, 421, 434, 43, 256, 456, 345};
    public static String encrypt(String key)
    {
        int i = 0;
        String result = "";
        int len = key.length();

        char ch;
        int ck = 0;
        for(i = 0; i < len; i++)
        {
            if(ck >= chain.length - 1)
                ck = 0;
            ch = key.charAt(i);
            ch += chain[ck];
            result += ch;
            ck++;
        }
        return result;
    }


    public static String decrypt(String key)
    {
        int i = 0;
        String result = "";
        int len = key.length();

        char ch;
        for(i = 0; i < len; i++)
        {
            if(ck >= chain.length - 1)
                ck = 0;
            ch = key.charAt(i);
            ch -= chain[ck];
            result += ch;
            ck++;
        }
        return result;
}

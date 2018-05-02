package users;

import java.util.Random;

public class generateUser {
    // variables used to generate a unique numeric username

    private static final long LIMIT = 10000000000L;
    private static long last = 0;

    // list of characters usable in a password

    private static final String symbols =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$&@?<>~!%#";

    // function used to generate a password

    public static String genPassword(Random r) {
        while(true) {
            // generate a password between 16 and 24 characters long
            char[] password = new char[r.nextBoolean()?16:24];

            // creates booleans to keep track of password requirements
            boolean upper = false, lower = false, digit = false, special = false;

            // loop through generated passwords to check requirements
            for(int i=0; i<password.length; i++) {
                char ch = symbols.charAt(r.nextInt(symbols.length()));
                if(Character.isUpperCase(ch))
                    upper = true;
                else if(Character.isLowerCase(ch))
                    lower = true;
                else if(Character.isDigit(ch))
                    digit = true;
                else
                    special = true;
                password[i] = ch;
            }

            // if all requirements have been met then return password
            if(upper && lower && digit && special) {
                return new String(password);
            }
        }
    }

    // generates a unique numeric id using the current time in milliseconds
    public static long getID() {
        // 10 digits.
        long id = System.currentTimeMillis() % LIMIT;
        if ( id <= last ) {
            id = (last + 1) % LIMIT;
        }
        return last = id;
    }
}

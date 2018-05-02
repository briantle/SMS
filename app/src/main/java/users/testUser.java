package users;

import java.util.Random;

public class testUser {

    public static void main(String[] args) {

        // tests generateUser by generating 5 usernames and passwords
        for (int i=0; i<5; i++) {
            Random rand = new Random();
            long user = generateUser.getID();
            String pass = generateUser.genPassword(rand);
            System.out.println(user + "\n" + pass);
        }
    }
}

package Encryption;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public static void write(String input){

        try {
            // just to test if functions work
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("output")));

            writer.write(input);
            writer.flush();
            writer.close();
            System.out.println("Done!");

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

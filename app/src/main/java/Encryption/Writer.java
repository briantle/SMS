package Encryption;

public class Writer {
    public static void write(String input){
        try{
            //just to test if functions work
            BufferWriter writer = new BufferWriter(new FileWriter(new File("output")));

            writer.write(input);
            writer.flush();
            writer.close();
            System.out.println("Done!");

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

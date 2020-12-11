import java.io.*;

public class Main {
    public static void main(String[] args) {
        Parser parser;
        try {
            parser = new Parser(args[0]);
            parser.readAndParse();
            parser.getOutput();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
        }
    }
}

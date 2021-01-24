import java.io.*;

public class Main {
    public static void main(String[] args) {
        try (Parser parser = new Parser(
                new InputStreamReader(new FileInputStream(args[0])),
                new OutputStreamWriter(new FileOutputStream(args[1])))) {
            parser.readInput();
            parser.writeSorted();
        } catch (Exception e) {
            System.out.println("An error has occured: " + e.getLocalizedMessage());
        }
    }
}

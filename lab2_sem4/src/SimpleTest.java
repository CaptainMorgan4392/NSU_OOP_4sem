import Exceptions.WrongTestResultException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleTest {
    static Logger newLogger;

    static {
        try (FileInputStream input = new FileInputStream("src/logger.config")) {
            LogManager.getLogManager().readConfiguration(input);
            newLogger = Logger.getLogger(SimpleTest.class.getName());

            PrintWriter clearFile = new PrintWriter(new FileWriter("log.txt"));
            clearFile.write("");
            clearFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public static void test() throws Exception {
        try {
            WorkflowExecutor executor = new WorkflowExecutor("src/workflowTest.txt");
            executor.start();

            ArrayList <String> wordsInOutFile = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("testOut.txt"));

            while (reader.ready()) {
                wordsInOutFile.add(reader.readLine());
            }

            if (!(wordsInOutFile.size() == 1 && wordsInOutFile.get(0).equals("bab"))) {
                newLogger.log(Level.INFO, "Test failed :(");
                throw new WrongTestResultException();
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Test
    public static void runAll() throws Exception {
        newLogger.log(Level.INFO, "Running test...");

        test();

        newLogger.log(Level.INFO, "Test passed successfully!");
    }
}
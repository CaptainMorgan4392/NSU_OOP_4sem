import Exceptions.WorkflowException;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            SimpleTest.runAll();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

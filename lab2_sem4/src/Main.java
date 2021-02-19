import Exceptions.WorkflowException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            WorkflowExecutor.start();
        } catch (WorkflowException |
                NoSuchMethodException |
                IllegalAccessException |
                InvocationTargetException |
                InstantiationException |
                ClassNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
    }
}

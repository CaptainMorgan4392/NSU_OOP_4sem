import Blocks.WorkflowBlock;
import Exceptions.WorkflowException;
import Factory.WorkflowBlockFactory;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class WorkflowExecutor {
    private static ArrayList <String> text = new ArrayList<>();

    private WorkflowExecutor() {}

    public static void start() throws WorkflowException,
                                        NoSuchMethodException,
                                            IllegalAccessException,
                                                InvocationTargetException,
                                                    InstantiationException,
                                                        ClassNotFoundException,
                                                            IOException
    {
        WorkflowBlockFactory factory = new WorkflowBlockFactory();
        Class clazz = Class.forName(String.valueOf(factory.getBlockByID()));
        WorkflowBlock block = (WorkflowBlock)clazz.getDeclaredConstructor().newInstance();
        block.execute(factory.getCurArgsToExecute(), text);
    }
}

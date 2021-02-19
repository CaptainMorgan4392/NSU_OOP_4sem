package Factory;

import Blocks.WorkflowBlock;

import java.io.IOException;
import java.util.Properties;

public class WorkflowBlockFactory {
    private static Properties config = new Properties();

    private WorkflowBlockFactory() throws IOException {
        var configStream = WorkflowBlockFactory.class.getResourceAsStream("config.properties");
        if (configStream == null) {
            throw new IOException();
        }

        config.load(configStream);
    }

    private static volatile WorkflowBlockFactory instance;

    public static WorkflowBlockFactory getInstance() throws IOException {
        if (instance == null) {
            synchronized (WorkflowBlockFactory.class) {
                if (instance == null) {
                    instance = new WorkflowBlockFactory();
                }
            }
        }

        return instance;
    }

    public WorkflowBlock getBlock(String name) throws Exception {
        if (!config.containsKey(name)) {
            throw new Exception("Command not found!");
        }

        WorkflowBlock block;
        try {
            var classOfBlock = Class.forName(config.getProperty(name));
            var objectOfBlock = classOfBlock.getDeclaredConstructor().newInstance();
            block = (WorkflowBlock)objectOfBlock;
        } catch (Exception e) {
            throw new Exception("Unable to create!");
        }

        return block;
    }
}

package Factory;

import Blocks.Block;
import Exceptions.CommandNotFoundException;

import java.io.IOException;
import java.util.Properties;

public class BlockFactory {
    private static Properties config = new Properties();

    private BlockFactory() throws IOException {
        var configStream = BlockFactory.class.getResourceAsStream("factory.config");
        if (configStream == null) {
            throw new IOException();
        }

        config.load(configStream);
    }

    private static volatile BlockFactory instance;

    public static BlockFactory getInstance() throws IOException {
        if (instance == null) {
            synchronized (BlockFactory.class) {
                if (instance == null) {
                    instance = new BlockFactory();
                }
            }
        }

        return instance;
    }

    public Block getBlock(String command) throws Exception {
        if (!config.containsKey(command)) {
            throw new CommandNotFoundException("Unrecognized command: " + command);
        }

        Block block;

        try {
            var classOfBlock = Class.forName(config.getProperty(command));
            var objectInstance = classOfBlock.getDeclaredConstructor().newInstance();
            block = (Block) objectInstance;
        } catch (Exception e) {
            throw new Exception("Unable to create!");
        }

        return block;
    }
}

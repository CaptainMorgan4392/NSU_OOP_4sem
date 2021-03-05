package Blocks;

import Exceptions.WrongParamsQuantityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

import static Blocks.Block.InOutParam.OUT;

public class WriteFile implements Block {
    private static final Logger logger = LogManager.getLogger(WriteFile.class.getName());
    @Override
    public void execute(ArrayList<String> text, ArrayList<String> currentArguments) throws WrongParamsQuantityException, FileNotFoundException {
        logger.trace("Starting execution!");
        if (currentArguments.size() != 1) {
            throw new WrongParamsQuantityException();
        }

        String filename = currentArguments.get(0);
        PrintWriter writer = new PrintWriter(
                                new OutputStreamWriter(
                                        new FileOutputStream(filename)));

        for (String line : text) {
            writer.write(line + '\n');
        }

        writer.close();

        for (int i = 0; i < text.size(); ++i) {
            text.removeAll(text);
        }

        logger.trace("Execution completed successfully!");
    }

    @Override
    public InOutParam getParamOfBlock() {
        return OUT;
    }
}

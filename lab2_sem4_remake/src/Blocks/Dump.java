package Blocks;

import Exceptions.WrongParamsQuantityException;
import Executor.WorkflowExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Dump implements Block {
    private static final Logger logger = LogManager.getLogger(Dump.class.getName());
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

        logger.trace("Execution completed successfully!");
    }
}

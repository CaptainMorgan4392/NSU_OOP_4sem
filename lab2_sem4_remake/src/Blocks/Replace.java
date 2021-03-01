package Blocks;

import Exceptions.WrongParamsQuantityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Replace implements Block {
    private static final Logger logger = LogManager.getLogger(Replace.class.getName());
    @Override
    public void execute(ArrayList<String> text, ArrayList<String> currentArguments) throws WrongParamsQuantityException, FileNotFoundException {
        logger.trace("Starting execution!");
        if (currentArguments.size() != 2) {
            throw new WrongParamsQuantityException();
        }

        String toRemove = "\\b" + currentArguments.get(0) + "\\b";
        String toInsert = currentArguments.get(1);

        for (int i = 0; i < text.size(); ++i) {
            text.set(i, text.get(i).replaceAll(toRemove, toInsert));
        }

        logger.trace("Execution completed successfully!");
    }
}

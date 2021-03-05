package Blocks;

import Exceptions.WrongParamsQuantityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static Blocks.Block.InOutParam.IN_OUT;

public class Grep implements Block {
    private static final Logger logger = LogManager.getLogger(Grep.class.getName());
    @Override
    public void execute(ArrayList<String> text, ArrayList<String> currentArguments) throws WrongParamsQuantityException, FileNotFoundException {
        logger.trace("Starting execution!");
        if (currentArguments.size() != 1) {
            throw new WrongParamsQuantityException();
        }

        ArrayList <String> selected = new ArrayList<>();
        String wordToFind = currentArguments.get(0);

        for (String line : text) {
            if (line.contains(wordToFind)) {
                selected.add(line);
            }
        }

        text.removeAll(text);
        text.addAll(selected);

        logger.trace("Execution completed successfully!");
    }

    @Override
    public InOutParam getParamOfBlock() {
        return IN_OUT;
    }
}

package Blocks;

import Exceptions.WrongParamsQuantityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

public class ReadFile implements Block {
    private static final Logger logger = LogManager.getLogger(ReadFile.class.getName());
    @Override
    public void execute(ArrayList<String> text, ArrayList<String> currentArguments) throws WrongParamsQuantityException, IOException {
        logger.trace("Starting execution!");
        if (currentArguments.size() != 1) {
            throw new WrongParamsQuantityException();
        }

        String filename = currentArguments.get(0);
        BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(
                                        new FileInputStream(filename)));

        while (reader.ready()) {
            text.add(reader.readLine());
        }

        reader.close();

        logger.trace("Execution completed successfully!");
    }
}

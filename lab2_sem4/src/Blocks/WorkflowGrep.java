package Blocks;

import Exceptions.WorkflowException;
import Exceptions.WrongParamsQuantityException;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class WorkflowGrep extends WorkflowBlock {
    static Logger newLogger;

    static {
        new WorkflowGrep();
        try (FileInputStream input = new FileInputStream("src/logger.config")) {
            LogManager.getLogManager().readConfiguration(input);
            newLogger = Logger.getLogger(WorkflowFileReader.class.getName());

            newLogger.log(Level.INFO, "Created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized ArrayList<String> execute(List <String> args, ArrayList<String> text) throws WorkflowException {
        if (args.size() != 1) {
            throw new WrongParamsQuantityException();
        }

        ArrayList <String> newText = new ArrayList<>();
        String wordToBeFound = args.get(0);

        for (String line : text) {
            if (line.contains(wordToBeFound)) {
                newText.add(line);
            }
        }

        newLogger.log(Level.INFO, "Executed successfully!");
        return newText;
    }
}

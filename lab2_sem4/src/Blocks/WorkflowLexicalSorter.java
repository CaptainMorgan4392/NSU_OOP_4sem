package Blocks;

import Exceptions.WorkflowException;
import Exceptions.WrongParamsQuantityException;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class WorkflowLexicalSorter extends WorkflowBlock {
    static Logger newLogger;

    static {
        new WorkflowLexicalSorter();
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
        if (!args.isEmpty()) {
            throw new WrongParamsQuantityException();
        }
        if (text.size() > 1) {
            text.sort((Comparator<String>) (o1, o2) -> o1.compareToIgnoreCase(o2));
        }

        newLogger.log(Level.INFO, "Executed successfully!");
        return text;
    }
}

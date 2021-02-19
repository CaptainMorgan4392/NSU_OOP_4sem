package Blocks;

import Exceptions.WorkflowException;
import Exceptions.WrongParamsQuantityException;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class WorkflowReplacer extends WorkflowBlock {
    static Logger newLogger;

    static {
        new WorkflowReplacer();
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
        if (args.size() != 2) {
            throw new WrongParamsQuantityException();
        }

        String toRemove = args.get(0);
        String toInsert = args.get(1);

        for (int i = 0; i < text.size(); ++i) {
            text.set(i, text.get(i).replaceAll("\\b" + toRemove + "\\b", toInsert));
        }

        newLogger.log(Level.INFO, "Executed successfully!");
        return text;
    }
}

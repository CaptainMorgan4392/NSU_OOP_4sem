package Blocks;

import Exceptions.WorkflowException;
import Exceptions.WrongParamsQuantityException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class WorkflowFileWriter extends WorkflowBlock {
    static Logger newLogger;

    static {
        new WorkflowFileWriter();
        try (FileInputStream input = new FileInputStream("src/logger.config")) {
            LogManager.getLogManager().readConfiguration(input);
            newLogger = Logger.getLogger(WorkflowFileReader.class.getName());

            newLogger.log(Level.INFO, "Created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized ArrayList<String> execute(List <String> args, ArrayList<String> text) throws IOException, WorkflowException {
        if (args.size() != 1) {
            throw new WrongParamsQuantityException();
        }

        String filename = args.get(0);
        BufferedWriter output = new BufferedWriter(
                                    new FileWriter(filename));

        for (String line : text) {
            output.write(line + '\n');
        }

        output.close();

        newLogger.log(Level.INFO, "Executed successfully!");
        return new ArrayList<>();
    }
}

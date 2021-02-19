package Blocks;

import Exceptions.WorkflowException;
import Exceptions.WrongParamsQuantityException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class WorkflowFileReader extends WorkflowBlock {
    static Logger newLogger;

    static {
        new WorkflowFileReader();
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
        text = new ArrayList<>();

        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));

        while (input.ready()) {
            text.add(input.readLine());
        }

        input.close();

        newLogger.log(Level.INFO, "Executed successfully!");
        return text;
    }
}

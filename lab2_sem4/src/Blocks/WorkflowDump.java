package Blocks;

import Exceptions.WorkflowException;
import Exceptions.WrongParamsQuantityException;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class WorkflowDump extends WorkflowBlock {
    static {
        new WorkflowDump();
    }

    @Override
    public synchronized void execute(List <String> args, ArrayList<String> text) throws IOException, WorkflowException {
        if (args.size() != 1) {
            throw new WrongParamsQuantityException();
        }

        String filename = args.get(0);
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));

        for (String line : text) {
            output.write(line + '\n');
        }
    }
}

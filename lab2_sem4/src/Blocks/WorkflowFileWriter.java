package Blocks;

import Exceptions.WorkflowException;
import Exceptions.WrongParamsQuantityException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WorkflowFileWriter extends WorkflowBlock {
    static {
        new WorkflowFileWriter();
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

        text = new ArrayList<>();
    }
}

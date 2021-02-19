package Blocks;

import Exceptions.WorkflowException;
import Exceptions.WrongParamsQuantityException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WorkflowFileReader extends WorkflowBlock {
    static {
        new WorkflowFileReader();
    }

    @Override
    public synchronized void execute(List <String> args, ArrayList<String> text) throws IOException, WorkflowException {
        if (args.size() != 1) {
            throw new WrongParamsQuantityException();
        }

        String filename = args.get(0);
        text = new ArrayList<>();

        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));

        while (input.ready()) {
            text.add(input.readLine());
        }
    }
}

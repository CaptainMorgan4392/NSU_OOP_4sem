package Blocks;

import Exceptions.WorkflowException;
import Exceptions.WrongParamsQuantityException;

import java.util.ArrayList;
import java.util.List;

public class WorkflowReplacer extends WorkflowBlock {
    static {
        new WorkflowReplacer();
    }

    @Override
    public synchronized void execute(List <String> args, ArrayList<String> text) throws WorkflowException {
        if (args.size() != 2) {
            throw new WrongParamsQuantityException();
        }

        String toRemove = args.get(0);
        String toInsert = args.get(1);

        for (String line : text) {
            line = line.replaceAll(toRemove, toInsert);
        }
    }
}

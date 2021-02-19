package Blocks;

import Exceptions.WorkflowException;
import Exceptions.WrongParamsQuantityException;

import java.util.ArrayList;
import java.util.List;

public class WorkflowGrep extends WorkflowBlock {
    static {
        new WorkflowGrep();
    }

    @Override
    public synchronized void execute(List <String> args, ArrayList<String> text) throws WorkflowException {
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

        text = newText;
    }
}

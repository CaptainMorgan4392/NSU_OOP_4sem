package Blocks;

import Exceptions.WorkflowException;
import Exceptions.WrongParamsQuantityException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WorkflowLexicalSorter extends WorkflowBlock {
    static {
        new WorkflowLexicalSorter();
    }

    @Override
    public synchronized void execute(List <String> args, ArrayList<String> text) throws WorkflowException {
        text.sort((Comparator<String>) (o1, o2) -> o1.compareToIgnoreCase(o2));
    }
}

package Blocks;

import Exceptions.WorkflowException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class WorkflowBlock {
    public abstract ArrayList <String> execute(List <String> args, ArrayList <String> text) throws WorkflowException, IOException;
}

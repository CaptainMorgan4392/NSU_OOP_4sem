package Factory;

import Exceptions.WorkflowException;

import java.io.IOException;

public abstract class AbstractBlockFactory {
    public abstract void getConfig() throws IOException, WorkflowException;
}

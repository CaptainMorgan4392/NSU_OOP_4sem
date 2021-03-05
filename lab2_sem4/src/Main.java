import Executor.WorkflowExecutor;

public class Main {
    public static void main(String[] args) {
        WorkflowExecutor executor = new WorkflowExecutor("workflow.txt");
        executor.start();
    }
}

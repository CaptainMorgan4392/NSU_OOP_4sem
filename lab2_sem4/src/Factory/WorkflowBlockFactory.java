package Factory;

import Blocks.*;
import Exceptions.UndefinedIdentificatorException;
import Exceptions.UnknownWorkflowOperationException;
import Exceptions.WorkflowException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class WorkflowBlockFactory extends AbstractBlockFactory {
    private static class Pair <K extends Class, V extends List> {
        private K first;
        private V second;

        public K getFirst() {
            return first;
        }

        public V getSecond() {
            return second;
        }

        public void setFirst(K first) {
            this.first = first;
        }

        public void setSecond(V second) {
            this.second = second;
        }
    }

    private Map <Integer, Pair <Class, ArrayList <String>>> configStorage;

    private List <Integer> workflowSequence;
    private int stageOfExecution = 0;
    private List <String> curArgs;

    public WorkflowBlockFactory() throws IOException, WorkflowException {
        configStorage = new TreeMap<>();
        workflowSequence = new ArrayList<>();

        getConfig();
    }

    public Class getBlockByID() throws UndefinedIdentificatorException {
        int curStage = workflowSequence.get(stageOfExecution);
        stageOfExecution++;

        if (!configStorage.containsKey(curStage)) {
            throw new UndefinedIdentificatorException();
        }

        curArgs = configStorage.get(curStage).getSecond();
        return configStorage.get(curStage).getFirst();
    }

    @Override
    public void getConfig() throws IOException, NumberFormatException, WorkflowException {
        BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(
                                            WorkflowBlockFactory.class.getResourceAsStream("workflow.txt")));

        boolean definesReadEnded = false;
        while (reader.ready()) {
            String[] arrayOfKeyWords = reader.readLine().split(" +");
            String stringLiteralOfId = arrayOfKeyWords[0];

            if (!definesReadEnded) {
                switch (stringLiteralOfId) {
                    case "desc":
                        break;
                    case "csed":
                        definesReadEnded = true;
                        break;
                    default:
                        int curId = Integer.parseInt(stringLiteralOfId);
                        String operationId = arrayOfKeyWords[2];

                        ArrayList <String> arguments = new ArrayList<>();
                        arguments.addAll(Arrays.asList(arrayOfKeyWords).subList(3, arrayOfKeyWords.length));

                        Pair<Class, ArrayList<String>> associationWithId = new Pair<>();

                        switch (operationId) {
                            case "readfile":
                                associationWithId.setFirst(WorkflowFileReader.class);
                                associationWithId.setSecond(arguments);
                                configStorage.put(curId, associationWithId);
                                break;
                            case "writefile":
                                associationWithId.setFirst(WorkflowFileWriter.class);
                                associationWithId.setSecond(arguments);
                                configStorage.put(curId, associationWithId);
                                break;
                            case "sort":
                                associationWithId.setFirst(WorkflowLexicalSorter.class);
                                associationWithId.setSecond(arguments);
                                configStorage.put(curId, associationWithId);
                                break;
                            case "grep":
                                associationWithId.setFirst(WorkflowGrep.class);
                                associationWithId.setSecond(arguments);
                                configStorage.put(curId, associationWithId);
                                break;
                            case "replace":
                                associationWithId.setFirst(WorkflowReplacer.class);
                                associationWithId.setSecond(arguments);
                                configStorage.put(curId, associationWithId);
                                break;
                            case "dump":
                                associationWithId.setFirst(WorkflowDump.class);
                                associationWithId.setSecond(arguments);
                                configStorage.put(curId, associationWithId);
                                break;
                            default:
                                throw new UnknownWorkflowOperationException();
                        }
                }
            } else {
                for (String line  : arrayOfKeyWords) {
                    boolean isValid = !line.equals("->");
                    if (isValid) {
                        workflowSequence.add(Integer.parseInt(line));
                    }
                }
                break;
            }
        }
    }

    public List<String> getCurArgsToExecute() {
        return curArgs;
    }
}

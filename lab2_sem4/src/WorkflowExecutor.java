import Blocks.WorkflowBlock;
import Exceptions.RedefinitionException;
import Exceptions.UndefinedIdentificatorException;
import Exceptions.WrongConfigInformationException;
import Factory.WorkflowBlockFactory;

import java.io.*;
import java.util.*;

public class WorkflowExecutor {
    private ArrayList <String> text;
    private String configFilename;

    private boolean cfgReadStarted = false;
    private boolean cfgReadEnded = false;

    Map<String, ArrayList<String>> associativeArgs = new TreeMap<>();
    ArrayList <String> workflowSequence = new ArrayList<>();

    int indexInSequence = 0;

    public WorkflowExecutor() throws Exception {
        text = new ArrayList<>();
        configFilename = "workflow.txt";

        Properties cfgProperties = new Properties();
        cfgProperties = getConfig();

        cfgProperties.store(new FileWriter("src/Factory/config.properties"), null);
    }

    public WorkflowExecutor(String fileName) throws Exception {
        text = new ArrayList<>();
        configFilename = fileName;

        Properties cfgProperties;
        cfgProperties = getConfig();

        cfgProperties.store(new FileWriter("src/Factory/config.properties"), null);
    }

    public ArrayList <String> getText() {
        return text;
    }


    private String getBlockId() {
        return workflowSequence.get(indexInSequence);
    }

    private List <String> getCurListOfArgs() {
        String curId = workflowSequence.get(indexInSequence);
        indexInSequence++;

        return associativeArgs.get(curId);
    }

    private boolean isEndOfSequence() {
        return indexInSequence == workflowSequence.size();
    }

    private void readSequence(BufferedReader reader) throws IOException {
        String[] arrayOfLiterals = reader.readLine().split(" +-> +");
        workflowSequence.addAll(Arrays.asList(arrayOfLiterals));
    }

    private Properties getConfig() throws Exception {
        BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(
                                            new FileInputStream(configFilename)));

        Properties properties = new Properties();
        boolean readCfgStarted = false;

        while (reader.ready()) {
            String[] keyWords = reader.readLine().split(" +");
            final String id = keyWords[0];

            if (properties.containsKey(id)) {
                throw new RedefinitionException();
            }

            switch (id) {
                case "desc":
                    readCfgStarted = true;
                    break;
                case "csed":
                    readSequence(reader);
                    return properties;
                default:
                    if (!readCfgStarted) {
                        throw new WrongConfigInformationException();
                    }
                    final String operation = keyWords[2];
                    switch (operation) {
                        case "readfile" -> properties.setProperty(id, "Blocks.WorkflowFileReader");
                        case "writefile" -> properties.setProperty(id, "Blocks.WorkflowFileWriter");
                        case "grep" -> properties.setProperty(id, "Blocks.WorkflowGrep");
                        case "sort" -> properties.setProperty(id, "Blocks.WorkflowLexicalSorter");
                        case "replace" -> properties.setProperty(id, "Blocks.WorkflowReplacer");
                        case "dump" -> properties.setProperty(id, "Blocks.WorkflowDump");
                        default -> throw new UndefinedIdentificatorException();
                    }

                    ArrayList<String> args = new ArrayList<>(Arrays.asList(keyWords).subList(3, keyWords.length));
                    associativeArgs.put(id, args);
            }
        }

        return properties;
    }

    public void start() throws Exception {
        while (!isEndOfSequence()) {
            WorkflowBlock newBlock = WorkflowBlockFactory.getInstance().getBlock(getBlockId());
            text = newBlock.execute(getCurListOfArgs(), text);
        }
    }
}

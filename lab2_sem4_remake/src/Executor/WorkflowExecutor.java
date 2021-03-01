package Executor;

import Blocks.Block;
import Exceptions.UnrecognizedIdException;
import Exceptions.WrongInputFormatException;
import Exceptions.WrongParamsQuantityException;
import Factory.BlockFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

public class WorkflowExecutor {
    private static final Logger logger = LogManager.getLogger(WorkflowExecutor.class);

    static class Pair <K extends String, V extends ArrayList<String>> {
        K command;
        V arguments;

        public Pair(K first, V second) {
            this.setCommand(first);
            this.setArguments(second);
        }

        public K getCommand() {
            return command;
        }

        public void setCommand(K command) {
            this.command = command;
        }

        public V getArguments() {
            return arguments;
        }

        public void setArguments(V arguments) {
            this.arguments = arguments;
        }
    }



    private ArrayList <String> text;
    private final String filename;

    private ArrayList <Pair <String, ArrayList <String>>> workflowSequence;
    private int indexInSequence = 0;

    public WorkflowExecutor(String inputFilename) {
        logger.trace("Creating new workflow environment...");

        this.filename = inputFilename;
        workflowSequence = new ArrayList<>();
        text = new ArrayList<>();

        logger.trace("Workflow environment created successfully!");
    }



    public ArrayList<String> getText() {
        return text;
    }

    ArrayList<Pair<String, ArrayList<String>>> getSequence() {
        return workflowSequence;
    }

    String getFilename() {
        return filename;
    }


    private boolean allSequenceRead() {
        return this.indexInSequence == workflowSequence.size();
    }

    private String getCurrentKey() {
        return workflowSequence.get(indexInSequence).getCommand();
    }

    private ArrayList <String> getCurrentArguments() {
        return workflowSequence.get(indexInSequence).getArguments();
    }

    public void start() {
        try {
            logger.trace("Start parsing raw data...");
            FileParser.parseInput(this);
            logger.trace("Parsing completed successfully!");

            while (!allSequenceRead()) {
                Block newBlock = BlockFactory.getInstance().getBlock(getCurrentKey());
                logger.trace("Instance of " + newBlock.getClass().getSimpleName() + " has created.");

                newBlock.execute(text, getCurrentArguments());

                indexInSequence++;
            }

            logger.trace("All sequence completed without errors! This session will be terminated.\n");
        } catch (IOException e) {
            logger.error("An error has occured during reading file or creating buffer!!!");
            logger.error("Process will be terminated.");
        } catch (WrongInputFormatException e) {
            logger.error("Raw data has wrong format!");
            logger.error("Process will be terminated.");
        } catch (UnrecognizedIdException e) {
            logger.error("Workflow sequence contains undefined ID!");
            logger.error("Process will be terminated.");
        } catch (WrongParamsQuantityException e) {
            logger.error("In created block passed wrong number of arguments!");
            logger.error("Process will be terminated.");
        } catch (Exception e) {
            logger.error("An error has occured during creating of block!");
            logger.error("Process will be terminated.");
        }
    }
}

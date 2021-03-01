package Executor;

import Exceptions.UnrecognizedIdException;
import Exceptions.WrongInputFormatException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FileParser {
    public static void parseInput(WorkflowExecutor executor) throws IOException, WrongInputFormatException, UnrecognizedIdException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(executor.getFilename())));
        Map<String, WorkflowExecutor.Pair<String, ArrayList<String>>> association = new HashMap<>();

        while (reader.ready()) {
            String curLine = reader.readLine();

            if (curLine.isEmpty()) {
                continue;
            }

            String[] keyWords = curLine.split(" +");

            String firstWord = keyWords[0];
            if (firstWord.equals("csed")) {
                break;
            } else if (!firstWord.equals("desc")) {
                String mustBeEqualitySymbol = keyWords[1];
                if (!mustBeEqualitySymbol.equals("=")) {
                    throw new WrongInputFormatException();
                }

                ArrayList <String> args = new ArrayList<>(Arrays.asList(keyWords).subList(3, keyWords.length));
                String command = keyWords[2];

                association.put(firstWord, new WorkflowExecutor.Pair<>(command, args));
            }
        }

        if (reader.ready()) {
            String[] sequence = reader.readLine().split(" +-> +");
            for (String current : sequence) {
                if (association.containsKey(current)) {
                    executor.getSequence().add(association.get(current));
                } else {
                    throw new UnrecognizedIdException();
                }
            }
        }
    }

}

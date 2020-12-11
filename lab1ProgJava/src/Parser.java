import java.util.*;
import java.io.*;

public class Parser {
    private final Scanner scanner;
    private Map<String, Integer> counting;
    private int totalRead;

    public Parser(String filename) throws FileNotFoundException {
        scanner = new Scanner(new FileInputStream(filename));
        counting = new TreeMap<>();
    }

    private void sortByValue() {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(counting.entrySet());

        list.sort(new ValueComparator());
        counting = new LinkedHashMap<>();

        for (Map.Entry <String, Integer> cur : list) {
            counting.put(cur.getKey(), cur.getValue());
        }
    }

    public void readAndParse() {
        while (scanner.hasNextLine()) {
            String[] curLine = scanner.nextLine().split(" +");
            for (String word : curLine) {
                if (counting.containsKey(word)) {
                    counting.put(word, counting.get(word) + 1);
                } else {
                    counting.put(word, 1);
                }
                totalRead++;
            }
        }

        sortByValue();
    }

    public void getOutput() {
        for (Map.Entry <String, Integer> current : counting.entrySet()) {
            String key = current.getKey();
            Integer val = current.getValue();
            System.out.println(key + " " + val + " " + (double)val / totalRead);
        }
    }
}

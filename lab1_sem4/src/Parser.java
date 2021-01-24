import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;

public class Parser implements AutoCloseable {
    private Map<String, Long> counter;
    private List<Map.Entry<String, Long>> sortedByValue;
    private Reader reader;
    private Writer writer;

    long totalRead = 0;

    public Parser(Reader inputReader, Writer outputWriter) throws IOException {
        reader = inputReader;
        writer = outputWriter;
        counter = new HashMap<>();
        sortedByValue = new LinkedList<>();
    }

    public void readInput() throws IOException {
        int cur;
        StringBuilder bufferString = new StringBuilder();
        while ((cur = reader.read()) != -1) {
            char curChar = (char)cur;
            if (Character.isLetterOrDigit(curChar)) {
                bufferString.append(curChar);
            } else if (!bufferString.isEmpty()) {
                String currentWord = bufferString.toString();
                long tiedValue = (counter.containsKey(currentWord) ? counter.get(currentWord) + 1 : 1);

                counter.put(currentWord, tiedValue);

                bufferString = new StringBuilder();
                totalRead++;
            }
        }

        if (!bufferString.isEmpty()) {
            String currentWord = bufferString.toString();
            long tiedValue = (counter.containsKey(currentWord) ? counter.get(currentWord) + 1 : 1);

            counter.put(currentWord, tiedValue);
        }
    }

    private void getSortedList() {
        sortedByValue.addAll(counter.entrySet());

        sortedByValue.sort((o1, o2) -> -(o1.getValue().compareTo(o2.getValue())));
    }

    public void writeSorted() throws IOException {
        getSortedList();

        for (Map.Entry <String, Long> curEntry : sortedByValue) {
            writer.write(curEntry.getKey() + " " + curEntry.getValue() + " " + (double)curEntry.getValue() / totalRead + '\n');
        }
    }

    @Override
    public void close() throws Exception {
        reader.close();
        writer.close();
    }
}

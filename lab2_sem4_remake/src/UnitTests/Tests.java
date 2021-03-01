package UnitTests;

import Executor.WorkflowExecutor;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {
    @Test
    @DisplayName("Test reading file...") void read() {
        WorkflowExecutor executor = new WorkflowExecutor("testingRead.txt");

        executor.start();

        ArrayList <String> words = new ArrayList<>(Arrays.asList("kek lol",
                                                                 " arbidol",
                                                                 "    chebyrek",
                                                                 "",
                                                                 "prosrochenniy chebyrek"));
        assertArrayEquals(executor.getText().toArray(), words.toArray());
    }

    @Test
    @DisplayName("Test sort...") void sort() {
        WorkflowExecutor executor = new WorkflowExecutor("testingSort.txt");

        executor.start();

        ArrayList <String> words = new ArrayList<>(Arrays.asList("",
                "    chebyrek",
                " arbidol",
                "kek lol",
                "prosrochenniy chebyrek"));
        assertArrayEquals(executor.getText().toArray(), words.toArray());
    }

    @Test
    @DisplayName("Test grep...") void grep() {
        WorkflowExecutor executor = new WorkflowExecutor("testingGrep.txt");

        executor.start();

        ArrayList <String> words = new ArrayList<>(Arrays.asList("    chebyrek", "prosrochenniy chebyrek"));
        assertArrayEquals(executor.getText().toArray(), words.toArray());
    }

    @Test
    @DisplayName("Test replace...") void replace() {
        WorkflowExecutor executor = new WorkflowExecutor("testingReplace.txt");

        ArrayList <String> words = new ArrayList<>(Arrays.asList("kek lol",
                " arbidol",
                "    belyash",
                "",
                "prosrochenniy belyash"));

        executor.start();
        assertArrayEquals(executor.getText().toArray(), words.toArray());
    }

    @Test
    @DisplayName("Test dump...") void dump() throws IOException {
        WorkflowExecutor executor = new WorkflowExecutor("testingDump.txt");

        executor.start();

        InputStreamReader reader1 = new InputStreamReader(new FileInputStream("words.txt"));
        InputStreamReader reader2 = new InputStreamReader(new FileInputStream("written.txt"));

        while (reader1.ready() && reader2.ready()) {
            assertEquals(reader2.read(), reader1.read());
        }

        assertFalse(reader1.ready() || reader2.ready());

        reader1.close();
        reader2.close();

        ArrayList <String> words = new ArrayList<>(Arrays.asList("kek lol",
                " arbidol",
                "    chebyrek",
                "",
                "prosrochenniy chebyrek"));

        assertArrayEquals(executor.getText().toArray(), words.toArray());
    }

    @Test
    @DisplayName("Test writing file...") void write() throws IOException {
        WorkflowExecutor executor = new WorkflowExecutor("testingWrite.txt");

        executor.start();

        InputStreamReader reader1 = new InputStreamReader(new FileInputStream("words.txt"));
        InputStreamReader reader2 = new InputStreamReader(new FileInputStream("written.txt"));

        while (reader1.ready() && reader2.ready()) {
            assertEquals(reader2.read(), reader1.read());
        }

        assertFalse(reader1.ready() || reader2.ready());

        reader1.close();
        reader2.close();
    }
}

package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Console;
import java.io.IOException;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            Infrastructure infrastructure = new Infrastructure("/config.properties");
            infrastructure.start();

            waitUntilEnterPressed(infrastructure);
        } catch (Exception e) {
            logger.error("Unable to create infrastructure! Terminating...");
        }
    }

    private static void waitUntilEnterPressed(Infrastructure infrastructure) {
        while (true) {
            try {
                char curEnter = (char)System.in.read();
                if (curEnter == '\n') {
                    infrastructure.stop();
                    return;
                }
            } catch (IOException e) {
                return;
            }
        }
    }
}

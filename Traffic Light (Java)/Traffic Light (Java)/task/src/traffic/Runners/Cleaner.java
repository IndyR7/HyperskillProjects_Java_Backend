package traffic.Runners;

import java.io.IOException;
import java.util.Scanner;

public class Cleaner {
    protected static void clearOutput() {
        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
        }
    }
}

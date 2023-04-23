package advisor;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length > 1 && args[0].equals("-access")) {
            Config.SERVER_PATH = args[1];
        }
        if (args.length > 3 && args[2].equals("-resource")) {
            Config.API_PATH = args[3];
        }
        if (args.length > 5 && args[4].equals("-page")) {
            Controller.numberOfItems = Integer.parseInt(args[5]);
        }

        Scanner scanner = new Scanner(System.in);
        UI userInterface = new UI(scanner);

        userInterface.start();
    }
}

package flashcards;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UI userInterface = new UI(scanner);
        if (args.length > 3) {
            userInterface.start(args[0], args[1], args[2], args[3]);
        }
        else if (args.length > 1) {
            userInterface.start(args[0], args[1], "", "");
        }
        else {
            userInterface.start("", "", "", "");
        }
    }
}

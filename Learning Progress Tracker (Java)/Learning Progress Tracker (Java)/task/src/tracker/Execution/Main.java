package tracker.Execution;

import tracker.Constants.Prompt;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UI userInterface = new UI(scanner);

        userInterface.start();
    }
}
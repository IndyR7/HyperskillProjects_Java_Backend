package traffic.Runners;

import traffic.Constants.Prompt;
import traffic.DataHandler.RoadDataHandler;
import traffic.Validators.InputValidator;

import java.util.Scanner;

public class MainMenuRunner {
    private static Scanner scanner;

    protected static void run(Scanner sc) {
        initialize(sc);

        while (true) {
            System.out.println(Prompt.MAIN_MENU);

            String input = scanner.nextLine();
            int operation = InputValidator.isValidNumberFormat(input) ? Integer.parseInt(input) : -1;

            if (operation == 0) {
                SystemInfoRunner.getInstance().interrupt();
                break;
            }

            runOperation(operation);
            scanner.nextLine();

            if (operation == 3) {
                SystemInfoRunner.getInstance().infoDisplayer().interrupt();
            }

            Cleaner.clearOutput();
        }
    }

    private static void runOperation(int operation) {
        switch (operation) {
            case 1 -> addRoad();
            case 2 -> deleteRoad();
            case 3 -> SystemInfoRunner.getInstance().infoDisplayer().start();
            default -> System.out.println(Prompt.INCORRECT_OPTION);
        }
    }

    private static void initialize(Scanner sc) {
        SystemInfoRunner.getInstance().start();
        scanner = sc;
    }

    private static void addRoad() {
        System.out.println(Prompt.INPUT_ROAD_NAME);

        RoadDataHandler.getInstance().addRoad(scanner.nextLine());
    }

    private static void deleteRoad() {
        RoadDataHandler.getInstance().deleteRoad();
    }
}
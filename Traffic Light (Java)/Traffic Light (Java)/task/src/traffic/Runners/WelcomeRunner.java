package traffic.Runners;

import traffic.Constants.Prompt;
import traffic.DataHandler.RoadDataHandler;
import traffic.Validators.InputValidator;

import java.util.Scanner;

public class WelcomeRunner {
    protected static void run(Scanner scanner) {
        System.out.println(Prompt.WELCOME);

        System.out.println(Prompt.INPUT_NUMBER_OF_ROADS);

        runWelcomeSubStage(scanner, 1);

        System.out.println(Prompt.INPUT_INTERVAL);

        runWelcomeSubStage(scanner, 2);
    }

    private static void runWelcomeSubStage(Scanner scanner, int stageNumber) {
        while (true) {
            String input = scanner.nextLine();

            if (InputValidator.isValidInputInWelcomeStage(input)) {
                if (stageNumber == 1) {
                    RoadDataHandler.getInstance().setMaxRoads(Integer.parseInt(input));
                    break;
                }

                if (stageNumber == 2) {
                    RoadDataHandler.getInstance().setInterval(Integer.parseInt(input));
                    break;
                }
            }

            System.out.print(Prompt.INCORRECT_INPUT);
        }
    }
}
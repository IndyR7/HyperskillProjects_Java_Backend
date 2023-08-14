package traffic.Runners;

import traffic.Constants.Prompt;

import java.util.Scanner;

public class UserInterface {
    public static void run(Scanner scanner) {
        WelcomeRunner.run(scanner);
        MainMenuRunner.run(scanner);

        System.out.println(Prompt.BYE);
    }
}
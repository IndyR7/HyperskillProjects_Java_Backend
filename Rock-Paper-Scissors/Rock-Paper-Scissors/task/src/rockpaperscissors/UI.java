package rockpaperscissors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UI {
    private final Scanner scanner;
    private Ratings ratings;

    public UI(Scanner scanner, Ratings ratings) {
        this.scanner = scanner;
        this.ratings = ratings;
    }

    public void start() {
        System.out.println("Enter your name:");
        String user = scanner.nextLine();
        System.out.printf("Hello, %s\n", user);
        ratings.update(user, 0);
        String options = scanner.nextLine();
        System.out.println("Okay, let's start");
        Game game = new Game(scanner, options, user, ratings);
        while (game.isRunning()) {
            game.play();
        }
    }
}

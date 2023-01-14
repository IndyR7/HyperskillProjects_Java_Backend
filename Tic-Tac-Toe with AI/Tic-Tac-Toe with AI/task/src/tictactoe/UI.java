package tictactoe;

import java.util.Scanner;

public class UI {
    private final Scanner scanner;

    public UI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println("Input command:");
            String[] command = scanner.nextLine().split(" ");
            if (isLegalCommand(command)) {
                if (command[0].equals("exit")) {
                    break;
                }
                Game game = new Game(new GameBoard(), scanner, command[1], command[2]);
                game.play();
            } else {
                System.out.println("Bad parameters!");
            }
        }
    }

    private boolean isLegalCommand(String[] command) {
        if (command.length < 3 && !command[0].equals("exit")) {
            return false;
        }
        if (command[0].equals("exit")) {
            return true;
        }
        if (!command[0].equals("start")) {
            return false;
        }
        if (!command[1].equals("user") && !command[1].equals("easy") && !command[1].equals("medium")
                && !command[1].equals("hard")) {
            return false;
        }
        return command[2].equals("user") || command[2].equals("easy") || command[2].equals("medium")
                || command[2].equals("hard");
    }
}




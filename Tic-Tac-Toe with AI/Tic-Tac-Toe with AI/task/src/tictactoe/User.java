package tictactoe;

import java.util.Scanner;

public class User implements Player {
    private final GameBoard gameBoard;
    private final Scanner scanner;

    public User(GameBoard gameBoard, Scanner scanner) {
        this.gameBoard = gameBoard;
        this.scanner = scanner;
    }

    @Override
    public void makeMove() {
        gameBoard.setCurrentPlayer();
        addUserMove();
    }

    private void addUserMove() {
        while (true) {
            System.out.println("Enter the coordinates:");
            String input = scanner.nextLine();
            if (isLegalMove(input)) {
                String[] coordinates = input.split(" ");
                int x = Integer.parseInt(coordinates[0]) - 1;
                int y = Integer.parseInt(coordinates[1]) - 1;
                gameBoard.add(x, y, gameBoard.getCurrentPlayer());
                break;
            } else {
                printError(input);
            }
        }
    }

    private boolean isLegalMove(String input) {
        return isParsableToIntegers(input) && areLegalCoordinates(input) && isEmptySpot(input);
    }

    private boolean isParsableToIntegers(String input) {
        String[] coordinates = input.split(" ");
        try {
            Integer.parseInt(coordinates[0]);
            Integer.parseInt(coordinates[1]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean areLegalCoordinates(String input) {
        String[] coordinates = input.split(" ");
        int x = Integer.parseInt(coordinates[0]) - 1;
        int y = Integer.parseInt(coordinates[1]) - 1;
        return x >= 0 && x < 3 && y >= 0 && y < 3 && coordinates.length < 3;
    }

    private boolean isEmptySpot(String input) {
        String[] coordinates = input.split(" ");
        int x = Integer.parseInt(coordinates[0]) - 1;
        int y = Integer.parseInt(coordinates[1]) - 1;
        return gameBoard.isEmpty(x, y);
    }

    private void printError(String input) {
        if (!isParsableToIntegers(input)) {
            System.out.println("You should enter numbers!\n");
        } else if (!areLegalCoordinates(input)) {
            System.out.println("Coordinates should be from 1 to 3!\n");
        } else {
            System.out.println("This cell is occupied! Choose another one!\n");
        }
    }
}

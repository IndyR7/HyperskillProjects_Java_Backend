package tictactoe;

import java.util.Scanner;
public class UI {
    private Scanner scanner;
    public UI(Scanner scanner) {
        this.scanner = scanner;
    }
    public void start() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.printField();
        while(gameBoard.isNotFinished()) {
            String coordinates = scanner.nextLine();
            if (gameBoard.moveIsLegal(coordinates)) {
                    gameBoard.takeTurn();
                }
            else {
                gameBoard.printError();
            }
        }
        gameBoard.printResult();
    }
}

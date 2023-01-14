package tictactoe;

import java.util.Scanner;

public class Game {
    private final GameBoard gameBoard;
    private final Player firstPlayer;
    private final Player secondPlayer;

    public Game(GameBoard gameBoard, Scanner scanner, String first, String second) {
        this.gameBoard = gameBoard;
        firstPlayer = first.equals("user") ? new User(gameBoard, scanner) : new AI(gameBoard, first);
        secondPlayer = second.equals("user") ? new User(gameBoard, scanner) : new AI(gameBoard, second);
    }

    public void play() {
        boolean firstPlayerPlaying = true;
        while (gameBoard.isRunning()) {
            gameBoard.printField();
            if (firstPlayerPlaying) {
                firstPlayer.makeMove();
            } else {
                secondPlayer.makeMove();
            }
            firstPlayerPlaying = !firstPlayerPlaying;
        }
        gameBoard.printResult();
    }
}

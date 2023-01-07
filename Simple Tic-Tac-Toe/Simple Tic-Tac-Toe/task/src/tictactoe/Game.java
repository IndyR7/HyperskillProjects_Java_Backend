package tictactoe;

import java.util.Scanner;

public class Game {
    private final GameBoard gameBoard;
    private final Player firstPlayer;
    private final Player secondPlayer;

    public Game(GameBoard gameBoard, Scanner scanner) {
        this.gameBoard = gameBoard;
        firstPlayer = new User(gameBoard, scanner);
        secondPlayer = new User(gameBoard, scanner);
    }

    public void play() {
        boolean firstPlayerPlaying = true;
        while (gameBoard.isNotFinished()) {
            gameBoard.printField();
            if (firstPlayerPlaying) {
                firstPlayer.makeMove();
                firstPlayerPlaying = false;
            } else {
                secondPlayer.makeMove();
                firstPlayerPlaying = true;
            }
        }
        gameBoard.printResult();
    }
}

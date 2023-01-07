package tictactoe;

import java.util.Scanner;
public class UI {
    private final Scanner scanner;
    public UI(Scanner scanner) {
        this.scanner = scanner;
    }
    public void start() {
        Game game = new Game(new GameBoard(), scanner);
        game.play();
    }
}

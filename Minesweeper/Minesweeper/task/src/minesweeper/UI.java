package minesweeper;

import java.util.Scanner;

public class UI {
    private Scanner scanner;

    public UI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("How many mines do you want on the field?");
        Game game = new Game(scanner.nextInt());
        game.printField();
        while (game.isRunning()) {
            System.out.println("Set/unset mines marks or claim a cell as free:");
            if (!game.add(scanner.nextInt() - 1, scanner.nextInt() - 1, scanner.next())) {
                System.out.println("This cell is already occupied!");
                continue;
            }
            game.printField();
        }
        game.printResult();
    }
}

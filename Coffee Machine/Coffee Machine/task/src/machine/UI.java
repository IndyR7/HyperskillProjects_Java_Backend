package machine;

import java.util.Scanner;

public class UI {
    private Scanner scanner;

    public UI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        Machine machine = new Machine(400, 540, 120, 9, 550);
        Operation operation = new Operation(scanner, machine);
        while (operation.isRunning()) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            operation.performOperation(scanner.nextLine());
        }

    }
}

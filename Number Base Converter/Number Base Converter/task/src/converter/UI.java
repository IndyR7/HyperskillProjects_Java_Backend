package converter;

import java.util.Scanner;

public class UI {

    private final Scanner scanner;

    public UI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit)");
            String command = scanner.nextLine();
            if (command.equals("/exit")) {
                break;
            }
            String[] bases = command.split(" ");
            int sourceBase = Integer.parseInt(bases[0]);
            int targetBase = Integer.parseInt(bases[1]);
            while (true) {
                System.out.printf("Enter number in base %d to convert to base %d (To go back type /back)\n",
                        sourceBase, targetBase);
                String input = scanner.nextLine();
                if (input.equals("/back")) {
                    break;
                }
                System.out.printf("Conversion result: %s\n\n",
                        Converter.getNumber(input, sourceBase, targetBase));
            }
        }
    }
}
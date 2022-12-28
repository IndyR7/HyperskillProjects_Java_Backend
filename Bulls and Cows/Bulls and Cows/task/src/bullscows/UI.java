package bullscows;

import java.util.*;

public class UI {
    private Scanner scanner;

    public UI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("Input the length of the secret code:");
        String codeLength = scanner.nextLine();
        if (!isLegalCodeLengthCommand(codeLength)) {
            printCodeLengthError(codeLength);
            return;
        }
        System.out.println("Input the number of possible symbols in the code:");
        String numberOfSymbols = scanner.nextLine();
        if (!isLegalNumberOfSymbolsCommand(codeLength, numberOfSymbols)) {
            printNumberOfSymbolsError(codeLength, numberOfSymbols);
            return;
        }
        Game game = new Game(Integer.parseInt(codeLength), Integer.parseInt(numberOfSymbols));
        printInitialisation(Integer.parseInt(codeLength), Integer.parseInt(numberOfSymbols), game);
        System.out.println("Okay, let's start a game!");
        int turn = 1;
        while (game.isRunning()) {
            System.out.println(String.format("Turn %d:", turn++));
            game.takeTurn(scanner.nextLine());
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    private boolean isLegalNumberOfSymbolsCommand(String codeLength, String numberOfSymbols) {
        return isLegalNumberOfSymbolsFormat(numberOfSymbols)
                && isLegalNumberOfSymbolsLength(codeLength, numberOfSymbols);
    }

    private boolean isLegalNumberOfSymbolsFormat(String numberOfSymbols) {
        try {
            Integer.parseInt(numberOfSymbols);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isLegalNumberOfSymbolsLength(String codeLength, String numberOfSymbols) {
        return Integer.parseInt(codeLength) < Integer.parseInt(numberOfSymbols)
                && Integer.parseInt(numberOfSymbols) <= 36;
    }

    private boolean isLegalCodeLengthCommand(String codeLength) {
        return isLegalCodeLengthFormat(codeLength) && isLegalCodeLength(codeLength);
    }

    private boolean isLegalCodeLengthFormat(String codeLength) {
        try {
            Integer.parseInt(codeLength);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isLegalCodeLength(String codeLength) {
        return Integer.parseInt(codeLength) <= 36 && !codeLength.equals("0");
    }

    private void printInitialisation(int codeLength, int numberOfSymbols, Game game) {
        StringBuilder sbToPrint = new StringBuilder().append("The secret is prepared: ");
        for (int i = 0; i < codeLength; i++) {
            sbToPrint.append("*");
        }
        String symbolsAllowed = numberOfSymbols <= 10 ? String.format(" (0-%s).", game.getSymbols()[numberOfSymbols - 1])
                : String.format(" (0-9, %s-%s).", game.getSymbols()[10], game.getSymbols()[numberOfSymbols - 1]);
        sbToPrint.append(symbolsAllowed);
        System.out.println(sbToPrint);
    }

    private void printNumberOfSymbolsError(String codeLength, String numberOfSymbols) {
        StringBuilder errorMessage = new StringBuilder();
        if (!isLegalNumberOfSymbolsFormat(numberOfSymbols)) {
            errorMessage.append(String.format("Error: \"%s\" isn't a valid number.\n", numberOfSymbols));
        } else if (!isLegalNumberOfSymbolsLength(codeLength, numberOfSymbols)) {
            errorMessage.append("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
        }
        System.out.print(errorMessage);
    }

    private void printCodeLengthError(String codeLength) {
        StringBuilder errorMessage = new StringBuilder();
        if (!isLegalCodeLengthFormat(codeLength)) {
            errorMessage.append(String.format("Error: \"%s\" isn't a valid number.\n", codeLength));
        } else if (!isLegalCodeLength(codeLength)) {
            errorMessage.append("Error: the length of code should be between 1 and 36.\n");
        }
        System.out.print(errorMessage);
    }
}

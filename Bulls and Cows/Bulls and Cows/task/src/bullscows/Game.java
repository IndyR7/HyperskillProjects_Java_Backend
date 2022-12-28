package bullscows;

import java.util.Arrays;
import java.util.Random;

public class Game {
    private String[] code;
    private int bulls;
    private int cows;
    private String[] symbols = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    public Game(int codeLength, int numberOfSymbols) {
        generateCode(codeLength, numberOfSymbols);
        this.bulls = 0;
        this.cows = 0;
    }

    private void checkInput(String input) {
        String[] numbers = input.split("");
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i].equals(this.code[i])) {
                this.bulls++;
                continue;
            }
            if (Arrays.asList(this.code).contains(numbers[i])) {
                this.cows++;
            }
        }
    }

    public boolean isRunning() {
        return this.bulls < this.code.length;
    }

    private boolean containsBulls() {
        return this.bulls > 0;
    }

    private boolean containsCows() {
        return this.cows > 0;
    }

    private boolean containsBoth() {
        return containsBulls() && containsCows();
    }

    public String getCode() {
        StringBuilder codeString = new StringBuilder();
        for (String number : this.code) {
            codeString.append(number);
        }
        return codeString.toString();
    }

    private void reset() {
        if (this.bulls != this.code.length) {
            this.cows = 0;
            this.bulls = 0;
        }
    }

    private String result() {
        StringBuilder result = new StringBuilder().append("Grade: ");
        String bulls = this.bulls > 1 ? "bulls" : "bull";
        String cows = this.cows > 1 ? "cows" : "cow";
        if (containsBoth()) {
            return result.append(String.format("%d %s and %d %s", this.bulls, bulls, this.cows, cows)).toString();
        }
        if (containsCows()) {
            return result.append(String.format("%d %s", this.cows, cows)).toString();
        }
        if (containsBulls()) {
            return result.append(String.format("%d %s", this.bulls, bulls)).toString();
        }
        return result.append("None.").toString();
    }

    String[] getSymbols() {
        return this.symbols;
    }

    private void generateCode(int codeLength, int numberOfSymbols) {
        this.code = new String[codeLength];
        this.code[0] = this.symbols[new Random().nextInt(numberOfSymbols - 1) + 1];
        for (int i = 1; i < this.code.length; i++) {
            String number = this.symbols[new Random().nextInt(numberOfSymbols)];
            if (!Arrays.asList(this.code).contains(number)) {
                this.code[i] = number;
                continue;
            }
            i--;
        }
    }

    public void takeTurn(String input) {
        checkInput(input);
        System.out.println(result());
        reset();
    }
}

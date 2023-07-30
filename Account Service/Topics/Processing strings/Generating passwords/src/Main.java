import java.util.*;

public class Main {
    static String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
    static String upperCaseLetters = lowerCaseLetters.toUpperCase();
    static String digits = "0123456789";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(createPassword(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
    }

    private static String createPassword(int upperCase, int lowerCase, int numbers, int count) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < upperCase; i++) {
            password.append(getUpperCaseLetters());
        }
        for (int i = 0; i < lowerCase; i++) {
            password.append(getLowerCaseLetters());
        }
        for (int i = 0; i < numbers; i++) {
            password.append(getDigits());
        }
        while (password.length() < count) {
            int symbolToAdd = new Random().nextInt(0, 3);
            char c = symbolToAdd == 0 ? getUpperCaseLetters()
                    : symbolToAdd == 1 ? getLowerCaseLetters()
                    : getDigits();
            password.append(c);
        }
        for (int i = 0; i < new Random().nextInt(0, 50); i++) {
            swap(password);
        }
        rearrange(password);
        return password.toString();
    }

    private static void rearrange(StringBuilder password) {
        for (int i = 0; i < password.length() - 1; i++) {
            if (password.charAt(i) == password.charAt(i + 1)) {
                swap(password);
                i = -1;
            }
        }
    }

    private static void swap(StringBuilder password) {
        int a = new Random().nextInt(0, password.length());
        int b = new Random().nextInt(0, password.length());
        char c = password.charAt(a);
        char d = password.charAt(b);
        password.setCharAt(a, d);
        password.setCharAt(b, c);
    }

    private static char getUpperCaseLetters() {
        return upperCaseLetters.charAt(new Random().nextInt(0, upperCaseLetters.length()));
    }

    private static char getLowerCaseLetters() {
        return lowerCaseLetters.charAt(new Random().nextInt(0, lowerCaseLetters.length()));
    }

    private static char getDigits() {
        return digits.charAt(new Random().nextInt(0, digits.length()));
    }
}
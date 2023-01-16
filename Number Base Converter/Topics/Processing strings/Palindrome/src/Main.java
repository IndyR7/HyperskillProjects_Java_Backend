import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        char[] listOfChars = input.toCharArray();
        int toCheck = listOfChars.length - 1;
        boolean test = true;
        for (char aChar : listOfChars) {
            if (aChar != listOfChars[toCheck]) {
                test = false;
                break;
            }
            toCheck--;
        }
        String toPrint = test ? "yes" : "no";
        System.out.println(toPrint);
    }
}

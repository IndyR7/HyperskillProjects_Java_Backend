import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfVariables = Integer.parseInt(scanner.nextLine());

        String regex1 = "_[A-Za-z\\d]+[\\w]*";
        String regex2 = "[A-Za-z][\\w]*";
        String regex = String.format("%s|%s", regex1, regex2);

        for (int i = 0; i < numberOfVariables; i++) {
            String identifier = scanner.nextLine();
            if (!identifier.matches(regex)) {
                System.out.println(identifier);
            }
        }
    }
}
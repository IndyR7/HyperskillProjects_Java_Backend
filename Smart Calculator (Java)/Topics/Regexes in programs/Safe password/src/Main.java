import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{12,}$";
        System.out.println(password.matches(passwordRegex) ? "YES" : "NO");
    }
}
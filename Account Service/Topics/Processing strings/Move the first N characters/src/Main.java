import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String a = scanner.next();
        int n = scanner.nextInt();

        String result = n > a.length() ? a
                : new StringBuilder(a).delete(0, n).append(a, 0, n).toString();
        System.out.println(result);
    }
}
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        if (input % 2 == 0) {
            System.out.println(input + 2);
        }
        else {
            System.out.println(input + 1);
        }
    }
}

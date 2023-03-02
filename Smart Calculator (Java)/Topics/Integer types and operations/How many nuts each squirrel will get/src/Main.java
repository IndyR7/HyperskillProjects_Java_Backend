import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int first = Integer.valueOf(scanner.next());
        int second = Integer.valueOf(scanner.next());
        System.out.println(second / first);
    }
}

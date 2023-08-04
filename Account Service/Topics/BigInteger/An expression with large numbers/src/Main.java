import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.nextBigInteger().negate()
                .multiply(scanner.nextBigInteger())
                .add(scanner.nextBigInteger())
                .subtract(scanner.nextBigInteger()));
    }
}
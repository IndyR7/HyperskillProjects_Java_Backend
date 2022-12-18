import java.util.Scanner;

public class Main {

    public static long factorial(long n) {
        long toReturn = 1;
        for (long i = n; i > 0; i--) {
            toReturn *= i;
        }
        return toReturn;
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = Integer.parseInt(scanner.nextLine().trim());
        System.out.println(factorial(n));
    }
}
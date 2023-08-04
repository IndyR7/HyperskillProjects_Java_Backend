import java.math.BigInteger;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BigInteger m = scanner.nextBigInteger();
        BigInteger k = BigInteger.valueOf(1);
        BigInteger n = BigInteger.valueOf(1);

        while (k.compareTo(m) < 0) {
            k = k.multiply(n);
            n = n.add(BigInteger.ONE);
        }

        System.out.println(n.subtract(BigInteger.ONE));
    }
}
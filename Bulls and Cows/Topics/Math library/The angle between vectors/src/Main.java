import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double vX1 = scanner.nextInt();
        double vX2 = scanner.nextInt();
        double vY1 = scanner.nextInt();
        double vY2 = scanner.nextInt();

        double angle = Math.abs(Math.atan2(vX2, vX1) - Math.atan2(vY2, vY1));

        System.out.println(Math.toDegrees(angle));
    }
}
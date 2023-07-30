import java.time.LocalTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        LocalTime first = LocalTime.parse(scanner.nextLine());
        LocalTime second = LocalTime.parse(scanner.nextLine());

        System.out.println(Math.abs(first.toSecondOfDay() - second.toSecondOfDay()));
    }
}

import java.time.LocalDate;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] date = scanner.nextLine().split("-");

        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);

        LocalDate localDate = LocalDate.of(year, month, day);

        System.out.printf("%d %d", localDate.getDayOfYear(), localDate.getDayOfMonth());
    }
}
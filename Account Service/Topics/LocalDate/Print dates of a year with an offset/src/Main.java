import java.time.LocalDate;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] date = scanner.nextLine().split("-");
        int offset = scanner.nextInt();

        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);

        LocalDate localDate = LocalDate.of(year, month, day);

        while (localDate.getYear() == year) {
            System.out.println(localDate);

            localDate = localDate.plusDays(offset);
        }
    }
}
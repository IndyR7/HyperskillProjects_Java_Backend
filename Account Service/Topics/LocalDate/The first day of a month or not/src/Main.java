import java.time.LocalDate;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split(" ");

        int year = Integer.parseInt(input[0]);
        int day = Integer.parseInt(input[1]);

        LocalDate date = LocalDate.ofYearDay(year, day);

        boolean isFirstDayOfMonth = date.getDayOfMonth() == 1;

        System.out.println(isFirstDayOfMonth);
    }
}
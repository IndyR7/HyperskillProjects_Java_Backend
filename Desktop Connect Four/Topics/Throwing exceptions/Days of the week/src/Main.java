import java.util.*;

public class Main {

    public static String getDayOfWeekName(int number) {
        return number == 1 ? "Mon"
                : number == 2 ? "Tue"
                : number == 3 ? "Wed"
                : number == 4 ? "Thu"
                : number == 5 ? "Fri"
                : number == 6 ? "Sat"
                : number == 7 ? "Sun"
                : String.valueOf(new IllegalArgumentException());
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dayNumber = scanner.nextInt();
        try {
            System.out.println(getDayOfWeekName(dayNumber));
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }
    }
}
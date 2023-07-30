import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] input = scanner.nextLine().split(" ");

        String longest = Arrays
                .stream(input)
                .max(Comparator.comparingInt(String::length)).orElse("");

        System.out.println(longest);
    }
}
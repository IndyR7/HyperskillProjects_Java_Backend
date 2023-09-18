import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> list1 = List.of(scanner.nextLine().split(" "));
        List<String> list2 = List.of(scanner.nextLine().split(" "));

        System.out.printf("%d %d", Collections.indexOfSubList(list1, list2),
                Collections.lastIndexOfSubList(list1, list2));
    }
}
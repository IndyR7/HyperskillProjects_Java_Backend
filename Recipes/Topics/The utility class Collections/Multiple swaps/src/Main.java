import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> list = Arrays.asList(scanner.nextLine().split(" "));
        int times = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < times; i++) {
            String[] input = scanner.nextLine().split(" ");

            Collections.swap(list, Integer.parseInt(input[0]), Integer.parseInt(input[1]));
        }

        System.out.println(String.join(" ", list));
    }
}
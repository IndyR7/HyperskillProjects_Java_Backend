import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] minMax = scanner.nextLine().split(" ");
        int min = Integer.parseInt(minMax[0]);
        int max = Integer.parseInt(minMax[1]);
        int times = Integer.parseInt(scanner.nextLine());
        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < times; i++) {
            String[] input = scanner.nextLine().split(" ");
            int key = Integer.parseInt(input[0]);
            String value = input[1];

            map.put(key, value);
        }

        map.entrySet().stream()
                .filter(entry -> entry.getKey() >= min && entry.getKey() <= max)
                .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
    }
}
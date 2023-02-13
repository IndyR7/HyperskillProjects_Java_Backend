import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> map = new HashMap<>();
        for (String k : scanner.nextLine().split("")) {
            map.put(k.toLowerCase(), map.getOrDefault(k, 0) + 1);
        }
        for (String k : scanner.nextLine().split("")) {
            map.put(k.toLowerCase(), map.getOrDefault(k, 0) - 1);
        }
        System.out.println(map.values().stream().mapToInt(Math::abs).sum());
    }
}

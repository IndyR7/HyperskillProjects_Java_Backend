import java.util.*;

class MapUtils {

    public static SortedMap<String, Integer> wordCount(String[] strings) {
        SortedMap<String, Integer> sortedMap = new TreeMap<>();
        for (String s : strings) {
            sortedMap.put(s, sortedMap.getOrDefault(s, 0) + 1);
        }
        return sortedMap;
    }

    public static void printMap(Map<String, Integer> map) {
        map.forEach((k, v) -> System.out.printf("%s : %d\n", k, v));
    }

}

/* Do not change code below */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] words = scanner.nextLine().split(" ");
        MapUtils.printMap(MapUtils.wordCount(words));
    }
}

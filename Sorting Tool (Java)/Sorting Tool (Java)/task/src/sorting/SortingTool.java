package sorting;

import java.util.*;

public class SortingTool {

    public static <T extends Comparable<T>> void sortList(List<T> list) {
        Comparator<T> comparator = Comparator.naturalOrder();
        list.sort(comparator);
    }

    public static <T> String getNaturalSortedString(List<T> list, String type) {
        StringBuilder sbNaturalSorted = new StringBuilder().append("Sorted data: ");

        for (T element : list) {
            if (type.equals("line")) {
                sbNaturalSorted.append("\n").append(element);
                continue;
            }

            sbNaturalSorted.append(element).append(" ");
        }

        return sbNaturalSorted.toString();
    }

    public static <T> String getSortedStringByCount(List<T> list) {
        Map<Integer, List<T>> sortedMap = getSortedMapByCount(list);
        StringBuilder sbSortedByCount = new StringBuilder();

        for (int countKey : sortedMap.keySet()) {
            int percentage = 100 * countKey / list.size();

            for (T element : sortedMap.get(countKey)) {
                sbSortedByCount.append(element)
                        .append(": ")
                        .append(countKey)
                        .append(" times(s), ")
                        .append(percentage)
                        .append("%\n");
            }
        }

        return sbSortedByCount.toString();
    }

    private static <T> Map<Integer, List<T>> getSortedMapByCount(List<T> list) {
        T current = list.get(0);
        Map<Integer, List<T>> sortedMap = new TreeMap<>();
        int count = 1;

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).equals(current)) {
                count++;
                continue;
            }

            sortedMap.putIfAbsent(count, new ArrayList<>());
            sortedMap.get(count).add(current);

            current = list.get(i);
            count = 1;
        }

        sortedMap.putIfAbsent(count, new ArrayList<>());
        sortedMap.get(count).add(current);

        return sortedMap;
    }
}
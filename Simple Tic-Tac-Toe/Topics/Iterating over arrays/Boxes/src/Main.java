
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] input1 = scanner.nextLine().split(" ");
        String[] input2 = scanner.nextLine().split(" ");

        int[] box1 = parseToIntArray(input1);
        int[] box2 = parseToIntArray(input2);

        sort(box1, box2);

        int compared = compare(box1, box2);

        System.out.print(compared == -3 ? "Box 1 < Box 2" : compared == 3 ? "Box 1 > Box 2" : "Incompatible");
    }

    public static void sort(int[] box1, int[] box2) {
        Arrays.sort(box1);
        Arrays.sort(box2);
    }

    public static int[] parseToIntArray(String[] input) {
        int[] box = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            box[i] = Integer.parseInt(input[i]);
        }
        return box;
    }

    public static int compare(int[] box1, int[] box2) {
        int compared = 0;
        for (int i = 0; i < box1.length; i++) {
            compared = box1[i] > box2[i] ? compared + 1 : box1[i] < box2[i] ? compared - 1 : compared;
        }
        return compared;
    }
}
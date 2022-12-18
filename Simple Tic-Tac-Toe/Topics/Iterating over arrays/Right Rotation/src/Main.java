import java.util.Scanner;
import java.util.Arrays;

class Main {
    // implement me
    private static void rotate(int[] arr, int steps) {
        for (int i = 0; i < steps; i++) {
            int last = arr[arr.length - 1];
            for (int j = arr.length - 1; j >= 0; j--) {
                if (j == 0) {
                    arr[0] = last;
                    break;
                }
                arr[j] = arr[j - 1];
            }
        }
    }

    // do not change code below
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        int steps = Integer.parseInt(scanner.nextLine());

        rotate(arr, steps);

        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
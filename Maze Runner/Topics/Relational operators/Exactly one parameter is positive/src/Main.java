import java.util.Scanner;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Boolean> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int input = scanner.nextInt();
            if (input > 0) {
                list.add(true);
            }
        }
        if (list.isEmpty() || list.size() > 1) {
            System.out.print(false);
        }
        else {
            System.out.print(true);
        }
    }
}

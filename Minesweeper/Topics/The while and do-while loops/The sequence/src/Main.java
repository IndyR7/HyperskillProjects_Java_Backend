import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
		int count = 0;
		int max = 0;
		while (count < length) {
			int input = scanner.nextInt();
			max = input % 4 == 0 && input > max ? input : max;
            count++;
		}
		System.out.println(max);
	}
}


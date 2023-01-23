import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
		int i = 1;
		while(i * i <= input) {
			System.out.println(i*i);
			i++;
        }
    }
}

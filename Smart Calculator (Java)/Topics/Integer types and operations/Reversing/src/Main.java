import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
     		int input = scanner.nextInt();
		int a = input % 10;
		int b = input / 10 % 10;
		int c = input / 100 % 10;
		if (!(a == 0)) {
			System.out.print(a);
		}
    	System.out.print(b);
		System.out.print(c);
	}
}


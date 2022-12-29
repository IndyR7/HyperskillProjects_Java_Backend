package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UI userInterface = new UI(scanner);
        userInterface.start();
    }
}

import java.util.EnumSet;
import java.util.Scanner;

public class Main {

    enum Alphabets {
        A, B, E, O, T, U, L, I
    }

    EnumSet<Alphabets> enumSet;

        public static void main(String[] args) {
        Main object = new Main();

        Scanner sc = new Scanner(System.in);
        String string = sc.nextLine().trim();

        // Change Code below this line
        switch (string) {
            case "setOfVowels":
                break;
            case "setOfConsonants":
                break;
            case "containsAOnly":
                break;
            case "empty":
                break;
            case "exceptT-A-E":
                break;
            default : System.out.println("ERROR");
                break;
       }
        System.out.println(object.enumSet);
        // Change the code above this line
    }
}
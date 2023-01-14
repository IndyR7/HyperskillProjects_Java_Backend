package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Scanner fileReader = new Scanner(new File("rating.txt"))) {
            Ratings ratings = new Ratings(fileReader);
            UI userInterface = new UI(scanner, ratings);
            userInterface.start();
        } catch (Exception e) {
            System.out.printf("Error: %s", e.getMessage());
        }
    }
}

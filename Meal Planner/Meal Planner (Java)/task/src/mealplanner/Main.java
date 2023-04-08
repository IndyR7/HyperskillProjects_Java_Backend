package mealplanner;

import java.util.Scanner;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        UI userInterface = new UI(scanner);

        Database.setDatabase();
        Database.setPlan();

        userInterface.start();
    }
}
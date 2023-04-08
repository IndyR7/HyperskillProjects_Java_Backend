package mealplanner;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class FileHandler {
    public static void save(String file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(Database.getShoppingList());
            fileWriter.close();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

package rockpaperscissors;

import java.util.HashMap;
import java.util.Scanner;

public class Ratings {
    private final HashMap<String, Integer> ratings;

    public Ratings(Scanner fileReader) {
        this.ratings = initRatings(fileReader);
    }

    public HashMap<String, Integer> initRatings(Scanner fileReader) {
        HashMap<String, Integer> ratings = new HashMap<>();
        while (fileReader.hasNext()) {
            String[] userRating = fileReader.nextLine().split(" ");
            ratings.put(userRating[0], Integer.parseInt(userRating[1]));
        }
        return ratings;
    }

    public int getRating(String name) {
        for (String user : ratings.keySet()) {
            if (user.equals(name)) {
                return ratings.get(name);
            }
        }
        return 0;
    }

    public void update(String name, int points) {
        ratings.put(name, ratings.getOrDefault(name, 0) + points);
    }
}

package rockpaperscissors;

import java.util.*;

public class Game {
    private final Scanner scanner;
    private final String[] options;
    private final HashMap<String, ArrayList<String>> ranking;
    private boolean isRunning;
    private final String user;
    private Ratings ratings;

    public Game(Scanner scanner, String options, String user, Ratings ratings) {
        this.scanner = scanner;
        isRunning = true;
        if (options.equals("")) {
            this.options = new String[]{"rock", "paper", "scissors"};
        } else {
            this.options = options.split(",");
        }
        this.ranking = initRanking();
        this.user = user;
        this.ratings = ratings;
    }

    public void play() {
        String input = scanner.nextLine();
        if (input.equals("!exit")) {
            System.out.println("Bye!");
            this.isRunning = false;
        } else if (input.equals("!rating")) {
            System.out.printf("Your rating: %d\n", ratings.getRating(user));
        } else if (Arrays.stream(this.options).toList().contains(input)) {
            System.out.println(getResult(input, AIMove()));
        } else {
            System.out.println("Invalid input");
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    private String AIMove() {
        int index = new Random().nextInt(0, options.length);
        return this.options[index];
    }

    private String getResult(String userMove, String AIMove) {
        if (userMove.equals(AIMove)) {
            ratings.update(user, 50);
            return String.format("There is a draw (%s)", userMove);
        }
        if (!ranking.get(userMove).contains(AIMove)) {
            ratings.update(user, 100);
            return String.format("Well done. The computer chose %s and failed", AIMove);
        }
        return String.format("Sorry, but the computer chose %s", AIMove);
    }

    private HashMap<String, ArrayList<String>> initRanking() {
        HashMap<String, ArrayList<String>> ranking = new HashMap<>();
        for (int i = 0; i < options.length; i++) {
            ranking.put(options[i], new ArrayList<>());
            int cutoff = options.length / 2;
            int indexOfBetterMove = i + 1;
            for (int j = 0; j < cutoff; j++) {
                if (indexOfBetterMove == options.length) {
                    indexOfBetterMove = 0;
                }
                ranking.get(options[i]).add(options[indexOfBetterMove]);
                indexOfBetterMove++;
            }
        }
        return ranking;
    }
}

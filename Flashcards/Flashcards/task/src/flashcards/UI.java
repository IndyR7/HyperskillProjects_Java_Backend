package flashcards;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class UI {
    private final Scanner scanner;
    private final LinkedHashMap<String, String> cards;
    private final HashMap<String, Integer> wrongAnswers;
    private final StringBuilder log;

    public UI(Scanner scanner) {
        this.scanner = scanner;
        this.cards = new LinkedHashMap<>();
        this.wrongAnswers = new HashMap<>();
        this.log = new StringBuilder();
    }

    public void start(String firstCommand, String firstFile, String secondCommand, String secondFile) {
        if (firstCommand.contains("import")) {
            importCards(firstFile);
        }
        if (secondCommand.contains("import")) {
            importCards(secondFile);
        }

        do {
            String menu = "Input the action (add, remove, import, export, ask, exit, log, " +
                    "hardest card, reset stats)";
            System.out.println(menu);
            log.append(menu).append("\n");
        } while (isRunning(scanner.nextLine()));

        System.out.println("Bye bye!");

        if (firstCommand.contains("export")) {
            export(firstFile);
        }
        if (secondCommand.contains("export")) {
            export(secondFile);
        }
    }

    private boolean isRunning(String operation) {
        log.append(operation).append("\n");

        switch (operation.toLowerCase()) {
            case "add" -> {
                addCards();
                return true;
            }
            case "remove" -> {
                remove();
                return true;
            }
            case "import" -> {
                importCards(null);
                return true;
            }
            case "export" -> {
                export(null);
                return true;
            }
            case "ask" -> {
                ask();
                return true;
            }
            case "log" -> {
                log();
                return true;
            }
            case "hardest card" -> {
                System.out.println(getHardestCard());
                log.append(getHardestCard()).append("\n");
                return true;
            }
            case "reset stats" -> {
                resetStats();
                return true;
            }
            case "exit" -> {
                return false;
            }
            default -> {
                System.out.println("The is no such operation!\n");
                return true;
            }
        }
    }

    private void addCards() {
        System.out.println("Card:");
        log.append("Card:\n");

        String term = scanner.nextLine();

        log.append(term).append("\n");

        if (cards.containsKey(term)) {
            System.out.printf("The card \"%s\" already exists.\n", term);
            log.append(String.format("The card \"%s\" already exists.\n", term));
            return;
        }

        System.out.println("The definition of the card:");
        log.append("The definition of the card:\n");

        String def = scanner.nextLine();

        if (cards.containsValue(def)) {
            System.out.printf("The definition \"%s\" already exists.\n", def);
            log.append(String.format("The definition \"%s\" already exists.\n", def));
            return;
        }
        cards.put(term, def);

        System.out.printf("The pair (\"%s\":\"%s\") has been added.\n", term, def);
        log.append(String.format("The pair (\"%s\":\"%s\") has been added.\n", term, def));
    }

    private void remove() {
        System.out.println("Which card?");
        log.append("Which card?\n");

        String cardToRemove = scanner.nextLine();

        log.append(cardToRemove).append("\n");

        if (cards.containsKey(cardToRemove)) {
            cards.remove(cardToRemove);
            System.out.println("The card has been removed.");
            log.append("The card has been removed.\n");
        } else {
            System.out.printf("Can't remove \"%s\": there is no such card.\n", cardToRemove);
            log.append(String.format("Can't remove \"%s\": there is no such card.\n", cardToRemove));
        }
    }

    private void importCards(String file) {
        try {
            BufferedReader br;

            if (file != null) {
                br = new BufferedReader(new FileReader(file));
            } else {
                System.out.println("File name:");
                log.append("File name:\n");
                br = new BufferedReader(new FileReader(scanner.nextLine()));
            }

            String line;
            int count = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                try {
                    wrongAnswers.put(parts[0], wrongAnswers.getOrDefault(parts[0], 0) + Integer.parseInt(parts[1]));
                } catch (Exception e) {
                    count++;
                    cards.put(parts[0], parts[1]);
                }
            }

            br.close();

            System.out.printf("%d cards have been loaded\n", count);
            log.append(String.format("%d cards have been loaded\n", count));

        } catch (IOException e) {
            System.out.printf("Error: file \"%s\" was not found!\n", e.getMessage());
            log.append(String.format("Error: file \"%s\" was not found!\n", e.getMessage()));
        }
    }

    private void export(String file) {
        try {
            PrintWriter filePrinter;

            if (file != null) {
                filePrinter = new PrintWriter(new FileWriter(file));
            } else {
                System.out.println("File name:");
                log.append("File name:\n");
                filePrinter = new PrintWriter(new FileWriter(scanner.nextLine()));
            }

            filePrinter.flush();

            for (String term : cards.keySet()) {
                filePrinter.append(term).append("=").append(cards.get(term)).append("\n");
            }

            for (String term : wrongAnswers.keySet()) {
                filePrinter.append(term).append("=").append(String.valueOf(wrongAnswers.get(term)));
            }

            filePrinter.close();

            System.out.printf("%d cards have been saved.\n", cards.size());
            log.append(String.format("%d cards have been saved.\n", cards.size()));

        } catch (Exception e) {
            System.out.printf("Error: file \"%s\" was not found!\n", e.getMessage());
            log.append(String.format("Error: file \"%s\" was not found!\n", e.getMessage()));
        }
    }

    private void ask() {
        System.out.println("How many times to ask?");
        log.append("How many times to ask?\n");

        int times = Integer.parseInt(scanner.nextLine());
        int count = 0;

        log.append(times).append("\n");

        loop:
        while (true) {
            for (String card : cards.keySet()) {
                System.out.printf("Print the definition of \"%s\":\n", card);
                log.append(String.format("Print the definition of \"%s\":\n", card));

                String answer = scanner.nextLine();
                String result = getResult(cards, card, answer);

                log.append(answer).append("\n");

                System.out.println(result);
                log.append(result);

                if (++count == times) {
                    break loop;
                }
            }
        }
    }

    private void log() {
        System.out.println("File name:");
        log.append("File name:\n");

        String logFile = scanner.nextLine();

        log.append(logFile).append("\n");
        log.append("Log has been saved successfully.\n");
        log.append(LocalDateTime.now());

        String[] lines = log.toString().split("\n");

        try {
            PrintWriter filePrinter = new PrintWriter(new FileWriter(logFile));
            filePrinter.flush();

            for (String line : lines) {
                filePrinter.append(line).append("\n");
            }
            filePrinter.close();

            System.out.println("The log has been saved.");

        } catch (Exception e) {
            System.out.printf("Error: file \"%s\" was not found!\n", e.getMessage());
            log.append(String.format("Error: file \"%s\" was not found!\n", e.getMessage()));
        }
    }

    private String getHardestCard() {
        int max = 0;
        StringBuilder sbToReturn = new StringBuilder();
        ArrayList<String> wrongTerms = new ArrayList<>();

        if (wrongAnswers.size() == 0) {
            return "There are no cards with errors.";
        }
        for (String term : wrongAnswers.keySet()) {
            if (wrongAnswers.get(term) == max) {
                wrongTerms.add(term);
            }
            if (wrongAnswers.get(term) > max) {
                wrongTerms.clear();
                wrongTerms.add(term);
                max = wrongAnswers.get(term);
            }
        }

        sbToReturn.append("The hardest card");

        if (wrongTerms.size() > 1) {
            sbToReturn.append("s are ");
            for (String term : wrongTerms) {
                sbToReturn.append(String.format("\"%s\", ", term));
            }
            sbToReturn.delete(sbToReturn.length() - 2, sbToReturn.length());
            sbToReturn.append(String.format(". You have %d errors answering them.\n", max));
        } else {
            sbToReturn.append(String.format(" is \"%s\". ", wrongTerms.get(0)))
                    .append(String.format("You have %d errors answering it.\n", max));
        }
        return sbToReturn.toString();
    }

    private void resetStats() {
        wrongAnswers.clear();
        System.out.println("Card statistics have been reset.");
        log.append("Card statistics have been reset.\n");
    }

    private String getResult(LinkedHashMap<String, String> cards, String term, String def) {
        if (cards.get(term).equals(def)) {
            return "Correct!";
        }
        wrongAnswers.put(term, wrongAnswers.getOrDefault(term, 0) + 1);
        for (String k : cards.keySet()) {
            if (cards.get(k).equals(def)) {
                return String.format("Wrong. The right answer is \"%s\", " +
                        "but your definition is correct for \"%s\".", cards.get(term), k);
            }
        }
        return String.format("Wrong. The right answer is \"%s\".", cards.get(term));
    }
}
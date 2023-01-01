package readability;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static java.lang.Math.max;

public class Main {
    public static void main(String[] args) {
        File fileToRead = new File(args[0]);
        Scanner scanner = new Scanner(System.in);
        try (Scanner fileReader = new Scanner(fileToRead)) {
            String text = Files.readString(Path.of(args[0]));
            System.out.printf("The text is:\n%s\n\n", text);
            System.out.printf("Words: %d\nSentences: %d\nCharacters: %d\nSyllables: %d\n" +
                            "Polysyllables: %d\n\nEnter the score you want to calculate " +
                            "(ARI, FK, SMOG, CL, all):",
                    getWordCount(text), getSentenceCount(text), getCharacterCount(text), getSyllableCount(text),
                    getPolySyllableCount(text));
            System.out.println(getResult(scanner.nextLine(), text));
            System.out.printf("\nThis text should be understood in average by %.2f-year-olds.", getAverageAge(text));
        } catch (Exception e) {
            System.out.printf("Error: %s not found", e.getMessage());
        }
    }

    public static String getResult(String command, String text) {
        String ari = String.format("\nAutomated Readability Index: %.2f (about %d-year-olds).",
                getARIScore(text), getAge(getARIScore(text)));
        String fk = String.format("\nFlesch–Kincaid readability tests: %.2f (about %d-year-olds).",
                getFKScore(text), getAge(getFKScore(text)));
        String smog = String.format("\nSimple Measure of Gobbledygook: %.2f (about %d-year-olds).",
                getSMOGScore(text), getAge(getSMOGScore(text)));
        String cli = String.format("\nColeman–Liau index: %.2f (about %d-year-olds).",
                getCLIScore(text), getAge(getCLIScore(text)));
        switch (command) {
            case "ARI":
                return ari;
            case "FK":
                return fk;
            case "SMOG":
                return smog;
            case "CL":
                return cli;
            case "all":
                return String.format("%s%s%s%s", ari, fk, smog, cli);
            default:
                return "There is no such command!";
        }
    }

    public static int getPolySyllableCount(String text) {
        int polySyllableCount = 0;
        String[] words = text.split("\s");
        for (String word : words) {
            int count = max(1, word.toLowerCase()
                    .replaceAll("e$", "")
                    .replaceAll("[aeiouy]{2}", "a")
                    .replaceAll("[aeiouy]{2}", "a")
                    .replaceAll("[^aeiouy]", "")
                    .length());
            if (count > 2) {
                polySyllableCount++;
            }
        }
        return polySyllableCount;
    }

    public static int getSyllableCount(String text) {
        int syllableCount = 0;
        String[] words = text.split("\s");
        for (String word : words) {
            int count = max(1, word.toLowerCase()
                    .replaceAll("e$", "")
                    .replaceAll("[aeiouy]{2}", "a")
                    .replaceAll("[aeiouy]{2}", "a")
                    .replaceAll("[^aeiouy]", "")
                    .length());
            syllableCount += count;
        }
        return syllableCount;
    }

    public static int getWordCount(String text) {
        return text.split("\s").length;
    }

    public static int getSentenceCount(String text) {
        return text.split("[?.!]+").length;
    }

    public static int getCharacterCount(String text) {
        return text.replaceAll("\\s", "").length();
    }

    public static double getARIScore(String text) {
        return 4.71 * getCharacterCount(text) / getWordCount(text) + 0.5 * getWordCount(text) /
                getSentenceCount(text) - 21.43;
    }

    public static double getFKScore(String text) {
        return 0.39 * getWordCount(text) / getSentenceCount(text)
                + 11.8 * getSyllableCount(text) / getWordCount(text) - 15.59;
    }

    public static double getSMOGScore(String text) {
        return 1.043 * Math.sqrt(30.0 * getPolySyllableCount(text) / getSentenceCount(text)) + 3.1291;
    }

    public static double getCLIScore(String text) {
        return 0.0588 * (100.0 * getCharacterCount(text) / getWordCount(text))
                - 0.296 * (100.0 * getSentenceCount(text) / getWordCount(text)) - 15.8;
    }

    public static int getAge(double score) {
        for (Readability level : Readability.values()) {
            if (Math.ceil(score) == level.getScore()) {
                return level.getAge();
            }
        }
        return -1;
    }

    public static double getAverageAge(String text) {
        return (getAge(getARIScore(text)) + getAge(getFKScore(text) + getAge(getSMOGScore(text))
                + getAge(getCLIScore(text)) / 4.0));
    }
}

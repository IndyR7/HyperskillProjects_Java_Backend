package encryptdecrypt;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;

public class UI {
    private final String outputFile;
    private final String mode;
    private final String alg;
    private final int key;
    private final String data;
    private final String letters = "abcdefghijklmnopqrstuvwxyz";

    public UI(Scanner scanner, String[] args) {
        this.mode = getMode(args);
        this.key = getKey(args);
        this.data = getInput(args);
        this.outputFile = getOutputFile(args);
        this.alg = getAlg(args);
    }

    public void start() {
        if (!containsOutputFile()) {
            printToUserInterface();
            return;
        }
        printToFile();
    }

    private boolean containsOutputFile() {
        return !this.outputFile.isEmpty();
    }

    private void printToUserInterface() {
        switch (this.mode) {
            case "enc" -> System.out.println(getEncryptedString(this.data, this.key));
            case "dec" -> System.out.println(getDecryptedString(this.data, this.key));
            default -> System.out.println("There is no such command!");
        }
    }

    private void printToFile() {
        switch (this.mode) {
            case "enc" -> {
                try (PrintWriter pw = new PrintWriter(this.outputFile)) {
                    pw.println(getEncryptedString(this.data, this.key));
                } catch (Exception e) {
                    System.out.printf("Error: %s not found!", e.getMessage());
                }
            }
            case "dec" -> {
                try (PrintWriter pw = new PrintWriter(this.outputFile)) {
                    pw.println(getDecryptedString(this.data, this.key));
                } catch (Exception e) {
                    System.out.printf("Error: %s not found!", e.getMessage());
                }
            }
            default -> System.out.println("There is no such command!");
        }
    }

    private String getEncryptedString(String text, int key) {
        return this.alg.equals("unicode") ? getUnicodeEncryptedString(text, key)
                : getShiftEncryptedString(text, key);
    }

    private String getDecryptedString(String text, int key) {
        return this.alg.equals("unicode") ? getUnicodeDecryptedString(text, key)
                : getShiftDecryptedString(text, key);
    }

    private String getShiftEncryptedString(String text, int key) {
        StringBuilder encryptedString = new StringBuilder();
        for (String symbol : text.split("")) {
            if (!letters.contains(symbol.toLowerCase())) {
                encryptedString.append(symbol);
                continue;
            }
            int i = getShiftIndex(letters.indexOf(symbol.toLowerCase()) + key);
            char c = isUpperCase(symbol) ? Character.toUpperCase(letters.charAt(i))
                    : letters.charAt(i);
            encryptedString.append(c);
        }
        return encryptedString.toString();
    }

    private String getShiftDecryptedString(String text, int key) {
        StringBuilder decryptedString = new StringBuilder();
        for (String symbol : text.split("")) {
            if (!letters.contains(symbol.toLowerCase())) {
                decryptedString.append(symbol);
                continue;
            }
            int i = getShiftIndex(letters.indexOf(symbol.toLowerCase()) - key);
            char c = isUpperCase(symbol) ? Character.toUpperCase(letters.charAt(i))
                    : letters.charAt(i);
            decryptedString.append(c);
        }
        return decryptedString.toString();
    }

    private int getShiftIndex(int i) {
        while (i >= letters.length()) {
            i -= letters.length();
        }
        while (i < 0) {
            i += letters.length();
        }
        return i;
    }

    private String getUnicodeEncryptedString(String text, int key) {
        StringBuilder encryptedString = new StringBuilder();
        for (char symbol : text.toCharArray()) {
            char c = (char) getUnicodeIndex(symbol + key);
            encryptedString.append(c);
        }
        return encryptedString.toString();
    }

    private String getUnicodeDecryptedString(String text, int key) {
        StringBuilder encryptedString = new StringBuilder();
        for (char symbol : text.toCharArray()) {
            char c = (char) getUnicodeIndex(symbol - key);
            encryptedString.append(c);
        }
        return encryptedString.toString();
    }

    private int getUnicodeIndex(int i) {
        while (i > 255) {
            i -= 255;
        }
        while (i < 0) {
            i += 255;
        }
        return i;
    }

    private String getAlg(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("alg")) {
                return args[i + 1];
            }
        }
        return "shift";
    }

    private String getOutputFile(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("out")) {
                return args[i + 1];
            }
        }
        return "";
    }

    private String getInput(String[] args) {
        if (Arrays.asList(args).contains("data")) {
            return getData(args);
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("in")) {
                try {
                    return Files.readString(Path.of(args[i + 1]));
                } catch (Exception e) {
                    System.out.printf("Error: %s not found", e.getMessage());
                }
            }
        }
        return "";
    }

    private String getData(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("data")) {
                return args[i + 1];
            }
        }
        return "";
    }

    private int getKey(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("key")) {
                return Integer.parseInt(args[i + 1]);
            }
        }
        return 0;
    }

    private static String getMode(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("mode")) {
                return args[i + 1];
            }
        }
        return "enc";
    }

    private boolean isUpperCase(String symbol) {
        return Character.isUpperCase(symbol.charAt(0));
    }
}

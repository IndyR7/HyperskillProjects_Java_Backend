package asciimirror;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class UI {
    private final Scanner scanner;

    public UI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("Input the file path:");
        try {
            String file = Files.readString(Path.of(scanner.nextLine()));
            int max = Arrays.stream(file.split("\n")).max(Comparator.comparingInt(String::length)).get().length();
            for (String line : file.split("\n")) {
                StringBuilder sbLine = new StringBuilder().append(line);
                if (line.length() < max) {
                    sbLine.append(getSpaces(line.length(), max));
                }
                System.out.printf("%s | %s\n", sbLine, getReversed(sbLine.toString()));
            }
        } catch (Exception e) {
            System.out.println("File not found!");
        }
    }

    private String getSpaces(int length, int max) {
        return " ".repeat(Math.max(0, max - length));
    }

    private String getReversed(String line) {
        String[] chars = line.split("");
        StringBuilder sbReversed = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            switch (chars[i]) {
                case "<" -> chars[i] = ">";
                case ">" -> chars[i] = "<";
                case "[" -> chars[i] = "]";
                case "]" -> chars[i] = "[";
                case "{" -> chars[i] = "}";
                case "}" -> chars[i] = "{";
                case "(" -> chars[i] = ")";
                case ")" -> chars[i] = "(";
                case "/" -> chars[i] = "\\";
                case "\\" -> chars[i] = "/";
            }
            sbReversed.append(chars[i]);
        }
        return sbReversed.reverse().toString();
    }
}

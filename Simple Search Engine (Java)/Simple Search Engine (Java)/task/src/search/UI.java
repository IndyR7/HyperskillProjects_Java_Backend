package search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class UI {
    private final Scanner scanner;

    public UI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start(String file) throws IOException {
        String[] persons = Files.readString(Path.of(file)).split("\n");
        do {
            System.out.println(getMenu());
        } while (isPerformingOperation(persons, Integer.parseInt(scanner.nextLine())));
    }

    private boolean isPerformingOperation(String[] persons, int option) {
        switch (option) {
            case 1 -> {
                System.out.println("Select a matching strategy: ALL, ANY, NONE");
                String input = scanner.nextLine();
                System.out.println("\nEnter a name or email to search all suitable people.");
                System.out.println(getPersons(persons, scanner.nextLine(), input));
            }
            case 2 -> printPeople(persons);
            case 0 -> {
                System.out.println("\nBye!");
                return false;
            }
            default -> System.out.println("Incorrect option! Try again.\n");
        }
        return true;
    }

    private void printPeople(String[] persons) {
        System.out.println("=== List of people ===");
        Arrays.asList(persons).forEach(System.out::println);
    }

    private String getMenu() {
        return "=== Menu ===\n1. Find a person\n2. Print all people\n0. Exit";
    }

    private String getPersons(String[] persons, String searched, String query) {
        String foundPersons = "";
        switch (query.toLowerCase()) {
            case "all" -> foundPersons = getAll(persons, searched);
            case "any" -> foundPersons = getAny(persons, searched);
            case "none" -> foundPersons = getNone(persons, searched);
            default -> {
            }
        }
        return foundPersons.isEmpty() ? "No matching people found.\n"
                : String.format("%d persons found:\n%s", foundPersons.split("\n").length,
                foundPersons);
    }


    private String getAll(String[] persons, String searched) {
        StringBuilder foundPersons = new StringBuilder();
        String[] searchedVars = searched.split(" ");
        boolean found = true;
        for (String person : persons) {
            for (String searchedVar : searchedVars) {
                if (!person.toLowerCase().contains(searchedVar.toLowerCase())) {
                    found = false;
                    break;
                }
            }
            if (found) {
                foundPersons.append(String.format("%s\n", person));
            }
            found = true;
        }
        return foundPersons.toString();
    }

    private String getAny(String[] persons, String searched) {
        StringBuilder foundPersons = new StringBuilder();
        String[] searchedVars = searched.split(" ");
        boolean found = false;
        for (String person : persons) {
            for (String searchedVar : searchedVars) {
                if (person.toLowerCase().contains(searchedVar.toLowerCase())) {
                    found = true;
                    break;
                }
            }
            if (found) {
                foundPersons.append(String.format("%s\n", person));
            }
            found = false;
        }
        return foundPersons.toString();
    }

    private String getNone(String[] persons, String searched) {
        StringBuilder foundPersons = new StringBuilder();
        String[] searchedVars = searched.split(" ");
        boolean found = false;
        for (String person : persons) {
            for (String searchedVar : searchedVars) {
                if (person.toLowerCase().contains(searchedVar.toLowerCase())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                foundPersons.append(String.format("%s\n", person));
            }
            found = false;
        }
        return foundPersons.toString();
    }
}
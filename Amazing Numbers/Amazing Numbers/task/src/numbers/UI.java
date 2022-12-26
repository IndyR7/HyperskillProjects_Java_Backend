package numbers;

import java.util.Scanner;
import java.util.ArrayList;

public class UI {
    private final Scanner scanner;

    public UI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("Welcome to Amazing Numbers!\n\nSupported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "* the first parameter represents a starting number;\n" +
                "* the second parameter shows how many consecutive numbers are to be processed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.\n");
        while (true) {
            System.out.println("Enter a request:");
            String[] request = scanner.nextLine().toLowerCase().split(" ");
            if (!isLegalCommand(request)) {
                printError(request);
                continue;
            }
            if (Long.parseLong(request[0]) == 0) {
                System.out.println("Goodbye!");
                break;
            }
            if (request.length == 1) {
                Numbers number = new Numbers(Long.parseLong(request[0]));
                System.out.println(number.firstResult());
                continue;
            }
            if (request.length == 2) {
                long toPrint = Long.parseLong(request[0]);
                for (long i = 0; i < Long.parseLong(request[1]); i++) {
                    Numbers number = new Numbers(toPrint);
                    System.out.println(number.secondResult());
                    toPrint++;
                }
                continue;
            }
            printNumbersEqualToProperties(request);
        }
    }

    private boolean isLegalCommand(String[] request) {
        return firstIndexIsNatural(request) && secondIndexIsNatural(request) && areExistingProperties(request)
                && !containsMutuallyExclusivePairs(request);
    }

    private boolean containsMutuallyExclusivePairs(String[] request) {
        return getMutuallyExclusivePairs(request).size() > 0;
    }

    private ArrayList<String> getMutuallyExclusivePairs(String[] request) {
        ArrayList<String> oppositePairs = new ArrayList<>();
        for (String property : request) {
            for (String toCheck : request) {
                if (property.equals("even") && toCheck.equals("odd")
                        || property.equals("odd") && toCheck.equals("even")
                        || property.equals("-even") && toCheck.equals("-odd")
                        || property.equals("-odd") && toCheck.equals("-even")
                        || property.equals("duck") && toCheck.equals("spy")
                        || property.equals("spy") && toCheck.equals("duck")
                        || property.equals("-duck") && toCheck.equals("-spy")
                        || property.equals("-spy") && toCheck.equals("-duck")
                        || property.equals("square") && toCheck.equals("sunny")
                        || property.equals("sunny") && toCheck.equals("square")
                        || property.equals("-square") && toCheck.equals("-sunny")
                        || property.equals("-sunny") && toCheck.equals("-square")
                        || property.equals("happy") && toCheck.equals("sad")
                        || property.equals("sad") && toCheck.equals("happy")
                        || property.equals("-happy") && toCheck.equals("-sad")
                        || property.equals("-sad") && toCheck.equals("-happy")
                        || property.equals(oppositePair(toCheck))) {
                    if (!oppositePairs.contains(property.toUpperCase())) {
                        oppositePairs.add(property.toUpperCase());
                        oppositePairs.add(toCheck.toUpperCase());
                    }
                }
            }
        }
        return oppositePairs;
    }

    private boolean areExistingProperties(String[] request) {
        for (int i = 2; i < request.length; i++) {
            if (!isExistingProperty(request[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean isExistingProperty(String property) {
        return existingProperties().contains(property);
    }

    private boolean secondIndexIsNatural(String[] request) {
        if (request.length == 1) {
            return true;
        }
        try {
            return Long.parseLong(request[1]) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean firstIndexIsNatural(String[] request) {
        try {
            return Long.parseLong(request[0]) >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean numberAssertsToCommand(String[] request, Numbers number) {
        ArrayList<String> propertiesToExclude = new ArrayList<>();
        for (int i = 2; i < request.length; i++) {
            if (request[i].startsWith("-")) {
                propertiesToExclude.add(request[i]);
                continue;
            }
            if (!number.secondResult().contains(request[i])) {
                return false;
            }
        }
        for (String property : propertiesToExclude) {
            if (number.secondResult().contains(property.replace("-", ""))) {
                return false;
            }
        }
        return true;
    }

    private String getWrongProperties(String[] request) {
        StringBuilder sbToReturn = new StringBuilder();
        ArrayList<String> wrongProperties = new ArrayList<>();
        for (int i = 2; i < request.length; i++) {
            if (!isExistingProperty(request[i])) {
                wrongProperties.add(request[i].toUpperCase());
            }
        }
        if (wrongProperties.size() > 1) {
            return sbToReturn.append("The properties ")
                    .append(wrongProperties)
                    .append(" are wrong.\nAvailable properties: ")
                    .append("[EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]\n")
                    .toString();
        }
        return sbToReturn.append("The property ")
                .append(wrongProperties)
                .append(" is wrong.\nAvailable properties: ")
                .append("[EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]\n")
                .toString();
    }

    private String existingProperties() {
        return "-buzz, -duck, -palindromic, -gapful, -spy, -square, -sunny, -jumping, -happy, -sad, -even, -odd";
    }

    private String oppositePair(String property) {
        if (property.contains("-")) {
            return property.replace("-", "");
        }
        return "-" + property;
    }

    private void printError(String[] request) {
        StringBuilder errorMessage = new StringBuilder();
        if (!firstIndexIsNatural(request)) {
            errorMessage.append("The first parameter should be a natural number or zero.\n");
        }
        if (!secondIndexIsNatural(request)) {
            errorMessage.append("The second parameter should be a natural number.\n");
        }
        if (!areExistingProperties(request)) {
            errorMessage.append(getWrongProperties(request));
        }
        if (containsMutuallyExclusivePairs(request)) {
            errorMessage.append("The request contains mutually exclusive properties: ")
                    .append(getMutuallyExclusivePairs(request))
                    .append("\nThere are no numbers with these properties.\n");
        }
        System.out.println(errorMessage);
    }

    private void printNumbersEqualToProperties(String[] request) {
        int count = 0;
        long toPrint = Long.parseLong(request[0]);
        while (count < Long.parseLong(request[1])) {
            Numbers number = new Numbers(toPrint);
            if (numberAssertsToCommand(request, number)) {
                System.out.println(number.secondResult());
                count++;
            }
            toPrint++;
        }
    }
}
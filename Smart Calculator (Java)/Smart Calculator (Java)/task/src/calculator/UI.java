package calculator;

import java.math.BigInteger;
import java.util.*;

public class UI {
    private final Scanner scanner;
    private final Map<String, BigInteger> vars;

    public UI(Scanner scanner) {
        this.scanner = scanner;
        vars = new HashMap<>();
    }

    public void start() {
        String command;
        do {
            command = scanner.nextLine();
            if (isDeclaration(command)) {
                String[] dec = command.replaceAll("\\s", "").split("=");
                try {
                    vars.put(dec[0], new BigInteger(dec[1]));
                } catch (Exception e) {
                    vars.put(dec[0], vars.get(dec[1]));
                }
                continue;
            }
            String toPrint;
            if (!command.isEmpty()) {
                try {
                    toPrint = String.valueOf(getResult(command));
                } catch (Exception e) {
                    toPrint = command.equals("/exit") ? "Bye!"
                            : command.equals("/help") ? "This is a calculator"
                            : errorMessage(command);
                }
                System.out.println(toPrint);
            }
        }
        while (!command.equals("/exit"));
    }

    private BigInteger getResult(String command) {
        String[] tokens = getPostfix(command).split(" ");
        Stack<BigInteger> stack = new Stack<>();
        for (String token : tokens) {
            if (token.matches("-?\\d+")) {
                stack.push(new BigInteger(token));
                continue;
            }
            BigInteger first = stack.pop();
            BigInteger second = stack.size() == 0 ? BigInteger.ZERO : stack.pop();
            BigInteger result = token.equals("+") ? second.add(first)
                    : token.equals("-") ? second.subtract(first)
                    : token.equals("*") ? second.multiply(first)
                    : second.divide(first);
            stack.push(result);
        }
        return stack.pop();
    }

    private String getPostfix(String command) {
        // returns the postfix notation of the given expression
        StringBuilder postfix = new StringBuilder();
        Stack<String> stack = new Stack<>();
        String[] tokens = getValidCommand(command).split(" ");
        for (String token : tokens) {
            if (token.matches("-?\\d+|A-Z|a-z")) {
                postfix.append(String.format("%s ", token));
                continue;
            }
            if (stack.isEmpty() || stack.peek().equals("(")) {
                stack.push(token);
                continue;
            }
            if (token.equals("(")) {
                stack.push("(");
                continue;
            }
            if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    String operator = stack.pop();
                    postfix.append(String.format("%s ", operator));
                }
                if (stack.isEmpty()) {
                    return "Invalid expression";
                }
                stack.pop();
                continue;
            }
            if (isOfHigherPrecedence(token, stack.peek())) {
                while (!stack.peek().equals("(") && isOfHigherPrecedence(token, stack.peek())) {
                    String operator = stack.pop();
                    postfix.append(String.format("%s ", operator));
                    if (stack.isEmpty()) {
                        break;
                    }
                }
            }
            stack.push(token);
        }
        while (!stack.isEmpty()) {
            if (stack.peek().equals("(")) {
                return "Invalid expression";
            }
            postfix.append(String.format("%s ", stack.pop()));
        }
        return postfix.toString();
    }

    private String getValidCommand(String command) {
        // returns a command in correct notation (e.g. ( 5 + 7 ) * 2 - 9)
        if (command.contains("**") || command.contains("//")) {
            return "Invalid expression";
        }
        command = command.replaceAll("--", " + ");
        command = command.replaceAll("\\+\\++", " + ");
        command = command.replaceAll("\\+-+", " - ");
        command = command.replaceAll("-\\++", " - ");
        command = command.replaceAll("\\(", " ( ");
        command = command.replaceAll("\\)", " ) ");
        command = command.replaceAll("\\*", " * ");
        command = command.replaceAll("/", " / ");
        command = command.replaceAll("\\s\\s+", " ");
        command = command.trim();
        //converts variables to their numerical value
        for (String var : vars.keySet()) {
            command = command.replaceAll(var, String.format("%d", vars.get(var)));
        }
        return command;
    }

    private String errorMessage(String command) {
        // returns an error message, based on which rule isn't fulfilled
        return command.replaceAll("\\s", "").startsWith("/") ? "Unknown command"
                : command.matches("\\W+=\\d*") ? "Invalid identifier"
                : command.matches("\\w+=\\D+") ? "Invalid assignment"
                : command.matches("\\w+") ? "Unknown variable"
                : "Invalid expression";
    }

    private boolean isOfHigherPrecedence(String current, String previous) {
        Map<String, Integer> rank = new HashMap<>();
        rank.put("+", 1);
        rank.put("-", 1);
        rank.put("*", 2);
        rank.put("/", 2);
        rank.put("^", 3);
        return rank.get(current) <= rank.get(previous);
    }

    private boolean isDeclaration(String command) {
        // checks whether the given command is of the declaring type (e.g. a = 5 or b = a)
        if (command.replaceAll("\\s", "").matches("[a-zA-Z]+=-?\\d+")) {
            return true;
        }
        String key = command.contains("=") ? command.replaceAll("\\s", "").split("=")[1] : "";
        return vars.containsKey(key);
    }
}
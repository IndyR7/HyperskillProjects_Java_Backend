package calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Calc {
    protected static BigDecimal getResult(String command) {
        String[] tokens = getPostfix(command).split(" ");
        Stack<BigDecimal> stack = new Stack<>();
        for (String token : tokens) {
            if (token.matches("-?\\d+|-?\\d+\\.\\d+")) {
                stack.push(new BigDecimal(token));
                continue;
            }
            BigDecimal first = stack.pop();
            BigDecimal second = stack.size() == 0 ? BigDecimal.ZERO : stack.pop();
            BigDecimal result = token.equals("+") ? second.add(first)
                    : token.equals("-") ? second.subtract(first)
                    : token.equals("*") ? second.multiply(first)
                    : token.equals("/") ? second.divide(first).setScale(2, RoundingMode.HALF_EVEN)
                    : second.pow(first.intValue());
            stack.push(result);
        }
        return stack.pop();
    }

    private static String getPostfix(String command) {
        // returns the postfix notation of the given expression
        StringBuilder postfix = new StringBuilder();
        Stack<String> stack = new Stack<>();
        String[] tokens = getValidCommand(command).split(" ");
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.matches("-?\\d+|-?\\d+\\.\\d+")) {
                postfix.append(String.format("%s ", token));
                continue;
            }
            if (token.equals("√") || token.equals("^")) {
                StringBuilder subEquation = new StringBuilder();
                int count = i + 1;
                int rightPars = 0;
                int leftPars = 0;
                while (true) {
                    subEquation.append(tokens[count]);
                    leftPars = tokens[count].equals("(") ? leftPars + 1 : leftPars;
                    rightPars = tokens[count].equals(")") ? rightPars + 1 : rightPars;
                    if (tokens[count].equals(")") && rightPars == leftPars) {
                        break;
                    }
                    count++;
                }
                if (token.equals("√")) {
                    postfix.append(getResult(subEquation.toString()).sqrt(new MathContext(10))).append(" ");
                }
                else {
                    postfix.append(getResult(subEquation.toString())).append(" ");
                    stack.push(token);
                }
                i = count;
                continue;
            }
            if (stack.isEmpty() || stack.peek().equals("(") && !token.equals(")")) {
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

    private static String getValidCommand(String command) {
        // returns a command in correct notation (e.g. ( 5 + 7 ) * 2 - 9)
        command = command.replaceAll("×", "*");
        command = command.replaceAll("÷", "/");
        command = command.replaceAll("√", " √ ");
        command = command.replaceAll("\\^", " ^ ");
        command = command.replaceAll("\\(", " ( ");
        command = command.replaceAll("\\)", " ) ");
        command = command.replaceAll("\\*", " * ");
        command = command.replaceAll("/", " / ");
        command = command.replaceAll("\\+", " + ");
        command = command.replaceAll("-", " - ");
        command = command.replaceAll("\\s\\s+", " ");

        return command.trim();
    }

    private static boolean isOfHigherPrecedence(String current, String previous) {
        Map<String, Integer> rank = new HashMap<>();
        rank.put("+", 1);
        rank.put("-", 1);
        rank.put("*", 2);
        rank.put("/", 2);
        rank.put("√", 3);
        rank.put("^", 3);
        return rank.get(current) <= rank.get(previous);
    }
}
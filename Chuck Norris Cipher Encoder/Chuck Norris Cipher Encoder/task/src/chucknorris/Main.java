package chucknorris;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        start(scanner);
    }

    public static void start(Scanner scanner) {
        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            String command = scanner.nextLine();
            switch (command) {
                case "exit":
                    exitCommand();
                    break;
                case "encode":
                    encodeCommand(scanner);
                    continue;
                case "decode":
                    decodeCommand(scanner);
                    continue;
                default:
                    System.out.println("There is no '" + command + "' operation.\n");
                    continue;
            }
            break;
        }
    }
    public static void exitCommand() {
        System.out.println("Bye!");
    }
    public static void encodeCommand(Scanner scanner) {
        System.out.println("Input string:");
        String toEncode = scanner.nextLine();
        String encodedMessage = encode(toEncode);
        System.out.println("Encoded string:\n" + encodedMessage + "\n");
    }
    public static void decodeCommand(Scanner scanner) {
        System.out.println("Encoded string:");
        String toDecode = scanner.nextLine();
        if (testEncodedString(toDecode)) {
            String decodedMessage = decode(toDecode);
            System.out.println("Decoded string:\n" + decodedMessage + "\n");
        }
        else {
            System.out.println("Encoded String is not valid.\n");
        }
    }
    public static boolean testEncodedString(String input) {
        String[] chars = input.split("");
        try {
            String decodedMessage = decode(input);
            if (decodedMessage.equals(null)) {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
        for (int i = 0; i < chars.length; i++) {
            if (!(chars[i].equals("0")) && !(chars[i].equals(" "))) {
                return false;
            }
        }
        return true;
    }

    public static String decode(String input) {
        String binaryMessage = convertChuckNorrisUnaryToBinary(input);
        if(binaryMessage.length() % 7 != 0) {
            return null;
        }
        return convertBinaryToString(binaryMessage);
    }

    public static String encode(String input) {
        String binaryMessage = convertStringToBinary(input);
        return convertBinaryToChuckNorrisUnary(binaryMessage);
    }

    public static String convertStringToBinary(String input) {

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(String.format("%7s", Integer.toBinaryString(aChar))
                    .replaceAll(" ", "0")
            );
        }
        return result.toString();
    }

    public static String convertBinaryToChuckNorrisUnary(String input) {
        StringBuilder result = new StringBuilder();
        String[] chars = input.split("");
        String previous = chars[0];
        switch (chars[0]) {
            case "0":
                result.append("00 0");
                break;
            case "1":
                result.append("0 0");
                break;
        }
        for (int i = 1; i < chars.length; i++) {
            String current = chars[i];
            if (current.equals("0") && previous.equals("1")) {
                result.append(" 00 0");
            } else if (current.equals("0") && previous.equals("0")) {
                result.append("0");
            } else if (current.equals("1") && previous.equals("0")) {
                result.append(" 0 0");
            } else if (current.equals("1") && previous.equals("1")) {
                result.append("0");
            }
            previous = current;
        }
        return result.toString();
    }

    public static String convertBinaryToString(String input) {
        StringBuilder toReturn = new StringBuilder();
        String[] results = input.split("(?<=\\G.{" + 7 + "})");
        for (int i = 0; i < results.length; i++) {
            String result = Arrays.stream(results[i].split(" "))
                    .map(binary -> Integer.parseInt(binary, 2))
                    .map(Character::toString)
                    .collect(Collectors.joining());
            toReturn.append(result);
        }

        return toReturn.toString();
    }

    public static String convertChuckNorrisUnaryToBinary(String input) {
        StringBuilder result = new StringBuilder();
        String[] chars = input.split(" ");
        String test = chars[0];
        for (int i = 1; i < chars.length; i++) {
            if (i % 2 == 0) {
                test = chars[i];
                continue;
            }
            if (test.equals("0")) {
                for (int j = 0; j < chars[i].length(); j++) {
                    result.append("1");
                }
            }
            if (test.equals("00")) {
                for (int j = 0; j < chars[i].length(); j++) {
                    result.append("0");
                }
            }
        }
        return result.toString();
    }
}
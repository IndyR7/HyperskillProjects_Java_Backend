package converter;

import java.math.BigInteger;

public class Converter {

    public static String getNumber(String number, int sourceBase, int targetBase) {
        if (!number.contains(".")) {
            BigInteger bigInt = new BigInteger(number, sourceBase);
            return bigInt.toString(targetBase);
        }
        String[] numbers = number.split("\\.");
        String firstPart = getNumber(numbers[0], sourceBase, targetBase);
        String secondPart = getFractional(numbers[1], sourceBase, targetBase);
        StringBuilder result = new StringBuilder()
                .append(firstPart)
                .append(".")
                .append(secondPart);
        return result.toString();
    }

    public static String getFractional(String number, int sourceBase, int targetBase) {
        String[] digits = number.split("");
        double sum = 0;
        for (int i = 0; i < number.length(); i++) {
            int digit = Integer.parseInt(getNumber(digits[i], sourceBase, 10));
            sum += digit * Math.pow(sourceBase, -(i + 1));
        }
        int quotient = (int) (Math.pow(targetBase, 5) * sum);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int rem = quotient % targetBase;
            sb.append(rem).append(" ");
            quotient /= targetBase;
        }
        String[] numbers = sb.toString().split(" ");
        StringBuilder sbToReturn = new StringBuilder();
        for (int i = numbers.length - 1; i >= 0; i--) {
            sbToReturn.append(getNumber(numbers[i], 10, targetBase));
        }
        return sbToReturn.toString();
    }
}
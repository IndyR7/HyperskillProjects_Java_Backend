package numbers;


public class Numbers {
    private final long number;

    public Numbers(long number) {
        this.number = number;
    }

    private boolean isEven() {
        return this.number % 2 == 0;
    }

    private boolean isBuzzNumber() {
        return this.number % 7 == 0 || this.number % 10 == 7;
    }

    private boolean isDuckNumber() {
        return !String.valueOf(this.number).startsWith("0") && String.valueOf(this.number).contains("0");
    }

    private boolean isPalindromicNumber() {
        String numberConverted = String.valueOf(this.number);
        StringBuilder value = new StringBuilder(numberConverted).reverse();
        return numberConverted.equals(value.toString());
    }

    private boolean isGapfulNumber() {
        return this.number % getCombinedNumber() == 0 && this.number > 99;
    }

    private boolean isSpyNumber() {
        String[] numbers = String.valueOf(this.number).split("");
        int sum = 0;
        int product = 1;
        for (String s : numbers) {
            sum += Long.parseLong(s);
            product *= Long.parseLong(s);
        }
        return sum == product;
    }

    private boolean isSquareNumber() {
        return Math.sqrt((double) this.number) - Math.floor(Math.sqrt((double) this.number)) == 0;
    }

    private boolean isSunnyNumber() {
        return Math.sqrt((double) this.number + 1) - Math.floor(Math.sqrt((double) this.number + 1)) == 0;
    }

    private boolean isJumpingNumber() {
        String[] chars = String.valueOf(this.number).split("");
        long[] numbers = new long[chars.length];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Long.parseLong(chars[i]);
        }
        for (int i = numbers.length - 1; i > 0; i--) {
            if (numbers[i - 1] != numbers[i] + 1 && numbers[i - 1] != numbers[i] - 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isHappyNumber() {
        long sum = 0;
        long remainder;
        long number = this.number;
        while (true) {
            if (number == 0 && (sum == 1 || sum == 4)) {
                break;
            }
            if (number <= 0) {
                number = sum;
                sum = 0;
            }
            remainder = number % 10;
            sum += remainder * remainder;
            number /= 10;
        }
        return sum == 1;
    }

    private String getProperties() {
        StringBuilder properties = new StringBuilder();
        if (isEven()) {
            properties.append("even, ");
        } else {
            properties.append("odd, ");
        }
        if (isBuzzNumber()) {
            properties.append("buzz, ");
        }
        if (isDuckNumber()) {
            properties.append("duck, ");
        }
        if (isPalindromicNumber()) {
            properties.append("palindromic, ");
        }
        if (isGapfulNumber()) {
            properties.append("gapful, ");
        }
        if (isSpyNumber()) {
            properties.append("spy, ");
        }
        if (isSquareNumber()) {
            properties.append("square, ");
        }
        if (isSunnyNumber()) {
            properties.append("sunny, ");
        }
        if (isJumpingNumber()) {
            properties.append("jumping, ");
        }
        if (isHappyNumber()) {
            properties.append("happy, ");
        } else {
            properties.append("sad, ");
        }
        properties.delete(properties.length() - 2, properties.length() - 1);
        return properties.toString();
    }

    private long getCombinedNumber() {
        String[] numbers = String.valueOf(this.number).split("");
        String numberConverted = numbers[0] + numbers[numbers.length - 1];
        return Long.parseLong(numberConverted);
    }

    public String firstResult() {
        return String.format("Properties of %,d\nbuzz: %b\nduck: %b\npalindromic: %b\ngapful: %b" +
                        "\nspy: %b\nsquare: %b\nsunny: %b\njumping: %b\nhappy: %b\nsad: %b\n" +
                        "even: %b\nodd: %b\n",
                this.number, isBuzzNumber(), isDuckNumber(), isPalindromicNumber(), isGapfulNumber(), isSpyNumber(),
                isSquareNumber(), isSunnyNumber(), isJumpingNumber(), isHappyNumber(), !isHappyNumber(),
                isEven(), !isEven());
    }

    public String secondResult() {
        return String.format("%,d is %s\n", this.number, getProperties());
    }
}
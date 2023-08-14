package traffic.Validators;

public class InputValidator {
    public static boolean isValidInputInWelcomeStage(String input) {
        return isValidNumberFormat(input) && Integer.parseInt(input) > 0;
    }

    public static boolean isValidNumberFormat(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}

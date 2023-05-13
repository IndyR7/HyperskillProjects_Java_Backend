package tracker.Execution;

import tracker.Constants.Command;
import tracker.Constants.Pattern;
import tracker.Constants.Prompt;
import tracker.Persons.Students;

public class InputHandler {
    private static String[] credentialsInput;
    private static String[] pointsInput;

    public static boolean isValidCredentialsInput(String[] input) {
        credentialsInput = input;

        return isValidLength() && isValidFirstName()
                && isValidLastName() && isValidEmail();
    }

    public static boolean isValidPointsInput(String[] input) {
        pointsInput = input;

        return isValidId() && isValidPointsFormat();
    }

    public static String getErrorMessage(String type) {
        return type.matches(Command.ADD_STUDENTS) ? getIncorrectCredentialsError()
                : type.matches(Command.ADD_POINTS) ? getIncorrectPointsError()
                : null;
    }

    private static String getIncorrectCredentialsError() {
        return !isValidLength() ? Prompt.INCORRECT_CREDENTIALS
                : !isValidFirstName() ? Prompt.INCORRECT_FIRST_NAME
                : !isValidLastName() ? Prompt.INCORRECT_LAST_NAME
                : !isValidEmail() ? Prompt.INCORRECT_MAIL_ADDRESS
                : null;
    }

    private static String getIncorrectPointsError() {
        String id = pointsInput[0];

        return !isValidId() ? String.format(Prompt.NO_STUDENT_FOUND, id)
                : !isValidPointsFormat() ? Prompt.INCORRECT_POINTS_FORMAT
                : null;
    }

    private static boolean isValidLength() {
        return credentialsInput.length >= 3;
    }

    private static boolean isValidFirstName() {
        String firstName = credentialsInput[0];

        return firstName.matches(Pattern.FIRST_NAME);
    }

    private static boolean isValidLastName() {
        StringBuilder sbLastName = new StringBuilder();

        for (int i = 1; i < credentialsInput.length - 1; i++) {
            sbLastName.append(credentialsInput[i]).append(" ");
        }

        return sbLastName.toString().trim().matches(Pattern.LAST_NAME);
    }

    private static boolean isValidEmail() {
        String email = credentialsInput[credentialsInput.length - 1];

        return email.matches(Pattern.MAIL);
    }

    private static boolean isValidId() {
        String id = pointsInput[0];

        return Students.getStudent(id) != null;
    }

    private static boolean isValidPointsFormat() {
        StringBuilder sbPoints = new StringBuilder();

        for (int i = 1; i < pointsInput.length; i++) {
            sbPoints.append(pointsInput[i]).append(" ");
        }

        return sbPoints.toString().trim().matches(Pattern.POINTS);
    }
}
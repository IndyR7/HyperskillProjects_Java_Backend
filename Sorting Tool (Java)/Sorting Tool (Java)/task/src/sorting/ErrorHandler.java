package sorting;

public class ErrorHandler {
    private static StringBuilder errorMessage = new StringBuilder();

    public static String getErrorMessage() {
        return errorMessage.toString();
    }

    public static void setErrorMessage(String errorType, String error) {
        errorMessage.append("\"").append(error)
                .append("\" is not a ")
                .append(errorType)
                .append(". It will be skipped.\n");
    }

    public static void setErrorMessage(String errorType) {
        errorMessage.append("No ").append(errorType).append(" defined!\n");
    }

    public static void clearErrorMessage() {
        errorMessage = new StringBuilder();
    }
}


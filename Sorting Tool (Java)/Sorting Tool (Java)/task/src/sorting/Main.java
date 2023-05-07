package sorting;

public class Main {
    public static void main(final String[] args) {
        CmdHandler.checkCmd(args);

        if (CmdHandler.getStatusCode() < 1) {
            System.out.println(ErrorHandler.getErrorMessage());
        }

        if (CmdHandler.getStatusCode() > -1) {
            UI userInterface = new UI();

            ErrorHandler.clearErrorMessage();
            userInterface.start();
        }
    }
}

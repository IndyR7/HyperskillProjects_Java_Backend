package sorting;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class CmdHandler {
    private static String dataType = null;
    private static String sortingType = null;
    private static Scanner scanner = null;

    public static int getStatusCode() {
        if (sortingType == null) {
            ErrorHandler.setErrorMessage("sorting type");
            return -1;
        }

        if (dataType == null) {
            ErrorHandler.setErrorMessage("data type");
            return -1;
        }

        return ErrorHandler.getErrorMessage().isEmpty() ? 1 : 0;

    }

    public static void checkCmd(String[] args) {
        sortingType = !Arrays.asList(args).contains(Pattern.SORTING_QUALIFIER) ? "natural" : sortingType;
        dataType = !Arrays.asList(args).contains(Pattern.DATA_QUALIFIER) ? "long" : dataType;

        for (int i = 0; i < args.length - 1; i++) {
            String currentArg = args[i];
            String nextArg = args[i + 1];

            if (!currentArg.matches(Pattern.QUALIFIERS)) {
                ErrorHandler.setErrorMessage("valid parameter", currentArg);
                continue;
            }

            if (currentArg.matches(Pattern.SORTING_QUALIFIER)) {
                sortingType = nextArg.matches(Pattern.SORTING_TYPES) ? nextArg : sortingType;
            }

            if (currentArg.matches(Pattern.DATA_QUALIFIER)) {
                dataType = nextArg.matches(Pattern.DATA_TYPES) ? nextArg : dataType;
            }

            if (currentArg.matches(Pattern.INPUT_FILE_QUALIFIER)) {
                FileHandler.setInputFile(nextArg);
                try {
                    scanner = new Scanner(FileHandler.getInputFile());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            if (currentArg.matches(Pattern.OUTPUT_FILE_QUALIFIER)) {
                FileHandler.setOutputFile(nextArg);
                FileHandler.setFileWriter();
            }

            i++;
        }
    }

    public static Scanner getScanner() {
        return scanner == null ? new Scanner(System.in) : scanner;
    }
    public static String getDataType() {
        return dataType;
    }

    public static String getSortingType() {
        return sortingType;
    }
}

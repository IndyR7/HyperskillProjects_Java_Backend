package sorting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    private static File outputFile = null;
    private static File inputFile = null;
    private static FileWriter fileWriter;

    public static File getOutputFile() {
        return outputFile;
    }

    public static void setOutputFile(String file) {
        outputFile = new File(file);
    }

    public static File getInputFile() {
        return inputFile;
    }

    public static void setInputFile(String file) {
        inputFile = new File(file);
    }

    public static void setFileWriter() {
        try {
            fileWriter = new FileWriter(outputFile, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void writeToFile(T input) {
        try {
            fileWriter.append(String.valueOf(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeFileWriter() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

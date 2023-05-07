package sorting;

import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class UI {
    private final Scanner scanner;
    private final String dataType;
    private final String sortingType;
    private final StringBuilder sbResult;

    public UI() {
        this.scanner = CmdHandler.getScanner();
        this.dataType = CmdHandler.getDataType();
        this.sortingType = CmdHandler.getSortingType();
        this.sbResult = new StringBuilder();
    }

    public void start() {
        switch (dataType) {
            case "line" -> System.out.println(getStats(scanner::nextLine));
            case "word" -> System.out.println(getStats(scanner::next));
            default -> System.out.println(getStats(scanner::nextLong));
        }

        try {
            FileHandler.writeToFile(sbResult.toString());
            FileHandler.closeFileWriter();
        } catch (Exception ignored) {

        }

        scanner.close();
    }

    private <T extends Comparable<T>> String getStats(Supplier<T> supplier) {
        List<T> items = InputHandler.getInput(scanner, supplier);

        SortingTool.sortList(items);
        setResult(items);

        return FileHandler.getOutputFile() == null ? ErrorHandler.getErrorMessage() + sbResult
                : ErrorHandler.getErrorMessage();
    }

    private <T> void setResult(List<T> list) {
        sbResult.append("Total ")
                .append(dataType)
                .append("s: ")
                .append(list.size())
                .append(".\n");

        String form = sortingType.equals("byCount") ? SortingTool.getSortedStringByCount(list)
                : SortingTool.getNaturalSortedString(list, dataType);

        sbResult.append(form);
    }
}
package sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class InputHandler {

    public static <T> List<T> getInput(Scanner scanner, Supplier<T> supplier) {
        List<T> items = new ArrayList<>();

        while (scanner.hasNext()) {
            T input;

            try {
                input = supplier.get();
                items.add(input);
            } catch (Exception e) {
                input = (T) scanner.next();
                ErrorHandler.setErrorMessage("long", (String) input);
            }
        }

        return items;
    }
}
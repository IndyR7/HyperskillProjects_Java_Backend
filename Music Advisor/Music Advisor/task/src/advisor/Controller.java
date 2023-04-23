package advisor;

public class Controller {
    public static int numberOfItems = 5;
    private static int currentPage = 1;
    private static int numberOfPages = 1;
    private static String[] items;

    public static void setItems(String itemType) {
        items = itemType.split("&&");
        currentPage = 1;
        numberOfPages = items.length / numberOfItems;
    }

    public static String getItems(String cmd) {
        if (cmd.equals("prev") && currentPage == 1 || cmd.equals("next") && currentPage == numberOfPages) {
            return Prompt.NO_MORE_PAGES;
        }

        currentPage = cmd.equals("prev") ? currentPage - 1 : cmd.equals("next") ? currentPage + 1 : currentPage;

        StringBuilder sbItems = new StringBuilder();
        int startingIndex = numberOfItems * (currentPage - 1);
        int endingIndex = startingIndex + numberOfItems;

        while (startingIndex < endingIndex) {
            sbItems.append(items[startingIndex]).append("\n");
            startingIndex++;
        }

        return sbItems.append(String.format("---PAGE %d OF %d---", currentPage, numberOfPages)).toString();
    }
}

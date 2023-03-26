package budget;

import java.util.*;

public class UI {
    private final Scanner scanner;
    private final PurchaseList purchases;


    public UI(Scanner scanner) {
        this.scanner = scanner;
        this.purchases = new PurchaseList();
    }

    public void start() {
        do {
            System.out.println(getMainMenu());
        }
        while (isRunning(Integer.parseInt(scanner.nextLine())));
        System.out.println("\nBye!");
    }

    private boolean isRunning(int operation) {
        switch (operation) {
            case 0 -> {
                return false;
            }
            case 1 -> {
                addIncome();
                return true;
            }
            case 2 -> {
                addPurchases();
                return true;
            }
            case 3 -> {
                getPurchases();
                return true;
            }
            case 4 -> {
                System.out.printf("\nBalance: $%,.2f\n\n", BankAccount.getBalance());
                return true;
            }
            case 5 -> {
                if (DataHandler.saveData(purchases)) {
                    System.out.println("\nPurchases were saved!\n");
                }
                return true;
            }
            case 6 -> {
                if (DataHandler.loadData(purchases)) {
                    System.out.println("\nPurchases were loaded!\n");
                }
                return true;
            }
            case 7 -> {
                System.out.println();
                sortPurchases();
                return true;
            }
            default -> {
                System.out.println("Invalid input! Please try again\n");
                return true;
            }
        }
    }

    private String getMainMenu() {
        return """
                Choose your action
                1) Add income
                2) Add purchase
                3) Show list of purchases
                4) Balance
                5) Save
                6) Load
                7) Analyze (sort)
                0) Exit""";
    }

    private String getPurchaseMenu() {
        return """
                                
                Choose your action
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                5) All
                6) Back""";
    }

    private String getSortingMenu() {
        return """
                1) Sort all purchases
                2) Sort by type
                3) Sort certain type
                4) Back""";
    }

    private void sortPurchases() {
        while (true) {
            System.out.println(getSortingMenu());

            int sortingID = Integer.parseInt(scanner.nextLine());

            if (sortingID == 4) {
                System.out.println();
                break;
            }
            if (sortingID == 3) {
                System.out.println(getPurchaseMenu().replaceAll("5\\) All\n6\\) Back", ""));
            }
            PurchaseCategory category = sortingID == 3
                    ? PurchaseCategory.getCategoryById(Integer.parseInt(scanner.nextLine()))
                    : PurchaseCategory.ALL;

            System.out.println(purchases.getPurchasesByCategory(category, sortingID));
        }
    }

    private void addIncome() {
        System.out.println("\nEnter income:");

        BankAccount.addToBalance(Double.parseDouble(scanner.nextLine()));

        System.out.println("Income was added!\n");
    }

    private void addPurchases() {
        while (true) {
            System.out.println(getPurchaseMenu().replaceAll("5\\) All\n6\\) Back", "5) Back"));

            int categoryID = Integer.parseInt(scanner.nextLine());

            if (categoryID == 5) {
                System.out.println();
                break;
            }

            System.out.println("\nEnter purchase name:");
            String name = scanner.nextLine();

            System.out.println("Enter its price");
            double price = Double.parseDouble(scanner.nextLine());

            purchases.addPurchase(name, price, PurchaseCategory.getCategoryById(categoryID));

            BankAccount.removeFromBalance(price);

            System.out.println("Purchase was added!");
        }
    }

    private void getPurchases() {
        while (true) {
            System.out.println(getPurchaseMenu());

            int categoryID = Integer.parseInt(scanner.nextLine());

            if (categoryID >= 6) {
                System.out.println();
                break;
            }

            PurchaseCategory category = PurchaseCategory.getCategoryById(categoryID);

            System.out.printf("%s:\n", category.getCategoryName());
            System.out.println(purchases.getPurchasesByCategory(category, 0));
        }
    }
}
package budget;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class DataHandler {

    public static boolean saveData(PurchaseList purchaseList) {
        try {
            FileWriter fileWriter = new FileWriter("purchases.txt");
            fileWriter.write(String.format("%f\n", BankAccount.getBalance()));

            for (Map.Entry<PurchaseCategory, List<Purchase>> entry : purchaseList.getPurchases().entrySet()) {
                PurchaseCategory category = entry.getKey();
                List<Purchase> purchases = entry.getValue();

                for (Purchase purchase : purchases) {
                    fileWriter.write(String.format("%s,%f,%d\n",
                            purchase.name(), purchase.price(), category.getCategoryId()));
                }
            }
            fileWriter.close();
            return true;
        } catch (IOException fileNotFound) {
            System.out.printf("\n%s\n", fileNotFound.getMessage());
            return false;
        }
    }

    public static boolean loadData(PurchaseList purchaseList) {
        try {
            String[] data = Files.readString(Path.of("purchases.txt")).split("\n");
            double balance = Double.parseDouble(data[0]);

            BankAccount.setBalance(balance);

            for (int i = 1; i < data.length; i++) {
                String[] purchase = data[i].split(",");
                String name = purchase[0];
                double price = Double.parseDouble(purchase[1]);
                int categoryID = Integer.parseInt(purchase[2]);

                purchaseList.addPurchase(name, price, PurchaseCategory.getCategoryById(categoryID));
            }
            return true;

        } catch (IOException e) {
            System.out.printf("\n%s\n", e.getMessage());
            return false;
        }

    }
}
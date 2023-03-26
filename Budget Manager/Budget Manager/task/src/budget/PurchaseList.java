package budget;

import java.util.*;

public class PurchaseList {
    private final Map<PurchaseCategory, List<Purchase>> purchases;

    public PurchaseList() {
        this.purchases = new HashMap<>();
    }

    public void addPurchase(String name, double price, PurchaseCategory category) {
        List<Purchase> purchaseList = purchases.getOrDefault(category, new ArrayList<>());
        purchaseList.add(new Purchase(name, price, category));
        purchases.put(category, purchaseList);
    }

    public Map<PurchaseCategory, List<Purchase>> getPurchases() {
        return this.purchases;
    }

    public String getPurchasesByCategory(PurchaseCategory category, int sortingID) {
        if (sortingID == 2) {
            return getPurchasesSortedByType();
        }

        final double[] totalSum = {0.0};
        List<Purchase> purchaseList;

        StringBuilder sbPurchases = new StringBuilder()
                .append(String.format("\n%s:\n", category.getCategoryName()));

        if (category.getCategoryId() == 5 || sortingID == 1) {
            purchaseList = new ArrayList<>();
            purchases.forEach((k, v) -> purchaseList.addAll(v));
        } else {
            purchaseList = purchases.getOrDefault(category, new ArrayList<>());
        }

        if (sortingID == 1 || sortingID == 3) {
            purchaseList.sort(Comparator.comparing(Purchase::name).reversed());
            purchaseList.sort(Comparator.comparing(Purchase::price).reversed());
        }
        for (Purchase purchase : purchaseList) {
            String formatted = String.format("%s $%,.2f\n", purchase.name(), purchase.price());
            totalSum[0] += purchase.price();
            sbPurchases.append(formatted);
        }
        return purchases.isEmpty() ? "\nThe purchase list is empty\n"
                : sbPurchases.append(String.format("Total sum: $%,.2f\n", totalSum[0])).toString();
    }

    public String getPurchasesSortedByType() {
        final double[] totalSum = {0.0};
        Map<String, Double> categories = new HashMap<>();

        StringBuilder sbCategories = new StringBuilder().append("\nTypes:\n");

        categories.put(PurchaseCategory.FOOD.getCategoryName(), 0.0);
        categories.put(PurchaseCategory.CLOTHES.getCategoryName(), 0.0);
        categories.put(PurchaseCategory.ENTERTAINMENT.getCategoryName(), 0.0);
        categories.put(PurchaseCategory.OTHER.getCategoryName(), 0.0);

        purchases.forEach((purchaseCategory, purchases) -> {
            for (Purchase purchase : purchases) {
                categories.put(purchaseCategory.getCategoryName(), categories.get(purchaseCategory.getCategoryName()) + purchase.price());
                totalSum[0] += purchase.price();
            }
        });

        categories.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(entry -> {
                    String formatted = String.format("%s - $%,.2f\n", entry.getKey(), entry.getValue());
                    sbCategories.append(formatted);
                });

        return sbCategories.append(String.format("Total sum: $%,.2f\n", totalSum[0])).toString();
    }
}
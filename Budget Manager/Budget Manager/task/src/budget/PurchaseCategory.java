package budget;

public enum PurchaseCategory {
    FOOD(1, "Food"),
    CLOTHES(2, "Clothes"),
    ENTERTAINMENT(3, "Entertainment"),
    OTHER(4, "Other"),
    ALL(5, "All");

    private final int categoryId;
    private final String categoryName;

    PurchaseCategory(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public static PurchaseCategory getCategoryById(int categoryId) {
        for (PurchaseCategory category : PurchaseCategory.values()) {
            if (category.categoryId == categoryId) {
                return category;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + categoryId + "]");
    }
}
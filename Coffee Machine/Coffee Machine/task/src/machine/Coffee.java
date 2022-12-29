package machine;

public enum Coffee {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6);
    private int water;
    private int milk;
    private int beans;
    private int price;

    Coffee(int water, int milk, int beans, int price) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.price = price;
    }

    int getWaterNeeded() {
        return this.water;
    }

    int getMilkNeeded() {
        return this.milk;
    }

    int getBeansNeeded() {
        return this.beans;
    }

    int getPrice() {
        return this.price;
    }
}

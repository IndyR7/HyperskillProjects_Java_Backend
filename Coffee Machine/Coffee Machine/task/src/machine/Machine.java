package machine;

public class Machine {
    private int waterAvailable;
    private int milkAvailable;
    private int beansAvailable;
    private int cupsAvailable;
    private int moneyAvailable;

    public Machine(int water, int milk, int beans, int cups, int money) {
        this.waterAvailable = water;
        this.milkAvailable = milk;
        this.beansAvailable = beans;
        this.cupsAvailable = cups;
        this.moneyAvailable = money;
    }

    public boolean hasEnoughResources(Coffee coffee) {
        return hasEnoughWater(coffee) && hasEnoughMilk(coffee) && hasEnoughBeans(coffee) && hasEnoughCups();
    }

    private boolean hasEnoughWater(Coffee coffee) {
        return this.waterAvailable >= coffee.getWaterNeeded();
    }

    private boolean hasEnoughMilk(Coffee coffee) {
        return this.milkAvailable >= coffee.getMilkNeeded();
    }

    private boolean hasEnoughBeans(Coffee coffee) {
        return this.beansAvailable >= coffee.getBeansNeeded();
    }

    private boolean hasEnoughCups() {
        return cupsAvailable >= 1;
    }

    private int getWaterAvailable() {
        return this.waterAvailable;
    }

    private int getMilkAvailable() {
        return this.milkAvailable;
    }

    private int getBeansAvailable() {
        return this.beansAvailable;
    }

    private int getCupsAvailable() {
        return this.cupsAvailable;
    }

    public int getMoneyAvailable() {
        return this.moneyAvailable;
    }

    public void takeMoney() {
        this.moneyAvailable = 0;
    }

    public void addMoney(int moneyToAdd) {
        this.moneyAvailable += moneyToAdd;
    }

    public void addContents(int water, int milk, int beans, int cups) {
        this.waterAvailable += water;
        this.milkAvailable += milk;
        this.beansAvailable += beans;
        this.cupsAvailable += cups;
    }

    public void takeContents(int water, int milk, int beans, int cups) {
        this.waterAvailable -= water;
        this.milkAvailable -= milk;
        this.beansAvailable -= beans;
        this.cupsAvailable -= cups;
    }

    public void printContents() {
        System.out.printf("The coffee machine has:\n%d ml of water\n%d ml of milk\n%d g of coffee beans\n" +
                        "%d disposable cups\n$%d of money\n\n", getWaterAvailable(), getMilkAvailable(),
                getBeansAvailable(), getCupsAvailable(), getMoneyAvailable());
    }

    public void printError(Coffee coffee) {
        StringBuilder errorMessage = new StringBuilder().append("Sorry, not enough ");
        if (!hasEnoughWater(coffee)) {
            errorMessage.append("water, ");
        }
        if (!hasEnoughMilk(coffee)) {
            errorMessage.append("milk, ");
        }
        if (!hasEnoughBeans(coffee)) {
            errorMessage.append("beans, ");
        }
        if (!hasEnoughCups()) {
            errorMessage.append("cups, ");
        }
        errorMessage.replace(errorMessage.length() - 2, errorMessage.length() - 1, "!\n");
        System.out.println(errorMessage);
    }
}

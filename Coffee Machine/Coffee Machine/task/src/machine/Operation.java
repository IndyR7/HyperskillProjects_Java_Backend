package machine;

import java.util.Scanner;

public class Operation {
    private Scanner scanner;
    private Machine machine;
    private boolean running;

    public Operation(Scanner scanner, Machine machine) {
        this.scanner = scanner;
        this.machine = machine;
        this.running = true;
    }

    public void performOperation(String operation) {
        switch (operation) {
            case "buy":
                buy();
                break;
            case "fill":
                fill();
                break;
            case "take":
                take();
                break;
            case "remaining":
                machine.printContents();
                break;
            case "exit":
                this.running = false;
                break;
            default:
                System.out.println("There is no such operation!");
        }
    }

    public boolean isRunning() {
        return this.running;
    }

    private void buy() {
        Coffee coffee;
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        switch (scanner.nextLine()) {
            case "1":
                coffee = Coffee.ESPRESSO;
                if (machine.hasEnoughResources(coffee)) {
                    machine.takeContents(coffee.getWaterNeeded(), coffee.getMilkNeeded(),
                            coffee.getBeansNeeded(), 1);
                    machine.addMoney(coffee.getPrice());
                } else {
                    machine.printError(coffee);
                }
                break;
            case "2":
                coffee = Coffee.LATTE;
                if (machine.hasEnoughResources(coffee)) {
                    machine.takeContents(coffee.getWaterNeeded(), coffee.getMilkNeeded(),
                            coffee.getBeansNeeded(), 1);
                    machine.addMoney(coffee.getPrice());
                } else {
                    machine.printError(coffee);
                }
                break;
            case "3":
                coffee = Coffee.CAPPUCCINO;
                if (machine.hasEnoughResources(coffee)) {
                    machine.takeContents(coffee.getWaterNeeded(), coffee.getMilkNeeded(),
                            coffee.getBeansNeeded(), 1);
                    machine.addMoney(coffee.getPrice());
                } else {
                    machine.printError(coffee);
                }
                break;
            case "back":
                break;
            default:
                System.out.println("There is no such operation!\n");
        }
    }

    private void fill() {
        System.out.println("Write how many ml of water you want to add:");
        int water = scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        int milk = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        int beans = scanner.nextInt();
        System.out.println("Write how many disposable cups you want to add:");
        int cups = scanner.nextInt();
        machine.addContents(water, milk, beans, cups);
    }

    private void take() {
        System.out.printf("I gave you $%d\n", machine.getMoneyAvailable());
        machine.takeMoney();
    }
}

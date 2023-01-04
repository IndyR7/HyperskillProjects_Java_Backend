class TestDrive {
    public static void main(String[] args) throws InterruptedException {
        BurgerStore burgerStore = new BurgerStore();
        burgerStore.orderBurger("Chinese");
        burgerStore.orderBurger("American");
        burgerStore.orderBurger("Russian");
    }
}

abstract class BurgerFactory {

    abstract Burger createBurger(String type);

    Burger orderBurger(String type) throws InterruptedException {
        Burger burger = createBurger(type);
        if (burger == null) {
            System.out.println("Sorry, we are unable to create this kind of burger\n");
            return null;
        }
        System.out.println("Making a " + burger.getName());
        burger.putBun();
        burger.putCutlet();
        burger.putSauce();
        Thread.sleep(1500L);
        System.out.println(burger.getName() + " ready" + "\n");
        return burger;
    }
}

class BurgerStore extends BurgerFactory {
    @Override
    Burger createBurger(String type) {
        return switch (type) {
            case "Chinese" -> new ChineseBurger();
            case "American" -> new AmericanBurger();
            case "Russian" -> new RussianBurger();
            default -> null;
        };
    }
}

abstract class Burger {
    private String name;

    Burger(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    void putBun() {
        System.out.println("Putting bun");
    }

    void putCutlet() {
        System.out.println("Putting patty");
    }

    void putSauce() {
        System.out.println("Putting sauce");
    }

}

class ChineseBurger extends Burger {
    public ChineseBurger() {
        super("Chinese Burger");
    }
}

class AmericanBurger extends Burger {
    public AmericanBurger() {
        super("American Burger");
    }
}

class RussianBurger extends Burger {
    public RussianBurger() {
        super("Russian Burger");
    }
}
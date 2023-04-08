package mealplanner;

import java.sql.SQLException;
import java.util.Scanner;

public class UI {
    private final Scanner scanner;

    public UI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() throws SQLException {
        mainLoop:
        while (true) {
            System.out.println(CommandPrompt.MAIN);

            switch (scanner.nextLine().toLowerCase()) {
                case "add":
                    addMeal();
                    break;
                case "show":
                    showMeals();
                    break;
                case "plan":
                    planMeals();
                    break;
                case "save":
                    saveShoppingList();
                    break;
                case "exit":
                    break mainLoop;
            }
        }
        System.out.println("Bye!");
    }

    private void addMeal() throws SQLException {
        System.out.println(CommandPrompt.ADD);

        MealCategory mealCategory = getValidMealCategory();

        System.out.println(CommandPrompt.INPUT_MEAL_NAME);
        String name = getValidMealName();

        System.out.println(CommandPrompt.INPUT_INGREDIENTS);
        String[] ingredients = getValidIngredients();

        Meals.addMeal(new Meal(name, ingredients, mealCategory), true);

        System.out.println(CommandPrompt.SUCCESSFUL_ADD);
    }

    private void showMeals() throws SQLException {
        System.out.println(CommandPrompt.CATEGORY);

        String category;

        while (true) {
            category = scanner.nextLine();
            if (category.matches(RegexPatterns.ADD_MEAL_CATEGORY_PATTERN)) {
                break;
            }
            System.out.println(ErrorMessage.WRONG_CATEGORY);
        }
        Database.setMeals(category.toUpperCase());

        if (Meals.getMeals().isEmpty()) {
            System.out.println(ErrorMessage.NO_MEALS);
        } else {
            System.out.printf("Category: %s\n", category);
            Meals.getMeals().forEach(System.out::println);
        }
    }

    private void planMeals() throws SQLException {
        for (Day day : Day.values()) {
            String dayString = StringUtils.capitalize(day.toString());
            Meal[] meals = new Meal[3];

            System.out.println(dayString);

            for (int i = 0; i < 3; i++) {
                System.out.println(Meals.getMealsByCategory(i));
                System.out.println(CommandPrompt.getMealSelectionPrompt(i, dayString));
                meals[i] = getValidMealFromList();
            }
            MealPlanner.addMeal(day, meals);

            System.out.printf("Yeah! We planned the meals for %s.\n", dayString);
        }

        System.out.println(MealPlanner.getScheme());

        Database.addPlan();
    }

    private void saveShoppingList() {
        if (MealPlanner.getMealPlanner().isEmpty()) {
            System.out.println(ErrorMessage.FAILED_SAVE);
        } else {
            System.out.println(CommandPrompt.INPUT_FILE_NAME);
            FileHandler.save(scanner.nextLine());
            System.out.println(CommandPrompt.SUCCESSFUL_SAVE);
        }
    }

    private Meal getValidMealFromList() {
        while (true) {
            Meal meal = Meals.getMeal(scanner.nextLine());
            if (meal != null) {
                return meal;
            }
            System.out.println(ErrorMessage.NON_EXISTING_MEAL);
        }
    }

    private MealCategory getValidMealCategory() {
        while (true) {
            String category = scanner.nextLine();
            if (category.matches(RegexPatterns.ADD_MEAL_CATEGORY_PATTERN)) {
                return MealCategory.valueOf(category.toUpperCase());
            }
            System.out.println(ErrorMessage.WRONG_CATEGORY);
        }
    }

    private String getValidMealName() {
        while (true) {
            String name = scanner.nextLine();
            if (name.matches(RegexPatterns.ADD_MEAL_NAME_PATTERN)) {
                return name;
            }
            System.out.println(ErrorMessage.WRONG_FORMAT);
        }
    }

    private String[] getValidIngredients() {
        while (true) {
            String ingredients = scanner.nextLine();
            if (ingredients.matches(RegexPatterns.ADD_INGREDIENTS_PATTERN)) {
                return ingredients.split(",\\s*");
            }
            System.out.println(ErrorMessage.WRONG_FORMAT);
        }
    }
}
package mealplanner;

import java.sql.SQLException;
import java.util.Arrays;

public class Meal {

    private final int mealID;
    private final String name;
    private final String[] ingredients;
    private final MealCategory mealCategory;

    public Meal(String name, String[] ingredients, MealCategory mealCategory) throws SQLException {
        this.mealID = Database.getNextMealID();
        this.name = name;
        this.ingredients = ingredients;
        this.mealCategory = mealCategory;
    }

    public String getName() {
        return this.name;
    }

    public MealCategory getMealCategory() {
        return this.mealCategory;
    }

    public String[] getIngredients() {
        return this.ingredients;
    }

    public int getMealID() {
        return this.mealID;
    }

    @Override
    public String toString() {
        StringBuilder sbMeal = new StringBuilder();
        sbMeal.append("Name: ")
                .append(this.name)
                .append("\nIngredients:\n");
        Arrays.stream(ingredients).forEach(s -> sbMeal.append(s).append("\n"));

        return sbMeal.toString();
    }
}
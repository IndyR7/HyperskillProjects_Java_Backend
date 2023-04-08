package mealplanner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Meals {
    private static final List<Meal> meals = new ArrayList<>();

    public static void addMeal(Meal meal, boolean addToDataBase) throws SQLException {
        meals.add(meal);
        if (addToDataBase) {
            Database.addMeal(meal);
        }
    }

    public static List<Meal> getMeals() {
        return meals;
    }

    public static Meal getMeal(String name) {
        for (Meal meal : meals) {
            if (meal.getName().equals(name)) {
                return meal;
            }
        }
        return null;
    }

    public static void clear() {
        meals.clear();
    }

    public static String getMealsByCategory(int i) {
        return i == 0 ? getBreakfastMeals() : i == 1 ? getLunchMeals() : getDinnerMeals();
    }

    private static String getBreakfastMeals() {
        StringBuilder sbBreakfastMeals = new StringBuilder();

        meals.stream().filter(s -> s.getMealCategory().toString().equals("BREAKFAST"))
                .sorted(Comparator.comparing(Meal::getName))
                .forEach(s -> sbBreakfastMeals.append(s.getName()).append("\n"));

        return sbBreakfastMeals.toString();
    }

    private static String getLunchMeals() {
        StringBuilder sbLunchMeals = new StringBuilder();

        meals.stream().filter(s -> s.getMealCategory().toString().equals("LUNCH"))
                .sorted(Comparator.comparing(Meal::getName))
                .forEach(s -> sbLunchMeals.append(s.getName()).append("\n"));

        return sbLunchMeals.toString();
    }

    private static String getDinnerMeals() {
        StringBuilder sbDinnerMeals = new StringBuilder();

        meals.stream().filter(s -> s.getMealCategory().toString().equals("DINNER"))
                .sorted(Comparator.comparing(Meal::getName))
                .forEach(s -> sbDinnerMeals.append(s.getName()).append("\n"));

        return sbDinnerMeals.toString();
    }
}
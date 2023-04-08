package mealplanner;

import java.util.HashMap;
import java.util.Map;


public class MealPlanner {
    private static final Map<Day, Meal[]> mealPlanner = new HashMap<>();

    public static void addMeal(Day day, Meal[] meals) {
        mealPlanner.put(day, meals);
    }

    public static Map<Day, Meal[]> getMealPlanner() {
        return mealPlanner;
    }

    public static String getScheme() {
        StringBuilder sbScheme = new StringBuilder();

        for (Day day : Day.values()) {
            sbScheme.append(StringUtils.capitalize(day.toString().toLowerCase()))
                    .append("\n");

            Meal[] meals = mealPlanner.get(day);

            for (Meal meal : meals) {
                sbScheme.append(StringUtils.capitalize(meal.getMealCategory().toString().toLowerCase()))
                        .append(": ")
                        .append(meal.getName())
                        .append("\n");
            }
        }
        return sbScheme.toString();
    }

    public static void clear() {
        mealPlanner.clear();
    }
}
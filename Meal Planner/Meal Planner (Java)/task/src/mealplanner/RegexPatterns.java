package mealplanner;

public final class RegexPatterns {
    public static final String ADD_MEAL_CATEGORY_PATTERN = "breakfast|lunch|dinner";
    public static final String ADD_MEAL_NAME_PATTERN = "([A-Za-z]+\\s*)+";
    public static final String ADD_INGREDIENTS_PATTERN = "[A-Za-z]+(,?\\s*[A-Za-z]+)*";
}
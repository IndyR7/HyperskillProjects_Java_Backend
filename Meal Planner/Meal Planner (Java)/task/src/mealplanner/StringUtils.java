package mealplanner;


public class StringUtils {
    public static String capitalize(String toCapitalize) {
        return toCapitalize.substring(0, 1).toUpperCase() + toCapitalize.toLowerCase().substring(1);
    }
}
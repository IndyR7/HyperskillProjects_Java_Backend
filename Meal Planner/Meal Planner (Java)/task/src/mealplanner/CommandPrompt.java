package mealplanner;

public class CommandPrompt {
    public static final String MAIN = "What would you like to do (add, show, plan, save, exit)?";
    public static final String CATEGORY = "Which category do you want to print (breakfast, lunch, dinner)?";
    public static final String ADD = "Which meal do you want to add (breakfast, lunch, dinner)?";
    public static final String INPUT_MEAL_NAME = "Input the meal's name:";
    public static final String INPUT_INGREDIENTS = "Input the ingredients:";
    public static final String INPUT_FILE_NAME = "Input a filename:";
    public static final String SUCCESSFUL_ADD = "The meal has been added!";
    public static final String SUCCESSFUL_SAVE = "Saved!";

    public static String getMealSelectionPrompt(int i, String day) {
        String category = i == 0 ? "Breakfast" : i == 1 ? "Lunch" : "Dinner";
        return String.format("Choose the %s for %s from the list above:", category, day);
    }
}

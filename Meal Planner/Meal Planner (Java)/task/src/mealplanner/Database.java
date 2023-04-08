package mealplanner;

import java.sql.*;

public class Database {

    private static final String DB_URL = "jdbc:postgresql:meals_db";
    private static final String USER = "postgres";
    private static final String PASS = "1111";

    public static void setDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        Statement statement = connection.createStatement();

        statement.executeUpdate("CREATE TABLE if NOT EXISTS meals ( " +
                "meal_id INT," +
                "category varchar(10) NOT NULL," +
                "meal varchar(50) NOT NULL," +
                "id serial" +
                ")");

        statement.executeUpdate("CREATE TABLE if NOT EXISTS ingredients ( " +
                "ingredient_id INT," +
                "meal_id INT NOT NULL," +
                "ingredient varchar(50) NOT NULL," +
                "id serial" +
                ")");

        statement.executeUpdate("CREATE TABLE if NOT EXISTS plan ( " +
                "meal_option varchar(50), " +
                "meal_category varchar(10), " +
                "meal_id INT NOT NULL," +
                "day varchar(9)" +
                ")");

        statement.close();
        connection.close();
    }

    public static void addMeal(Meal meal) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        PreparedStatement addMealStatement = connection
                .prepareStatement("INSERT INTO meals (category, meal) " +
                        "VALUES (?, ?)");

        addMealStatement.setString(1, meal.getMealCategory().toString());
        addMealStatement.setString(2, meal.getName());

        addMealStatement.executeUpdate();

        connection.createStatement().executeUpdate("UPDATE meals SET meal_id = id");

        for (String ingredient : meal.getIngredients()) {
            PreparedStatement addIngredientStatement = connection
                    .prepareStatement("INSERT INTO ingredients (meal_id, ingredient) " +
                            "VALUES (?, ?)");

            addIngredientStatement.setInt(1, meal.getMealID());
            addIngredientStatement.setString(2, ingredient);

            addIngredientStatement.executeUpdate();
        }
        connection.createStatement().executeUpdate("UPDATE ingredients SET ingredient_id = id");

        connection.close();
    }

    public static void addPlan() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        PreparedStatement addMealStatement = connection
                .prepareStatement("INSERT INTO plan (meal_option, meal_category, meal_id, day) " +
                        "VALUES (?, ?, ?, ?)");

        for (Day day : MealPlanner.getMealPlanner().keySet()) {
            for (int i = 0; i < 3; i++) {
                Meal meal = MealPlanner.getMealPlanner().get(day)[i];

                addMealStatement.setString(1, meal.getName());
                addMealStatement.setString(2, meal.getMealCategory().toString());
                addMealStatement.setInt(3, meal.getMealID());
                addMealStatement.setString(4, day.toString());

                addMealStatement.executeUpdate();
            }
        }
    }

    public static void setMeals(String category) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        Statement statement = connection.createStatement();
        ResultSet resultSet = category.equals("All") ? statement.executeQuery("SELECT * FROM meals") :
                statement.executeQuery(String.format("SELECT * FROM meals WHERE category = '%s'", category));

        Meals.clear();

        while (resultSet.next()) {
            int meal_id = resultSet.getInt("meal_id");
            String name = resultSet.getString("meal");
            String mealCategory = resultSet.getString("category");

            Meal meal = new Meal(name, getIngredients(meal_id), MealCategory.valueOf(mealCategory));

            Meals.addMeal(meal, false);
        }
    }

    public static void setPlan() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement
                .executeQuery("SELECT * FROM plan");

        MealPlanner.clear();

        int i = 0;
        Meal[] meals = new Meal[3];

        while (resultSet.next()) {
            String name = resultSet.getString("meal_option");
            MealCategory category = MealCategory.valueOf(resultSet.getString("meal_category".toUpperCase()));
            int meal_id = resultSet.getInt("meal_id");
            Day day = Day.valueOf(resultSet.getString("day".toUpperCase()));

            meals[i] = new Meal(name, getIngredients(meal_id), category);
            i++;

            if (i == 2) {
                MealPlanner.addMeal(day, meals);
                i = 0;
                meals = new Meal[3];
            }
        }
    }

    public static int getNextMealID() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement
                .executeQuery("SELECT MAX(meal_id) FROM meals");

        return resultSet.next() ? resultSet.getInt(1) + 1 : -1;
    }

    public static String getShoppingList() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        StringBuilder sbShoppingList = new StringBuilder();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT ingredient, count(*) AS count " +
                "FROM ingredients, plan " +
                "WHERE ingredients.meal_id = plan.meal_id " +
                "GROUP BY ingredient");

        while (resultSet.next()) {
            String ingredient = resultSet.getString("ingredient");
            int count = resultSet.getInt("count");

            sbShoppingList.append(String.format("%s x%d\n", ingredient, count));
        }
        return sbShoppingList.toString().replaceAll(" x1", "");
    }

    private static String[] getIngredients(int meal_id) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        StringBuilder sbIngredients = new StringBuilder();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement
                .executeQuery(String.format("SELECT * FROM ingredients WHERE meal_id = %d", meal_id));

        while (resultSet.next()) {
            String ingredient = resultSet.getString("ingredient");
            sbIngredients.append(ingredient).append("\n");
        }
        return sbIngredients.toString().split("\n");
    }

    private static void resetPlan() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);

        Statement statement = connection.createStatement();

        statement.executeQuery("DELETE FROM plan");
    }
}
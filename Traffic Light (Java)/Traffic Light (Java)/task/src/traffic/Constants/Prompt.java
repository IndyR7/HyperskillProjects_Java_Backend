package traffic.Constants;

public class Prompt {
    public final static String MAIN_MENU = """
            Menu:
            1. Add
            2. Delete
            3. System
            0. Quit""";

    public static final String WELCOME = "Welcome to the traffic management system!";
    public static final String INPUT_NUMBER_OF_ROADS = "Input the number of roads:";
    public static final String INPUT_INTERVAL = "Input the interval:";
    public static final String INPUT_ROAD_NAME = "Input road name:";
    public static final String INCORRECT_INPUT = "Error! Incorrect input. Try again:";
    public static final String INCORRECT_OPTION = "incorrect option";
    public static final String SYSTEM_INFO = """
            ! %ds. have passed since system startup !
            ! Number of roads: %d !
            ! Interval: %d !
            """;

    public static final String PRESS_ENTER_TO_CONTINUE = "! Press \"Enter\" to open menu !";
    public static final String ROAD_ADDED = "%s Added!";
    public  static final String ROAD_DELETED = "%s deleted!";
    public static final String ROAD_STATUS = "Road \"%s\" will be %s for %ds.";
    public static final String QUEUE_IS_FULL = "Queue is full";
    public static final String QUEUE_IS_EMPTY = "Queue is empty";
    public static final String BYE = "Bye!";
}

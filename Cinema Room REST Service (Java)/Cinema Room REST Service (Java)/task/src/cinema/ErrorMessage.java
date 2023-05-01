package cinema;

public class ErrorMessage {
    public static final String UNAVAILABLE = "{\"error\": \"The ticket has been already purchased!\"}";
    public static final String NON_EXISTING_SEAT = "{\"error\": \"The number of a row or a column is out of bounds!\"}";
    public static final String WRONG_TOKEN = "{\"error\": \"Wrong token!\"}";
    public static final String WRONG_PASSWORD = "{\"error\": \"The password is wrong!\"}";
}

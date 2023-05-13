package tracker.Constants;

public class Pattern {
    public static final String FIRST_NAME = "([A-Za-z]+['-]?[A-Za-z]+)";
    public static final String LAST_NAME = "([A-Za-z]+[ '-]?)+[A-Za-z]+";
    public static final String MAIL = "[A-Za-z0-9'.-]+@[A-Za-z0-9]+\\.[A-Za-z0-9]+";
    public static final String JAVA_POINTS = "([0-9]|([0-5]?[0-9]?[0-9])|(600))";
    public static final String DSA_POINTS = "([0-9]|([0-3]?[0-9]?[0-9])|(400))";
    public static final String DATABASES_POINTS = "([0-9]|([0-3]?[0-9][0-9]?)|(4[0-7][0-9])|(480))";
    public static final String SPRING_POINTS = "([0-9]|([0-4]?[0-9]?[0-9])|(5[0-4][0-9])|(450))";
    public static final String POINTS = JAVA_POINTS + " "
            + DSA_POINTS + " "
            + DATABASES_POINTS + " "
            + SPRING_POINTS;
}
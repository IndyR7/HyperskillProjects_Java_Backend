package tracker.Constants;

public class Prompt {
    public static final String TITLE = "Learning Progress Tracker";
    public static final String EXIT_HINT = "Enter 'exit' to exit the program.";
    public static final String ENTER_COURSE_NAME = "Type the name of a course to see details or 'back' to quit:";
    public static final String ENTER_CREDENTIALS = "Enter student credentials or 'back' to return:";
    public static final String ENTER_SCORES = "Enter an id and points or 'back' to return:";
    public static final String FIND_SCORES = "Enter an id or 'back' to return:";
    public static final String STUDENTS = "Students:\n";
    public static final String COURSE_SCORES = "%s points: %s";
    public static final String COURSE_SCORE = "%s=%d";
    public static final String BYE = "Bye!";
    public static final String SUCCESSFUL_STUDENT_ADD = "The student has been added.";
    public static final String SUCCESSFUL_SCORE_ADD = "Points updated.";
    public static final String TOTAL_STUDENTS_ADDED = "Total %d students have been added.";
    public static final String MOST_POPULAR = "Most popular: %s";
    public static final String LEAST_POPULAR = "Least popular: %s";
    public static final String HIGHEST_ACTIVITY = "Highest activity: %s";
    public static final String LOWEST_ACTIVITY = "Lowest activity: %s";
    public static final String EASIEST_COURSE = "Easiest course: %s";
    public static final String HARDEST_COURSE = "Hardest course: %s";
    public static final String STATS_HEAD = "id     points     completed";
    public static final String SCORE_ROW = "%s %d %s%%";
    public static final String COURSE_COMPLETED = """
            To: %s
            Re: Your Learning Progress
            Hello, %s! You have accomplished our %s course!""";
    public static final String TOTAL_NOTIFIED = "Total %d students have been notified.";
    public static final String NOT_AVAILABLE = "n/a";
    public static final String INCORRECT_CREDENTIALS = "Incorrect credentials.";
    public static final String INCORRECT_FIRST_NAME = "Incorrect first name.";
    public static final String INCORRECT_LAST_NAME = "Incorrect last name.";
    public static final String INCORRECT_MAIL_ADDRESS = "Incorrect email.";
    public static final String INCORRECT_POINTS_FORMAT = "Incorrect points format.";
    public static final String UNSUCCESSFUL_STUDENT_ADD = "This email is already taken.";
    public static final String NO_STUDENTS_FOUND = "No students found.";
    public static final String NO_STUDENT_FOUND = "No student is found for id=%s.";
    public static final String NO_INPUT = "No input.";
    public static final String UNKNOWN_COMMAND = "Unknown command!";
    public static final String UNKNOWN_COURSE = "Unknown course.";
}
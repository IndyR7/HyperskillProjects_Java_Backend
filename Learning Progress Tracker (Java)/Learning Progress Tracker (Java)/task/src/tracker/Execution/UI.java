package tracker.Execution;

import tracker.Constants.Command;
import tracker.Constants.Prompt;
import tracker.Courses.*;
import tracker.Persons.Student;
import tracker.Persons.Students;

import java.util.Scanner;

public class UI {
    private final Scanner scanner;

    public UI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        System.out.println(Prompt.TITLE);

        mainLoop:
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();

            switch (input) {
                case Command.EXIT:
                    break mainLoop;
                case Command.EMPTY:
                    System.out.println(Prompt.NO_INPUT);
                    break;
                case Command.ADD_STUDENTS:
                    addStudents();
                    break;
                case Command.LIST:
                    System.out.println(getList());
                    break;
                case Command.ADD_POINTS:
                    addPoints();
                    break;
                case Command.FIND:
                    findScores();
                    break;
                case Command.STATISTICS:
                    runStatistics();
                    break;
                case Command.NOTIFY:
                    System.out.println(Notifier.getNotifications());
                    break;
                case Command.BACK:
                    System.out.println(Prompt.EXIT_HINT);
                    break;
                default:
                    System.out.println(Prompt.UNKNOWN_COMMAND);
            }
        }

        System.out.println(Prompt.BYE);
    }

    private void addStudents() {
        System.out.println(Prompt.ENTER_CREDENTIALS);

        int total = 0;

        while (true) {
            String[] input = scanner.nextLine().trim().split(" ");

            if (input[0].equals(Command.BACK)) {
                break;
            }

            if (InputHandler.isValidCredentialsInput(input)) {
                String firstName = input[0];
                String lastName = input[1];
                String email = input[2];

                if (Students.isUniqueEmail(email)) {
                    Students.addStudent(new Student(firstName, lastName, email));
                    total++;

                    System.out.println(Prompt.SUCCESSFUL_STUDENT_ADD);
                    continue;
                }

                System.out.println(Prompt.UNSUCCESSFUL_STUDENT_ADD);
                continue;
            }

            System.out.println(InputHandler.getErrorMessage(Command.ADD_STUDENTS));
        }

        System.out.printf(Prompt.TOTAL_STUDENTS_ADDED + "\n", total);
    }

    private String getList() {
        return Students.getStudents().isEmpty() ? Prompt.NO_STUDENTS_FOUND
                : Students.getStudentsListedById();
    }

    private void findScores() {
        System.out.println(Prompt.FIND_SCORES);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals(Command.BACK)) {
                break;
            }

            if (Students.getStudent(input) != null) {
                Student student = Students.getStudent(input);

                System.out.println(student.getScores());
                continue;
            }

            System.out.printf((Prompt.NO_STUDENT_FOUND) + "\n", input);
        }
    }

    private void addPoints() {
        System.out.println(Prompt.ENTER_SCORES);

        while (true) {
            String[] input = scanner.nextLine().split(" ");

            if (input[0].equals(Command.BACK)) {
                break;
            }

            if (InputHandler.isValidPointsInput(input)) {
                String id = input[0];

                for (int i = 1; i < input.length; i++) {
                    Course course = Courses.getCourse(i - 1);
                    int points = Integer.parseInt(input[i]);

                    course.updateCourse(id, points);
                }

                System.out.println(Prompt.SUCCESSFUL_SCORE_ADD);
                continue;
            }

            System.out.println(InputHandler.getErrorMessage(Command.ADD_POINTS));
        }
    }

    private void runStatistics() {
        System.out.println(Prompt.ENTER_COURSE_NAME);
        System.out.println(Statistics.getStatistics());

        while (true) {
            String input = scanner.nextLine().toLowerCase();

            if (input.equals(Command.BACK)) {
                break;
            }

            System.out.println(Statistics.getCourseStatistics(input));
        }
    }
}
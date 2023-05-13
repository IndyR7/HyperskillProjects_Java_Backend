package tracker.Persons;

import tracker.Constants.Prompt;

import java.util.HashMap;
import java.util.Map;

public class Students {
    private static final Map<String, Student> students = new HashMap<>();
    private static final Map<String, Student> emails = new HashMap<>();

    public static void addStudent(Student student) {
        String id = student.getId();
        String email = student.getEmail();

        students.put(id, student);
        emails.put(email, student);
    }

    public static Map<String, Student> getStudents() {
        return students;
    }

    public static String getNextId() {
        return String.valueOf(students.size() + 1);
    }

    public static Student getStudent(String id) {
        return students.getOrDefault(id, null);
    }

    public static String getStudentsListedById() {
        StringBuilder sbStudents = new StringBuilder();

        sbStudents.append(Prompt.STUDENTS);
        students.forEach((id, student) -> sbStudents.append(id).append("\n"));

        return sbStudents.toString().trim();
    }

    public static boolean isUniqueEmail(String email) {
        return !emails.containsKey(email);
    }
}
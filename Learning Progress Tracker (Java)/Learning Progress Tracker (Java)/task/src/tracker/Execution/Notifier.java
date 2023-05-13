package tracker.Execution;

import tracker.Constants.Prompt;
import tracker.Courses.Course;
import tracker.Courses.Courses;
import tracker.Persons.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notifier {
    private static final Map<Course, List<String>> studentsToNotify = getMapOfStudentsToNotify();
    private static final List<String> studentsNotified = new ArrayList<>();

    public static String getNotifications() {
        StringBuilder sbNotifications = new StringBuilder();
        int count = 0;

        for (Course course : Courses.getCourses()) {
            for (Student student : course.getStudentsWithMaxScore()) {
                if (!studentsToNotify.get(course).contains(student.getId())) {
                    String notification = String.format(Prompt.COURSE_COMPLETED,
                            student.getEmail(),
                            student.getFullName(),
                            course.getName());

                    sbNotifications.append(notification).append("\n");
                    studentsToNotify.get(course).add(student.getId());
                }

                if (!studentsNotified.contains(student.getId())) {
                    studentsNotified.add(student.getId());
                    count++;
                }
            }
        }

        String totalNotified = String.format(Prompt.TOTAL_NOTIFIED, count);

        return sbNotifications.append(totalNotified).toString();
    }

    private static Map<Course, List<String>> getMapOfStudentsToNotify() {
        Map<Course, List<String>> studentsToNotify = new HashMap<>();

        for (Course course : Courses.getCourses()) {
            studentsToNotify.put(course, new ArrayList<>());
        }

        return studentsToNotify;
    }
}
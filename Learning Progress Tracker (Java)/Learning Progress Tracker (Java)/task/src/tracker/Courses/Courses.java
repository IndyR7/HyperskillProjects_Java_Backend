package tracker.Courses;

import java.util.ArrayList;
import java.util.List;

public class Courses {
    private static final List<Course> courses = getListOfCourses();

    private static List<Course> getListOfCourses() {
        List<Course> courses = new ArrayList<>();

        courses.add(new JavaCourse());
        courses.add(new DSACourse());
        courses.add(new DatabaseCourse());
        courses.add(new SpringCourse());

        return courses;
    }

    public static Course getCourse(int i) {
        return courses.get(i);
    }
    public static List<Course> getCourses() {
        return courses;
    }
}

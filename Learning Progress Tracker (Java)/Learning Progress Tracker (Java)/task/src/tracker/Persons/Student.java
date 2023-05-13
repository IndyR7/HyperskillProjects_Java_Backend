package tracker.Persons;

import tracker.Constants.Prompt;
import tracker.Courses.*;

import java.util.ArrayList;
import java.util.List;

public class Student implements Person {
    private String firstName;
    private String lastName;
    private String email;
    private final String id;

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = Students.getNextId();
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Override
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getScores() {
        StringBuilder sbCourseScores = new StringBuilder();

        for (Course course : Courses.getCourses()) {
            String courseScore = String.format(Prompt.COURSE_SCORE, course.getName(), course.getScore(id));

            sbCourseScores.append(courseScore)
                    .append("; ");
        }

        sbCourseScores.delete(sbCourseScores.length() - 2, sbCourseScores.length());

        return String.format(Prompt.COURSE_SCORES,
                id, sbCourseScores);
    }
}
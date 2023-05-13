package tracker.Courses;

import tracker.Persons.Student;

import java.util.List;
import java.util.Map;

public interface Course {
    String getName();

    int getScore(String id);

    void updateCourse(String id, int points);

    double getAverage();

    Map<String, Student> getStudentsEnrolled();

    Map<String, Integer> getScores();

    int getTotalActivity();

    int getTotalStudentsEnrolled();

    String getCourseStatistics();
    List<Student> getStudentsWithMaxScore();
}
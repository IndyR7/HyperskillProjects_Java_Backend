package tracker.Courses;

import tracker.Constants.Prompt;

import java.util.List;
import java.util.stream.Collectors;

public class Statistics {
    private static final List<Course> courses = Courses.getCourses();

    public static String getCourseStatistics(String name) {
        for (Course course : courses) {
            if (name.equals(course.getName().toLowerCase())) {
                return course.getCourseStatistics();
            }
        }

        return Prompt.UNKNOWN_COURSE;
    }

    private static final String[] categories = {
            Prompt.MOST_POPULAR,
            Prompt.LEAST_POPULAR,
            Prompt.HIGHEST_ACTIVITY,
            Prompt.LOWEST_ACTIVITY,
            Prompt.EASIEST_COURSE,
            Prompt.HARDEST_COURSE
    };

    public static String getStatistics() {
        StringBuilder sbStatistics = new StringBuilder();

        for (String category : categories) {
            String categoryStatistics = getCourseStatisticsByType(category);

            sbStatistics.append(categoryStatistics).append("\n");
        }

        return sbStatistics.toString().trim();
    }

    private static String getCourseStatisticsByType(String category) {
        StringBuilder sbResult = new StringBuilder();

        List<String> coursesSortedByType =
                switch (category) {
                    case Prompt.MOST_POPULAR -> getMostPopularCourseNames();
                    case Prompt.LEAST_POPULAR -> getLeastPopularCourseNames();
                    case Prompt.HIGHEST_ACTIVITY -> getHighestActivityCourseNames();
                    case Prompt.LOWEST_ACTIVITY -> getLowestActivityCourseNames();
                    case Prompt.EASIEST_COURSE -> getEasiestCourseNames();
                    default -> getHardestCourseNames();
                };

        coursesSortedByType.forEach(s -> sbResult.append(s).append(", "));

        if (!sbResult.isEmpty()) {
            sbResult.delete(sbResult.length() - 2, sbResult.length());

            return String.format(category, sbResult);
        }

        return String.format(category, Prompt.NOT_AVAILABLE);
    }

    private static List<String> getMostPopularCourseNames() {
        int maxEnrolled = courses.stream().mapToInt(Course::getTotalStudentsEnrolled).max().orElse(0);

        return courses.stream()
                .filter(course -> course.getTotalStudentsEnrolled() == maxEnrolled)
                .filter(course -> course.getTotalStudentsEnrolled() != 0)
                .map(Course::getName)
                .collect(Collectors.toList());
    }

    private static List<String> getLeastPopularCourseNames() {
        int minEnrolled = courses.stream().mapToInt(Course::getTotalStudentsEnrolled).min().orElse(0);
        List<String> mostPopularCourseNames = getMostPopularCourseNames();

        return courses.stream()
                .filter(course -> course.getTotalStudentsEnrolled() == minEnrolled)
                .filter(course -> course.getTotalStudentsEnrolled() != 0)
                .map(Course::getName)
                .filter(name -> !mostPopularCourseNames.contains(name))
                .collect(Collectors.toList());
    }

    private static List<String> getHighestActivityCourseNames() {
        int maxSubmissions = courses.stream().mapToInt(Course::getTotalActivity).max().orElse(0);

        return courses.stream()
                .filter(course -> course.getTotalActivity() == maxSubmissions)
                .filter(course -> course.getTotalStudentsEnrolled() != 0)
                .map(Course::getName)
                .collect(Collectors.toList());
    }

    private static List<String> getLowestActivityCourseNames() {
        int minSubmissions = courses.stream().mapToInt(Course::getTotalActivity).min().orElse(0);
        List<String> highestActivityCourseNames = getHighestActivityCourseNames();

        return courses.stream()
                .filter(course -> course.getTotalActivity() == minSubmissions)
                .filter(course -> course.getTotalStudentsEnrolled() != 0)
                .map(Course::getName)
                .filter(name -> !highestActivityCourseNames.contains(name))
                .collect(Collectors.toList());
    }

    private static List<String> getEasiestCourseNames() {
        double lowestAvg = courses.stream().mapToDouble(Course::getAverage).max().orElse(0);

        return courses.stream()
                .filter(course -> course.getAverage() == lowestAvg)
                .filter(course -> course.getTotalStudentsEnrolled() != 0)
                .map(Course::getName)
                .collect(Collectors.toList());
    }

    private static List<String> getHardestCourseNames() {
        double highestAvg = courses.stream().mapToDouble(Course::getAverage).min().orElse(0);
        List<String> hardestCourseNames = getEasiestCourseNames();

        return courses.stream()
                .filter(course -> course.getAverage() == highestAvg)
                .filter(course -> course.getTotalStudentsEnrolled() != 0)
                .map(Course::getName)
                .filter(name -> !hardestCourseNames.contains(name))
                .collect(Collectors.toList());
    }
}
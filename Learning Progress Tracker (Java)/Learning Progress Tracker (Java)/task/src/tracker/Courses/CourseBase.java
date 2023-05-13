package tracker.Courses;

import tracker.Constants.MaxScores;
import tracker.Constants.Prompt;
import tracker.Persons.Student;
import tracker.Persons.Students;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CourseBase implements Course {
    private final String name;
    private final Map<String, Student> studentsEnrolled;
    private final Map<String, Integer> scores;
    private final int maxScore;
    private int totalActivity;
    private int totalScores;

    public CourseBase(String name, int maxScore) {
        this.name = name;
        this.studentsEnrolled = new HashMap<>();
        this.scores = new HashMap<>();
        this.totalActivity = 0;
        this.totalScores = 0;
        this.maxScore = maxScore;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getScore(String id) {
        return this.scores.getOrDefault(id, 0);
    }

    @Override
    public void updateCourse(String id, int points) {
        if (Students.getStudent(id) != null && points > 0) {
            int score = scores.getOrDefault(id, 0) + points;

            studentsEnrolled.putIfAbsent(id, Students.getStudent(id));
            scores.put(id, score);

            totalScores += points;
            totalActivity++;
        }
    }

    @Override
    public int getTotalActivity() {
        return this.totalActivity;
    }

    @Override
    public int getTotalStudentsEnrolled() {
        return this.studentsEnrolled.size();
    }

    @Override
    public Map<String, Student> getStudentsEnrolled() {
        return this.studentsEnrolled;
    }

    @Override
    public Map<String, Integer> getScores() {
        return this.scores;
    }

    @Override
    public String getCourseStatistics() {
        StringBuilder sbStatistics = new StringBuilder();
        Map<String, Integer> scoresSorted = getScoresSorted();

        sbStatistics.append(this.name)
                .append("\n")
                .append(Prompt.STATS_HEAD)
                .append("\n");

        for (String id : scoresSorted.keySet()) {
            int points = scoresSorted.get(id);
            double percentage = BigDecimal.valueOf((double) points / maxScore * 100)
                    .setScale(1, RoundingMode.HALF_UP)
                    .doubleValue();

            DecimalFormat df = new DecimalFormat("0.0#");
            df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));

            String formatted = df.format(percentage);
            String scoreRow = String.format(Prompt.SCORE_ROW, id, points, formatted);

            sbStatistics.append(scoreRow)
                    .append("\n");
        }

        return sbStatistics.toString();
    }

    @Override
    public double getAverage() {
        return (double) totalScores / totalActivity;
    }

    @Override
    public List<Student> getStudentsWithMaxScore() {
        List<Student> studentsWithMaxScore = new ArrayList<>();

        for (String id : scores.keySet()) {
            int score = scores.get(id);

            if (score == maxScore) {
                Student student = Students.getStudent(id);

                studentsWithMaxScore.add(student);
            }
        }

        return studentsWithMaxScore;
    }
    private Map<String, Integer> getScoresSorted() {
        return scores.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
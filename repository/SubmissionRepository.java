package tracker.repository;

import tracker.entity.Submission;
import tracker.utils.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SubmissionRepository {

    private static SubmissionRepository instance;
    private static final List<Submission> submissions = new ArrayList<>();
    private static long id = 10000L;

    private SubmissionRepository() {}

    public static SubmissionRepository getInstance() {
        return instance == null ? new SubmissionRepository() : instance;
    }

    public void addSubmission(Submission submission) {
        submission.setId(id++);
        submissions.add(submission);
    }

    public List<Submission> findAllSubmissionsByStudentId(String studentId) {
        return submissions.stream()
                .filter(submission -> submission.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public long countPointsByStudentIdAndCourse(String studentId, String course) {
        return submissions.stream()
                .filter(submission -> submission.getStudentId().equals(studentId))
                .mapToLong(submission -> {
                    if (course.equals(Course.JAVA.name))
                        return submission.getJavaPoints();
                    if (course.equals(Course.DSA.name))
                        return submission.getDsaPoints();
                    if (course.equals(Course.DATABASES.name))
                        return submission.getDatabasesPoints();
                    if (course.equals(Course.SPRING.name))
                        return submission.getSpringPoints();
                    return 0;
                }).sum();
    }

    public List<Submission> findAll() {
        return submissions;
    }

    public long countEnrolledStudentsInCourse(String course) {
        return submissions.stream().filter(submission -> {
            if (course.equals(Course.JAVA.name))
                return submission.getJavaPoints() > 0;
            if (course.equals(Course.DSA.name))
                return submission.getDsaPoints() > 0;
            if (course.equals(Course.DATABASES.name))
                return submission.getDatabasesPoints() > 0;
            if (course.equals(Course.SPRING.name))
                return submission.getSpringPoints() > 0;
            return false;
        }).map(Submission::getStudentId).distinct().count();
    }

    public List<String> findMaxActivityCourses() {
        if (submissions.isEmpty())
            return List.of("n/a");
        Map<String, Long> courseActivities = getCourseActivities();
        long maxActivity = getMaxActivity(courseActivities);
        return courseActivities.entrySet().stream()
                .filter(stringLongEntry -> stringLongEntry.getValue() == maxActivity)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private long getMaxActivity(Map<String, Long> courseActivities) {
        return getCourseActivities().values().stream().mapToLong(Long::longValue).max().orElse(0);
    }

    public List<String> findMinActivityCourses() {
        Map<String, Long> courseActivities = getCourseActivities();
        long minActivity = getMinActivity(courseActivities);
        long maxActivity = getMaxActivity(courseActivities);
        if (maxActivity == minActivity)
            return List.of("n/a");
        return courseActivities.entrySet().stream()
                .filter(stringLongEntry -> stringLongEntry.getValue() == minActivity)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private long getMinActivity(Map<String, Long> courseActivities) {
        return getCourseActivities().values().stream().mapToLong(Long::longValue).min().orElse(0);
    }

    private Map<String, Long> getCourseActivities() {
        return Map.of(
                Course.JAVA.name, countActivityByCourse(Course.JAVA.name),
                Course.DSA.name, countActivityByCourse(Course.DSA.name),
                Course.DATABASES.name, countActivityByCourse(Course.DATABASES.name),
                Course.SPRING.name, countActivityByCourse(Course.SPRING.name)
        );
    }

    private long countActivityByCourse(String course) {
        return submissions.stream().filter(submission -> {
            if (course.equals(Course.JAVA.name))
                return submission.getJavaPoints() > 0;
            if (course.equals(Course.DSA.name))
                return submission.getDsaPoints() > 0;
            if (course.equals(Course.DATABASES.name))
                return submission.getDatabasesPoints() > 0;
            if (course.equals(Course.SPRING.name))
                return submission.getSpringPoints() > 0;
            return false;
        }).count();
    }

    public List<String> findMinAverageScoreCourses() {
        if (submissions.isEmpty())
            return List.of("n/a");
        Map<String, Double> averageScores = getAverageScores();
        double minAverageScore = getMinAverageScore(averageScores);
        return averageScores.entrySet().stream()
                .filter(stringLongEntry -> stringLongEntry.getValue() == minAverageScore)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> findMaxAverageScoreCourses() {
        if (submissions.isEmpty())
            return List.of("n/a");
        Map<String, Double> averageScores = getAverageScores();
        double minAverageScore = getMinAverageScore(averageScores);
        double maxAverageScore = getMaxAverageScore(averageScores);
        if (minAverageScore == maxAverageScore)
            return List.of("n/a");
        return averageScores.entrySet().stream()
                .filter(stringLongEntry -> stringLongEntry.getValue() == maxAverageScore)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private double getMinAverageScore(Map<String, Double> averageScores) {
        return averageScores.values().stream().mapToDouble(Double::doubleValue).min().orElse(0);
    }

    private double getMaxAverageScore(Map<String, Double> averageScores) {
        return averageScores.values().stream().mapToDouble(Double::doubleValue).max().orElse(0);
    }

    private Map<String, Double> getAverageScores() {
        return Map.of(
                Course.JAVA.name, countAverageScoreByCourse(Course.JAVA.name),
                Course.DSA.name, countAverageScoreByCourse(Course.DSA.name),
                Course.DATABASES.name, countAverageScoreByCourse(Course.DATABASES.name),
                Course.SPRING.name, countAverageScoreByCourse(Course.SPRING.name)
        );
    }

    private double countAverageScoreByCourse(String course) {
        return submissions.stream().mapToLong(submission -> {
            if (course.equals(Course.JAVA.name))
                return submission.getJavaPoints();
            if (course.equals(Course.DSA.name))
                return submission.getDsaPoints();
            if (course.equals(Course.DATABASES.name))
                return submission.getDatabasesPoints();
            if (course.equals(Course.SPRING.name))
                return submission.getSpringPoints();
            return 0;
        })
                .filter(score -> score > 0)
                .summaryStatistics()
                .getAverage();
    }

    public long findPointsByStudentId(String studentId, Course course) {
        return submissions.stream()
                .filter(submission -> submission.getStudentId().equals(studentId))
                .mapToLong(submission -> {
                    if (course.equals(Course.JAVA))
                        return submission.getJavaPoints();
                    if (course.equals(Course.DSA))
                        return submission.getDsaPoints();
                    if (course.equals(Course.DATABASES))
                        return submission.getDatabasesPoints();
                    if (course.equals(Course.SPRING))
                        return submission.getSpringPoints();
                    return 0;
                }).sum();
    }

    public List<String> findAllStudentIds() {
        return submissions.stream().map(Submission::getStudentId).distinct().collect(Collectors.toList());
    }

}

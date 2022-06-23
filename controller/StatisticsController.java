package tracker.controller;

import tracker.repository.SubmissionRepository;
import tracker.utils.Course;
import tracker.utils.Input;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tracker.utils.Commands.*;

public class StatisticsController {

    private static final SubmissionRepository SUBMISSION_REPOSITORY = SubmissionRepository.getInstance();

    public static void run() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        printStatistics();
        boolean back = false;
        while (!back) {
            String input = Input.getString().toLowerCase();

            if (isBack(input))
                back = true;

            else if (isStatistics(input))
                printStatistics();

            else if (isJava(input))
                printJava();

            else if (isDsa(input))
                printDsa();

            else if (isDatabases(input))
                printDatabases();

            else if (isSpring(input))
                printSpring();

            else
                System.out.println("Unknown course.");

        }
    }

    private static void printStatistics() {
        System.out.printf(
                "Most popular: %s\n" +
                "Least popular: %s\n" +
                "Highest activity: %s\n" +
                "Lowest activity: %s\n" +
                "Easiest course: %s\n" +
                "Hardest course: %s\n",
                getMostPopular(),
                getLeastPopular(),
                getHighestActivity(),
                getLowestActivity(),
                getEasiestCourse(),
                getHardestCourse()
        );
    }

    private static String getMostPopular() {
        if (SUBMISSION_REPOSITORY.findAll().isEmpty())
            return "n/a";
        Map<String, Long> courseNumberOfEnrolledStudentsMap = getCourseNumberOfEnrolledStudentsMap();
        long max = getMaxStudents(courseNumberOfEnrolledStudentsMap);
        StringBuilder builder = new StringBuilder();
        courseNumberOfEnrolledStudentsMap.forEach((k, v) -> {
            if (v == max)
                builder.append(k).append(", ");
        });
        builder.replace(builder.length() - 2, builder.length(), "");
        return builder.toString();
    }

    private static String getLeastPopular() {
        if (SUBMISSION_REPOSITORY.findAll().isEmpty())
            return "n/a";
        Map<String, Long> courseNumberOfEnrolledStudentsMap = getCourseNumberOfEnrolledStudentsMap();
        long max = getMaxStudents(courseNumberOfEnrolledStudentsMap);
        long min = getMinStudents(courseNumberOfEnrolledStudentsMap);
        if (max == min)
            return "n/a";
        StringBuilder builder = new StringBuilder();
        courseNumberOfEnrolledStudentsMap.forEach((k, v) -> {
            if (v == min)
                builder.append(k).append(", ");
        });
        deleteLastTwoChars(builder);
        return builder.toString();
    }

    private static String getHighestActivity() {
        StringBuilder builder = new StringBuilder();
        SUBMISSION_REPOSITORY.findMaxActivityCourses().forEach(s -> builder.append(s).append(", "));
        deleteLastTwoChars(builder);
        return builder.toString();
    }

    private static void deleteLastTwoChars(StringBuilder builder) {
        builder.replace(builder.length() - 2, builder.length(), "");
    }

    private static String getLowestActivity() {
        StringBuilder builder = new StringBuilder();
        SUBMISSION_REPOSITORY.findMinActivityCourses().forEach(s -> builder.append(s).append(", "));
        deleteLastTwoChars(builder);
        return builder.toString();
    }

    private static String getEasiestCourse() {
        StringBuilder builder = new StringBuilder();
        SUBMISSION_REPOSITORY.findMaxAverageScoreCourses().forEach(s -> builder.append(s).append(", "));
        deleteLastTwoChars(builder);
        return builder.toString();
    }

    private static String getHardestCourse() {
        StringBuilder builder = new StringBuilder();
        SUBMISSION_REPOSITORY.findMinAverageScoreCourses().forEach(s -> builder.append(s).append(", "));
        deleteLastTwoChars(builder);
        return builder.toString();
    }

    private static long countEnrolledStudentsInCourse(String course) {
        return SUBMISSION_REPOSITORY.countEnrolledStudentsInCourse(course);
    }

    private static Map<String, Long> getCourseNumberOfEnrolledStudentsMap() {
        return Map.of(
                Course.JAVA.name, countEnrolledStudentsInCourse(Course.JAVA.name),
                Course.DSA.name, countEnrolledStudentsInCourse(Course.DSA.name),
                Course.DATABASES.name, countEnrolledStudentsInCourse(Course.DATABASES.name),
                Course.SPRING.name, countEnrolledStudentsInCourse(Course.SPRING.name));
    }

    private static long getMaxStudents(Map<String, Long> courseNumberOfEnrolledStudentsMap) {
        return courseNumberOfEnrolledStudentsMap.values().stream().mapToLong(Long::longValue).max().orElse(0);
    }

    private static long getMinStudents(Map<String, Long> courseNumberOfEnrolledStudentsMap) {
        return courseNumberOfEnrolledStudentsMap.values().stream().mapToLong(Long::longValue).min().orElse(0);
    }

    private static void printJava() {
        System.out.println(Course.JAVA);
        printCourseStatistics(Course.JAVA);
    }

    private static void printDsa() {
        System.out.println(Course.DSA);
        printCourseStatistics(Course.DSA);
    }

    private static void printDatabases() {
        System.out.println(Course.DATABASES);
        printCourseStatistics(Course.DATABASES);
    }

    private static void printSpring() {
        System.out.println(Course.SPRING);
        printCourseStatistics(Course.SPRING);
    }

    private static void printCourseStatistics(Course course) {
        System.out.println("id\tpoints\tcompleted");
        getStudentIdAndPointsByCourseMap(course).entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .sorted(Map.Entry.comparingByKey())
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEachOrdered(e -> {
                    System.out.printf("%s\t%d\t%.1f%%\n",
                            e.getKey(),
                            e.getValue(),
                            100.0 * e.getValue() / course.points
                            );
                });
    }

    private static List<String> getAllStudentIds() {
        return SUBMISSION_REPOSITORY.findAllStudentIds();
    }

    private static long countPointsByStudentIdAndByCourse(String studentId, Course course) {
        return SUBMISSION_REPOSITORY.findPointsByStudentId(studentId, course);
    }

    private static Map<String, Long> getStudentIdAndPointsByCourseMap(Course course) {
        Map<String, Long> studentIdAndPointsByCourseMap = new HashMap<>();
        getAllStudentIds().forEach(studentId ->
                studentIdAndPointsByCourseMap.put(studentId, countPointsByStudentIdAndByCourse(studentId, course))
        );
        return studentIdAndPointsByCourseMap;
    }

}

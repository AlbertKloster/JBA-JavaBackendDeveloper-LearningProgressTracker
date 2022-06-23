package tracker.repository;

import tracker.entity.CompletionNotification;
import tracker.entity.Student;
import tracker.utils.Course;

import java.util.ArrayList;
import java.util.List;

public class CompletionNotificationRepository {

    private static CompletionNotificationRepository instance;

    private static final List<CompletionNotification> notifications = new ArrayList<>();

    private static long id = 1L;

    private CompletionNotificationRepository() {}

    public static CompletionNotificationRepository getInstance() {
        return instance == null ? new CompletionNotificationRepository() : instance;
    }

    public void addCompletionNotification(CompletionNotification completionNotification) {
        notifications.add(completionNotification);
    }

    public boolean isNotified(Student student, Course course) {
        return notifications.stream()
                .filter(completionNotification -> completionNotification.getStudent().getId().equals(student.getId()))
                .anyMatch(completionNotification -> completionNotification.getCourse().equals(course.name));
    }

    public boolean isNotNotified(Student student, Course course) {
        return !isNotified(student, course);
    }

}

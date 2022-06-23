package tracker.controller;

import tracker.entity.CompletionNotification;
import tracker.entity.Student;
import tracker.repository.CompletionNotificationRepository;
import tracker.repository.StudentRepository;
import tracker.repository.SubmissionRepository;
import tracker.utils.Course;

import java.util.concurrent.atomic.AtomicInteger;

public class NotifyController {

    private static final StudentRepository STUDENT_REPOSITORY = StudentRepository.getInstance();
    private static final SubmissionRepository SUBMISSION_REPOSITORY = SubmissionRepository.getInstance();
    private static final CompletionNotificationRepository COMPLETION_NOTIFICATION_REPOSITORY = CompletionNotificationRepository.getInstance();

    public static void run() {
        getCompletionNotifications();
    }

    private static void getCompletionNotifications() {
        AtomicInteger studentCount = new AtomicInteger();
        STUDENT_REPOSITORY.getAllStudents().forEach(student -> {
            int notificationCount = 0;
            for (Course course : Course.values()) {
                if (isFirstComplete(student, course)) {
                    notificationCount++;
                    sendCompletionNotification(student, course);
                }
            }
            if (notificationCount > 0)
                studentCount.getAndIncrement();
        });
        System.out.printf("Total %s students have been notified.\n", studentCount);
    }

    private static boolean isFirstComplete(Student student, Course course) {
        return SUBMISSION_REPOSITORY.findPointsByStudentId(student.getId(), course) >= course.points &&
                COMPLETION_NOTIFICATION_REPOSITORY.isNotNotified(student, course);
    }

    private static void sendCompletionNotification(Student student, Course course) {
        COMPLETION_NOTIFICATION_REPOSITORY.addCompletionNotification(new CompletionNotification(course.name, student));
        System.out.printf("To: %s\n" +
                "Re: Your Learning Progress\n" +
                "Hello, %s! You have accomplished our %s course!\n",
                student.getEmail(),
                getFullName(student),
                course.name
                );
    }

    private static String getFullName(Student student) {
        return student.getFirstName() + (student.getLastName().isBlank() ? "" : " " + student.getLastName());
    }

}

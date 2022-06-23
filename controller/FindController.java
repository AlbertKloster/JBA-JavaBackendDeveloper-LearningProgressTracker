package tracker.controller;

import tracker.entity.Student;
import tracker.repository.StudentRepository;
import tracker.repository.SubmissionRepository;
import tracker.utils.Input;

public class FindController {
    private static final StudentRepository STUDENT_REPOSITORY = StudentRepository.getInstance();
    private static final SubmissionRepository SUBMISSION_REPOSITORY = SubmissionRepository.getInstance();

    public static void run() {

        System.out.println("Enter an id or 'back' to return:");

        boolean back = false;
        while (!back) {
            String input = Input.getString();
            if (isBack(input))
                back = true;
            else
                printPointsById(input);
        }

    }

    private static boolean isBack(String input) {
        return input.equals("back");
    }

    private static void printPointsById(String id) {
        Student student = STUDENT_REPOSITORY.findStudentById(id);
        if (student == null)
            System.out.printf("No student is found for id=%s.\n", id);
        else
            System.out.printf("%s points: Java=%d; DSA=%d; Databases=%d; Spring=%d\n",
                    id,
                    SUBMISSION_REPOSITORY.countPointsByStudentIdAndCourse(id, "Java"),
                    SUBMISSION_REPOSITORY.countPointsByStudentIdAndCourse(id, "DSA"),
                    SUBMISSION_REPOSITORY.countPointsByStudentIdAndCourse(id, "Databases"),
                    SUBMISSION_REPOSITORY.countPointsByStudentIdAndCourse(id, "Spring")
            );
    }
}

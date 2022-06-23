package tracker.controller;

import tracker.entity.Student;
import tracker.entity.Submission;
import tracker.repository.StudentRepository;
import tracker.repository.SubmissionRepository;
import tracker.utils.Input;

import static tracker.utils.Commands.*;

public class AddPointsController {
    private static final StudentRepository STUDENT_REPOSITORY = StudentRepository.getInstance();
    private static final SubmissionRepository SUBMISSION_REPOSITORY = SubmissionRepository.getInstance();

    public static void run() {
        System.out.println("Enter an id and points or 'back' to return:");
        boolean back = false;
        while (!back) {
            String input = Input.getString();
            if (isBack(input))
                back = true;
            else
                updatePoints(input);
        }
    }

    private static void updatePoints(String input) {
        if (!input.matches("\\w+\\s\\d+\\s\\d+\\s\\d+\\s\\d+")) {
            System.out.println("Incorrect points format.");
            return;
        }

        String[] inputArray = input.split("\\s");
        String id = getId(inputArray);
        Student student = STUDENT_REPOSITORY.findStudentById(id);

        if (student == null) {
            System.out.printf("No student is found for id=%s.\n", id);
            return;
        }

        SUBMISSION_REPOSITORY.addSubmission(
                new Submission(
                        getJavaPoints(inputArray),
                        getDsaPoints(inputArray),
                        getDatabasesPoints(inputArray),
                        getSpringPoints(inputArray),
                        id)
        );

        System.out.println("Points updated.");
    }

}

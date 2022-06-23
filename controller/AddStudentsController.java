package tracker.controller;

import tracker.utils.Input;
import tracker.utils.StudentParser;
import tracker.repository.StudentRepository;
import tracker.entity.Student;

import static tracker.utils.Commands.isBack;

public class AddStudentsController {
    private static final StudentRepository STUDENT_REPOSITORY = StudentRepository.getInstance();
    public static void run() {

        System.out.println("Enter student credentials or 'back' to return:");
        boolean back = false;
        while (!back) {
            String input = Input.getString();
            if (isBack(input))
                back = true;
            else
                addStudent(input);
        }
        int size = STUDENT_REPOSITORY.getSize();
        System.out.printf("Total %d students have been added.\n", size);
    }

    private static void addStudent(String input) {
        Student student = StudentParser.getStudent(input);
        if (student == null)
            return;
        if (STUDENT_REPOSITORY.addStudent(student))
            System.out.println("The student has been added.");
        else
            System.out.println("This email is already taken.");

    }
}

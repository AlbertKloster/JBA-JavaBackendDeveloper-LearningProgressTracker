package tracker.controller;

import tracker.utils.Input;
import tracker.repository.StudentRepository;

import static tracker.utils.Commands.*;

public class ListController {
    private static final StudentRepository STUDENT_REPOSITORY = StudentRepository.getInstance();
    public static void run() {
        printList();

        boolean back = false;
        while (!back) {
            String input = Input.getString();

            if (isBack(input))
                back = true;

            else if (isAddPoints(input))
                AddPointsController.run();

            else if (isFind(input))
                FindController.run();

            else if (isStatistics(input))
                StatisticsController.run();

            else if (isNotify(input))
                NotifyController.run();
        }

    }

    private static void printList() {
        if (STUDENT_REPOSITORY.getSize() == 0) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("Students:");
        STUDENT_REPOSITORY.getAllId().forEach(System.out::println);
    }

    private static boolean isBack(String input) {
        return input.equals("back");
    }

}

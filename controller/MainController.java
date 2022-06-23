package tracker.controller;

import tracker.utils.Input;

import static tracker.utils.Commands.*;

public class MainController {
    public static void run() {

        System.out.println("Learning Progress Tracker");
        boolean exit = false;
        while (!exit) {
            String input = Input.getString();

            if (isExit(input))
                exit = true;

            else if (isBack(input))
                System.out.println("Enter 'exit' to exit the program.");

            else if (input.isBlank())
                System.out.println("No input.");

            else if (isAddStudents(input))
                AddStudentsController.run();

            else if (isList(input))
                ListController.run();

            else if (isAddPoints(input))
                AddPointsController.run();

            else if (isFind(input))
                FindController.run();

            else if (isStatistics(input))
                StatisticsController.run();

            else if (isNotify(input))
                NotifyController.run();

            else
                System.out.println("Error: unknown command!");
        }
        System.out.println("Bye!");
    }

 }

package tracker.utils;

import tracker.entity.Student;

public class StudentParser {
    public static Student getStudent(String input) {
        String[] items = input.split("\\s+");
        if (isIncorrectCredentials(items)) {
            System.out.println("Incorrect credentials.");
            return null;
        }
        String firstName = getFirstName(items);
        if (isIncorrectFirstName(firstName)) {
            System.out.println("Incorrect first name.");
            return null;
        }
        String lastName = getLastName(items);
        if (isIncorrectLastName(lastName)) {
            System.out.println("Incorrect last name.");
            return null;
        }

        String email = getEmail(items);
        if (isIncorrectEmail(email)) {
            System.out.println("Incorrect email.");
            return null;
        }

        return new Student(firstName, lastName, email);
    }

    private static boolean isIncorrectCredentials(String[] items) {
        return items.length < 3;
    }

    private static String getFirstName(String[] items) {
        return items[0];
    }

    private static String getLastName(String[] items) {
        StringBuilder builder = new StringBuilder(items[1]);
        for (int i = 2; i < items.length - 1; i++)
            builder.append(" ").append(items[i]);
        return builder.toString();
    }

    private static String getEmail(String[] items) {
        return items[items.length - 1];
    }

    private static boolean isIncorrectFirstName(String firstName) {
        return !firstName.matches("[A-Za-z]([-']?[A-Za-z])+");
    }

    private static boolean isIncorrectLastName(String lastName) {
        return !lastName.matches("([A-Za-z]([-']?[A-Za-z])+\\s?)+");
    }

    private static boolean isIncorrectEmail(String email) {
        return !email.matches("[A-Za-z\\d.]+@[A-Za-z\\d]+\\.[A-Za-z\\d]+");
    }

}

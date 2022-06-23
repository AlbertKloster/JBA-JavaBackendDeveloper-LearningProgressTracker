package tracker.utils;

import java.util.Scanner;

public class Input {
    private static final Scanner SC = new Scanner(System.in);

    public static String getString() {
        return SC.nextLine();
    }
}

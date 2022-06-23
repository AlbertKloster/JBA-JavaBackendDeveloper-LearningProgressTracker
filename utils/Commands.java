package tracker.utils;

public class Commands {
    public static boolean isExit(String input) {
        return input.equals("exit");
    }

    public static boolean isAddStudents(String input) {
        return input.equals("add students");
    }

    public static boolean isBack(String input) {
        return input.equals("back");
    }

    public static boolean isList(String input) {
        return input.equals("list");
    }

    public static boolean isAddPoints(String input) {
        return input.matches("add points");
    }

    public static boolean isFind(String input) {
        return input.matches("find");
    }

    public static String getId(String[] inputArray) {
        return inputArray[0];
    }

    public static long getJavaPoints(String[] inputArray) {
        return Integer.parseInt(inputArray[1]);
    }

    public static long getDsaPoints(String[] inputArray) {
        return Integer.parseInt(inputArray[2]);
    }

    public static long getDatabasesPoints(String[] inputArray) {
        return Integer.parseInt(inputArray[3]);
    }

    public static long getSpringPoints(String[] inputArray) {
        return Integer.parseInt(inputArray[4]);
    }

    public static boolean isStatistics(String input) {
        return input.equals("statistics");
    }

    public static boolean isJava(String input) {
        return input.equals("java");
    }
    public static boolean isDsa(String input) {
        return input.equals("dsa");
    }
    public static boolean isDatabases(String input) {
        return input.equals("databases");
    }
    public static boolean isSpring(String input) {
        return input.equals("spring");
    }
    public static boolean isNotify(String input) {
        return input.equals("notify");
    }

}

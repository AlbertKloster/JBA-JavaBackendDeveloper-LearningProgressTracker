package tracker.utils;

public enum Course {
    JAVA("Java", 600),
    DSA("DSA", 400),
    DATABASES("Databases", 480),
    SPRING("Spring", 550);

    public final String name;
    public final long points;

    Course(String name, long points) {
        this.name = name;
        this.points = points;
    }

}

package tracker.entity;

public class Submission {

    private long id;
    private long javaPoints;
    private long dsaPoints;
    private long databasesPoints;
    private long springPoints;
    private String studentId;

    public Submission(long javaPoints, long dsaPoints, long databasesPoints, long springPoints, String studentId) {
        this.javaPoints = javaPoints;
        this.dsaPoints = dsaPoints;
        this.databasesPoints = databasesPoints;
        this.springPoints = springPoints;
        this.studentId = studentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getJavaPoints() {
        return javaPoints;
    }

    public void setJavaPoints(long javaPoints) {
        this.javaPoints = javaPoints;
    }

    public long getDsaPoints() {
        return dsaPoints;
    }

    public void setDsaPoints(long dsaPoints) {
        this.dsaPoints = dsaPoints;
    }

    public long getDatabasesPoints() {
        return databasesPoints;
    }

    public void setDatabasesPoints(long databasesPoints) {
        this.databasesPoints = databasesPoints;
    }

    public long getSpringPoints() {
        return springPoints;
    }

    public void setSpringPoints(long springPoints) {
        this.springPoints = springPoints;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}

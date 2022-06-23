package tracker.entity;

public class CompletionNotification {
    private String course;
    private Student student;

    public CompletionNotification(String course, Student student) {
        this.course = course;
        this.student = student;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}

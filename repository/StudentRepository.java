package tracker.repository;

import tracker.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentRepository {

    private static StudentRepository instance;
    private static final List<Student> students = new ArrayList<>();
    private static long id = 10000L;

    private StudentRepository() {}

    public static StudentRepository getInstance() {
        return instance == null ? new StudentRepository() : instance;
    }

    public boolean addStudent(Student student) {
        if (getAllEmails().contains(student.getEmail()))
            return false;
        student.setId(String.valueOf(id++));
        students.add(student);
        return true;
    }
    
    public int getSize() {
        return students.size();
    }

    private List<String> getAllEmails() {
        return students.stream().map(Student::getEmail).collect(Collectors.toList());
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public List<String> getAllId() {
        return students.stream().map(Student::getId).collect(Collectors.toList());
    }

    public Student findStudentById(String id) {
        return students.stream().filter(student -> student.getId().equals(id)).findAny().orElse(null);
    }

}

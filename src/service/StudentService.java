package service;

import model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {
    private final List<Student> students = new ArrayList<>();

    public boolean addStudent(Student student) {
        if (isRollExists(student.getRollNo())) {
            return false;
        }
        students.add(student);
        return true;
    }

    // New Search Logic
    public List<Student> searchStudents(String query) {
        if (query == null || query.isEmpty()) {
            return getStudents();
        }
        return students.stream()
                .filter(s -> s.getName().toLowerCase().contains(query.toLowerCase()) || 
                             s.getRollNo().contains(query))
                .collect(Collectors.toList());
    }

    public boolean removeStudentByRoll(String rollNo) {
        return students.removeIf(student -> student.getRollNo().equals(rollNo));
    }

    public void removeStudent(int index) {
        if (index >= 0 && index < students.size()) {
            students.remove(index);
        }
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    private boolean isRollExists(String rollNo) {
        return students.stream().anyMatch(s -> s.getRollNo().equals(rollNo));
    }
}
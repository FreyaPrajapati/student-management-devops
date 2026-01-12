package service;

import model.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final List<Student> students = new ArrayList<>();

    // Add student only if roll number is unique
    public boolean addStudent(Student student) {
        if (isRollExists(student.getRollNo())) {
            return false;
        }
        students.add(student);
        return true;
    }

    // Remove student by roll number (business logic)
    public boolean removeStudentByRoll(String rollNo) {
        return students.removeIf(
                student -> student.getRollNo().equals(rollNo)
        );
    }

    // Keep old method for compatibility
    public void removeStudent(int index) {
        if (index >= 0 && index < students.size()) {
            students.remove(index);
        }
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students); // defensive copy
    }

    private boolean isRollExists(String rollNo) {
        for (Student student : students) {
            if (student.getRollNo().equals(rollNo)) {
                return true;
            }
        }
        return false;
    }
}

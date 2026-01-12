package model;

public class Student {
    private String rollNo;
    private String name;
    private String department;
    private String status;
    private String email; // New Field

    public Student(String rollNo, String name, String department, String status, String email) {
        this.rollNo = rollNo;
        this.name = name;
        this.department = department;
        this.status = status;
        this.email = email;
    }

    public String getRollNo() { return rollNo; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getStatus() { return status; }
    public String getEmail() { return email; } // New Getter
}
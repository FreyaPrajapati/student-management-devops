package ui;

import model.Student;
import service.StudentService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class StudentUI extends JFrame {
    private JTextField rollField, nameField, emailField, searchField;
    private JComboBox<String> deptBox, statusBox;
    private JTable table;
    private DefaultTableModel tableModel;
    private final StudentService service = new StudentService();

    private final Color primary = new Color(33, 150, 243);
    private final Color background = new Color(245, 247, 250);
    private final Color danger = new Color(244, 67, 54);

    public StudentUI() {
        setTitle("Student Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Header with Search Bar
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(primary);
        header.setPreferredSize(new Dimension(1000, 70));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("Student Management System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        searchField = new JTextField(15);
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);

        header.add(title, BorderLayout.WEST);
        header.add(searchPanel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 12, 12));
        inputPanel.setBackground(background);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        rollField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        deptBox = new JComboBox<>(new String[]{"Computer Science", "IT", "Electronics", "Mechanical"});
        statusBox = new JComboBox<>(new String[]{"Active", "Inactive"});

        JButton addBtn = new JButton("Add Student");
        JButton deleteBtn = new JButton("Delete Selected");
        addBtn.setBackground(primary); addBtn.setForeground(Color.WHITE);
        deleteBtn.setBackground(danger); deleteBtn.setForeground(Color.WHITE);

        inputPanel.add(new JLabel("Roll No:")); inputPanel.add(rollField);
        inputPanel.add(new JLabel("Name:")); inputPanel.add(nameField);
        inputPanel.add(new JLabel("Email:")); inputPanel.add(emailField);
        inputPanel.add(new JLabel("Department:")); inputPanel.add(deptBox);
        inputPanel.add(new JLabel("Status:")); inputPanel.add(statusBox);
        inputPanel.add(addBtn); inputPanel.add(deleteBtn);

        add(inputPanel, BorderLayout.WEST);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Roll No", "Name", "Email", "Dept", "Status"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Events
        addBtn.addActionListener(e -> addStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
        
        // Real-time Search Event
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                refreshTable(service.searchStudents(searchField.getText()));
            }
        });
    }

    private void addStudent() {
        Student s = new Student(rollField.getText(), nameField.getText(), 
                    deptBox.getSelectedItem().toString(), statusBox.getSelectedItem().toString(), 
                    emailField.getText());
        if(service.addStudent(s)) {
            refreshTable(service.getStudents());
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Roll No already exists!");
        }
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String roll = tableModel.getValueAt(row, 0).toString();
            service.removeStudentByRoll(roll);
            refreshTable(service.getStudents());
        }
    }

    private void refreshTable(List<Student> list) {
        tableModel.setRowCount(0);
        for (Student s : list) {
            tableModel.addRow(new Object[]{s.getRollNo(), s.getName(), s.getEmail(), s.getDepartment(), s.getStatus()});
        }
    }

    private void clearFields() {
        rollField.setText(""); nameField.setText(""); emailField.setText("");
    }

    public void showUI() { setVisible(true); }
}
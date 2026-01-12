package ui;

import model.Student;
import service.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StudentUI extends JFrame {

    private JTextField rollField, nameField;
    private JComboBox<String> deptBox, statusBox;
    private JTable table;
    private DefaultTableModel tableModel;
    private final StudentService service = new StudentService();

    // Color theme
    private final Color primary = new Color(33, 150, 243);
    private final Color background = new Color(245, 247, 250);
    private final Color danger = new Color(244, 67, 54);

    public StudentUI() {
        setTitle("Student Management System");
        setSize(900, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel();
        header.setBackground(primary);
        header.setPreferredSize(new Dimension(900, 60));

        JLabel title = new JLabel("Student Management System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.add(title);

        add(header, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 12, 12));
        inputPanel.setBackground(background);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        rollField = new JTextField();
        nameField = new JTextField();

        deptBox = new JComboBox<>(new String[]{
                "Computer Science", "IT", "Electronics", "Mechanical"
        });

        statusBox = new JComboBox<>(new String[]{"Active", "Inactive"});

        JButton addBtn = new JButton("Add Student");
        JButton deleteBtn = new JButton("Delete Selected");

        addBtn.setBackground(primary);
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);

        deleteBtn.setBackground(danger);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);

        inputPanel.add(new JLabel("Roll No:"));
        inputPanel.add(rollField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Department:"));
        inputPanel.add(deptBox);
        inputPanel.add(new JLabel("Status:"));
        inputPanel.add(statusBox);
        inputPanel.add(addBtn);
        inputPanel.add(deleteBtn);

        add(inputPanel, BorderLayout.WEST);

        // Table
        tableModel = new DefaultTableModel(
                new String[]{"Roll No", "Name", "Department", "Status"}, 0
        );
        table = new JTable(tableModel);
        table.setRowHeight(28);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Actions
        addBtn.addActionListener(e -> addStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
    }

    private void addStudent() {
        String roll = rollField.getText().trim();
        String name = nameField.getText().trim();

        if (roll.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter all fields");
            return;
        }

        Student student = new Student(
                roll,
                name,
                deptBox.getSelectedItem().toString(),
                statusBox.getSelectedItem().toString()
        );

        service.addStudent(student);
        tableModel.addRow(new Object[]{
                student.getRollNo(),
                student.getName(),
                student.getDepartment(),
                student.getStatus()
        });

        rollField.setText("");
        nameField.setText("");
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            service.removeStudent(row);
            tableModel.removeRow(row);
        }
    }

    public void showUI() {
        setVisible(true);
    }
}

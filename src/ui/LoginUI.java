package ui;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    // Color theme
    private final Color primary = new Color(33, 150, 243);   // Blue
    private final Color background = new Color(245, 247, 250); // Light gray

    public LoginUI() {
        setTitle("Login");
        setSize(400, 260);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // ===== Header =====
        JPanel header = new JPanel();
        header.setBackground(primary);
        header.setPreferredSize(new Dimension(400, 50));

        JLabel title = new JLabel("Student Management Login");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.add(title);

        add(header, BorderLayout.NORTH);

        // ===== Main Panel =====
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(background);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(primary);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginBtn);

        add(panel, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> authenticate());
    }

    private void authenticate() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Hardcoded credentials (acceptable for micro-project)
        if (username.equals("admin") && password.equals("admin123")) {
            dispose(); // close login window
            new DashboardUI(username).setVisible(true); // OPEN DASHBOARD
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

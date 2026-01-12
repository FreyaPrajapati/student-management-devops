package ui;

import javax.swing.*;
import java.awt.*;

public class DashboardUI extends JFrame {

    // Color theme
    private final Color primary = new Color(33, 150, 243);   // Blue
    private final Color background = new Color(245, 247, 250); // Light gray
    private final Color danger = new Color(244, 67, 54);     // Red

    public DashboardUI(String username) {
        setTitle("Dashboard");
        setSize(450, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI(username);
    }

    private void initUI(String username) {
        setLayout(new BorderLayout());

        // ===== Header =====
        JPanel header = new JPanel();
        header.setBackground(primary);
        header.setPreferredSize(new Dimension(450, 60));

        JLabel title = new JLabel("Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(title);

        add(header, BorderLayout.NORTH);

        // ===== Main Panel =====
        JPanel panel = new JPanel(new GridLayout(3, 1, 20, 20));
        panel.setBackground(background);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + username,
                SwingConstants.CENTER
        );
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JButton manageBtn = new JButton("Manage Students");
        JButton logoutBtn = new JButton("Logout");

        // Button styling
        manageBtn.setBackground(primary);
        manageBtn.setForeground(Color.WHITE);
        manageBtn.setFocusPainted(false);

        logoutBtn.setBackground(danger);
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);

        panel.add(welcomeLabel);
        panel.add(manageBtn);
        panel.add(logoutBtn);

        add(panel, BorderLayout.CENTER);

        // ===== Actions =====
        manageBtn.addActionListener(e -> {
            dispose();
            new StudentUI().showUI();
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginUI().setVisible(true);
        });
    }
}

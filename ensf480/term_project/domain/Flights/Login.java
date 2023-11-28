package ensf480.term_project.domain.Flights;

import javax.swing.*;
import java.awt.*;

public class Login extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Add components for the admin management page
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel panel = new JPanel();
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        // Set layout manager
        panel.setLayout(new GridLayout(3, 2));

        // Add components to the panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(loginButton);

        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

    }
}
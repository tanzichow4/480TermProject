package ensf480.term_project.domain.Flights;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Login extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;

    private void handleLogin() {
            // Check if the entered username and password are correct (replace with your authentication logic)
            String enteredUsername = usernameField.getText();
            char[] enteredPasswordChars = passwordField.getPassword();
            String enteredPassword = new String(enteredPasswordChars);
    
            if (isValidLogin(enteredUsername, enteredPassword)) {
                JOptionPane.showMessageDialog(null, "Login successful!");
                // Add logic to open the main application or switch to another page
                usernameField.setText("");
                passwordField.setText("");
            } 
            else{
                JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
            }
        }
    
        private boolean isValidLogin(String username, String password) {
            // Replace this with your actual authentication logic
            // For example, you might check against a database of user credentials
            return username.equals("admin") && password.equals("admin123");
        }

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

        //Add login button event
        loginButton.addActionListener((ActionEvent e) -> {
            handleLogin();
        });
        
    }
}
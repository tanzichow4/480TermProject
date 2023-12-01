package ensf480.term_project.domain.Flights;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ensf480.term_project.domain.Users.*;

public class MainGUI {
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Airline Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 700);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create landing page
        JPanel landingPanel = createLandingPanel();
        cardPanel.add(landingPanel, "landing");

        // Create browse flights page
        BrowseFlights browseFlights = new BrowseFlights(); // Use the BrowseFlights component
        cardPanel.add(browseFlights, "browseFlights");

        // Create Login Page
        Login login = new Login();
        cardPanel.add(login, "login");

        // Create Admin Page
        AdminManage adminManage = new AdminManage();
        cardPanel.add(adminManage, "adminManage");

        // Create Browse Passenger Page
        BrowsePassengerFlights browsePassengerFlights = new BrowsePassengerFlights();
        cardPanel.add(browsePassengerFlights, "browsePassengerFlights");

        // Create Manage Flight Page
        FlightManager flightManager = new FlightManager(cardLayout, cardPanel);
        cardPanel.add(flightManager, "flightManager");

        // Create Manage Aircraft Page
        AircraftManager aircraftManager = new AircraftManager(cardLayout, cardPanel);
        cardPanel.add(aircraftManager, "aircraftManager");

        // Create Airline Agent Browse Flight Page
        AirlineAgentBrowseFlights airlineAgentBrowseFlights = new AirlineAgentBrowseFlights();
        cardPanel.add(airlineAgentBrowseFlights, "airlineAgentBrowseFlights");

        // Create Airline Agent Portal Page
        AirlineAgentPortal airlineAgentPortal = new AirlineAgentPortal();
        cardPanel.add(airlineAgentPortal, "airlineAgentPortal");

        // frame.add(createTopBar(), BorderLayout.NORTH);
        frame.add(cardPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel createLandingPanel() {
        JPanel landingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // ImageIcon imageIcon = new
                // ImageIcon("/Users/ibrahimwani/eclipse-workspace/AirlineProject/src/images/airplane-image.png");
                ImageIcon imageIcon = new ImageIcon(
                        "/Users/ibrahimwani/eclipse-workspace/480TermProject/images/airplane-image.png");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        landingPanel.setLayout(new BoxLayout(landingPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setForeground(Color.WHITE); // Set text color to white

        JButton continueAsGuestButton = new JButton("Register");
        continueAsGuestButton.setFont(new Font("Arial", Font.PLAIN, 20));
        continueAsGuestButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueAsGuestButton.setBackground(new Color(0, 0, 102));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 20));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        continueAsGuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Load the background image
                ImageIcon backgroundImageIcon = new ImageIcon(
                        "/Users/ibrahimwani/eclipse-workspace/480TermProject/images/airplane-image.png");
                Image backgroundImage = backgroundImageIcon.getImage();

                // Create a panel to hold the components with a custom paintComponent method
                JPanel signUpPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        // Draw the background image
                        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    }
                };

                // Use a GridBagLayout for more flexibility in component placement
                signUpPanel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(5, 5, 5, 5);

                // Set foreground color for labels
                Color whiteColor = Color.WHITE;

                JLabel emailLabel = new JLabel("Email:");
                emailLabel.setForeground(whiteColor);
                signUpPanel.add(emailLabel, gbc);

                gbc.gridy++;
                JTextField emailField = new JTextField(20);
                signUpPanel.add(emailField, gbc);

                gbc.gridy++;
                JLabel usernameLabel = new JLabel("Username:");
                usernameLabel.setForeground(whiteColor);
                signUpPanel.add(usernameLabel, gbc);

                gbc.gridy++;
                JTextField usernameField = new JTextField(20);
                signUpPanel.add(usernameField, gbc);

                gbc.gridy++;
                JLabel passwordLabel = new JLabel("Password:");
                passwordLabel.setForeground(whiteColor);
                signUpPanel.add(passwordLabel, gbc);

                gbc.gridy++;
                JPasswordField passwordField = new JPasswordField(20);
                signUpPanel.add(passwordField, gbc);

                gbc.gridy++;
                JButton signUpButton = new JButton("Sign Up");
                signUpButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Add logic for the "Sign Up" button click
                        String email = emailField.getText();
                        String username = usernameField.getText();
                        char[] passwordChars = passwordField.getPassword();
                        String password = new String(passwordChars);

                        // Implement your sign-up logic here using email, username, and password
                        Login.setLoggedInCustomer(new Customer(username, password, email, true));

                        // Assuming successful signup, navigate to the next screen
                        cardLayout.show(cardPanel, "browseFlights");
                    }
                });
                signUpPanel.add(signUpButton, gbc);

                gbc.gridy++;
                // Add the "Back" button
                JButton backButton = new JButton("Back");
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Navigate back to the landing page
                        cardLayout.show(cardPanel, "landing");
                    }
                });
                signUpPanel.add(backButton, gbc);

                // Add the sign-up screen to your cardPanel
                cardPanel.add(signUpPanel, "signUp");

                // Show the sign-up screen
                cardLayout.show(cardPanel, "signUp");
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic for login button click if needed
                cardLayout.show(cardPanel, "login");
            }
        });

        landingPanel.add(Box.createVerticalGlue());
        landingPanel.add(welcomeLabel);
        landingPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        landingPanel.add(continueAsGuestButton);
        landingPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some space between buttons
        landingPanel.add(loginButton);
        landingPanel.add(Box.createVerticalGlue());

        return landingPanel;
    }

}
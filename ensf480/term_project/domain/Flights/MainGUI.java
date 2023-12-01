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

    private static JPanel createTopBar() {
        JPanel topBar = new JPanel();
        topBar.setLayout(new BorderLayout());

        // Menu bar on the left
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Manage Purchases");
        JMenuItem menuItem = new JMenuItem("Item 1");
        menu.add(menuItem);
        menuBar.add(menu);

        // Title in the center
        JLabel titleLabel = new JLabel("Airline");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        // Login button on the right
        JButton loginButton = new JButton("Logout");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle login button click
            }
        });

        topBar.add(menuBar, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);
        topBar.add(loginButton, BorderLayout.EAST);

        return topBar;
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
                // Create the sign-up screen dynamically
                JPanel signUpPanel = new JPanel(new GridLayout(4, 2));

                signUpPanel.add(new JLabel("Email:"));
                JTextField emailField = new JTextField();
                signUpPanel.add(emailField);

                signUpPanel.add(new JLabel("Username:"));
                JTextField usernameField = new JTextField();
                signUpPanel.add(usernameField);

                signUpPanel.add(new JLabel("Password:"));
                JPasswordField passwordField = new JPasswordField();
                signUpPanel.add(passwordField);

                JButton signUpButton = new JButton("Sign Up");
                signUpButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Add logic for the "Sign Up" button click
                        String email = emailField.getText();
                        String username = usernameField.getText();
                        char[] passwordChars = passwordField.getPassword();
                        String password = new String(passwordChars); // Convert char array to String

                        // Implement your sign-up logic here using email, username, and password
                        Login.setLoggedInCustomer(new Customer(username, password, email, true));
                        // For now, just print the entered values
                        // System.out.println("Email: " + email);
                        // System.out.println("Username: " + username);
                        // System.out.println("Password: " + password);

                        // Assuming successful signup, navigate to the next screen
                        cardLayout.show(cardPanel, "browseFlights");
                    }
                });

                signUpPanel.add(new JLabel()); // Empty label for layout purposes
                signUpPanel.add(signUpButton);

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
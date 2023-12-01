package ensf480.term_project.domain.Flights;

import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;

import ensf480.term_project.domain.Users.*;
import ensf480.term_project.domain.Boundaries.*;
import java.awt.event.ActionListener;

public class Login extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private static RegisteredUser loggedInUser;
    private static Customer loggedInCustomer;
    private static SystemAdmin loggedInAdmin;
    private static AirlineAgent loggedInAirlineAgent;
    private static FlightAttendant loggedInFlightAttendant;
    private static Image backgroundImage;

    private static final String BACKGROUND_IMAGE_PATH = "/Users/ibrahimwani/eclipse-workspace/480TermProject/images/airplane-image.png";

    private void handleLogin() {
        // Check if the entered username and password are correct (replace with your
        // authentication logic)
        String enteredUsername = usernameField.getText();
        char[] enteredPasswordChars = passwordField.getPassword();
        String enteredPassword = new String(enteredPasswordChars);

        int redirectValue = isValidLogin(enteredUsername, enteredPassword);

        if (redirectValue == 1) {
            JOptionPane.showMessageDialog(null, "Login as admin successful!");
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "adminManage");
            usernameField.setText("");
            passwordField.setText("");
        } else if (redirectValue == 0) {
            JOptionPane.showMessageDialog(null, "Login as User Successful");
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "browseFlights");
            usernameField.setText("");
            passwordField.setText("");
        } else if (redirectValue == 2) {
            JOptionPane.showMessageDialog(null, "Login as Flight Attendant Successful");
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "browsePassengerFlights");
            usernameField.setText("");
            passwordField.setText("");
        } else if (redirectValue == 3) {
            JOptionPane.showMessageDialog(null, "Login as Airline Agent Successful");
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "airlineAgentPortal");
            usernameField.setText("");
            passwordField.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "landing");
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    private int isValidLogin(String username, String password) {
        List<RegisteredUser> userList = PopulateFromDB.getRegisteredUserList();
        List<Customer> customerList = PopulateFromDB.getCustomers();
        List<SystemAdmin> adminList = PopulateFromDB.createSystemAdmins(userList);
        List<AirlineAgent> agents = PopulateFromDB.getAirlineAgentList();
        List<FlightAttendant> attendants = PopulateFromDB.createFlightAttendants(userList);

        for (RegisteredUser user : userList) {

            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                // loggedInUser = user;
                if (user.getUserType() == 0) {
                    for (Customer customer : customerList) {
                        if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                            loggedInCustomer = customer;
                            return customer.getUserType();

                        }
                    }
                } else if (user.getUserType() == 1) {
                    for (SystemAdmin admin : adminList) {
                        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                            loggedInAdmin = admin;

                            return admin.getUserType();

                        }
                    }
                } else if (user.getUserType() == 3) {
                    for (AirlineAgent agent : agents) {
                        if (agent.getUsername().equals(username) && agent.getPassword().equals(password)) {
                            loggedInAirlineAgent = agent;
                            System.out.println("logged in admin: " + loggedInAirlineAgent.getUsername());
                            return agent.getUserType();

                        }
                    }
                } else if (user.getUserType() == 2) {
                    for (FlightAttendant attendant : attendants) {
                        if (attendant.getUsername().equals(username) && attendant.getPassword().equals(password)) {
                            loggedInFlightAttendant = attendant;
                            return attendant.getUserType();

                        }
                    }
                }

            }

        }
        return 4;
    }

    public Login() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Load the background image
        ImageIcon backgroundImageIcon = new ImageIcon(BACKGROUND_IMAGE_PATH);
        Image backgroundImage = backgroundImageIcon.getImage();

        // Create a panel to hold the components
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back"); // Add Back button

        // Set foreground color for labels
        Color whiteColor = Color.WHITE;
        usernameLabel.setForeground(whiteColor);
        passwordLabel.setForeground(whiteColor);

        // Set layout manager for the main panel
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to the panel with GridBagLayout
        panel.add(usernameLabel, gbc);
        gbc.gridy++;
        panel.add(usernameField, gbc);
        gbc.gridy++;
        panel.add(passwordLabel, gbc);
        gbc.gridy++;
        panel.add(passwordField, gbc);
        gbc.gridy++;
        panel.add(loginButton, gbc);
        gbc.gridy++;
        panel.add(backButton, gbc); // Add Back button

        add(panel, BorderLayout.CENTER);

        // Add login button event
        loginButton.addActionListener((ActionEvent e) -> {
            handleLogin();
        });

        // Add Back button event
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "landing");
            }
        });
    }

    public static RegisteredUser getLoggedInUser() {
        return loggedInUser;
    }

    public static SystemAdmin getLoggedInAdmin() {
        return loggedInAdmin;
    }

    public static Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    public static AirlineAgent getLoggedInAirlineAgent() {
        return loggedInAirlineAgent;
    }

    public static FlightAttendant getLoggedInFlightAttendant() {
        return loggedInFlightAttendant;
    }

    public static void setLoggedInCustomer(Customer customer) {
        loggedInCustomer = customer;
    }
}

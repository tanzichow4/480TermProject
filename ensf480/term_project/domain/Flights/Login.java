package ensf480.term_project.domain.Flights;

import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;

import ensf480.term_project.domain.Users.*;
import ensf480.term_project.domain.Boundaries.*;

public class Login extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private static RegisteredUser loggedInUser;
    private static Customer loggedInCustomer;
    private static SystemAdmin loggedInAdmin;
    private static AirlineAgent loggedInAirlineAgent;
    private static FlightAttendant loggedInFlightAttendant;


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
            } 
            else if(redirectValue == 0){
                JOptionPane.showMessageDialog(null, "Login as User Successful");
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "browseFlights");
                usernameField.setText("");
                passwordField.setText("");
            }
            else if(redirectValue == 2){
                JOptionPane.showMessageDialog(null, "Login as Flight Attendant Successful");
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "browseFlights");
                usernameField.setText("");
                passwordField.setText("");
            }
            else if(redirectValue == 3){
                JOptionPane.showMessageDialog(null, "Login as Airline Agent Successful");
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "airlineAgentBrowseFlights");
                usernameField.setText("");
                passwordField.setText("");
            }
            else{
                JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                usernameField.setText("");
                passwordField.setText("");
            }
        }

    private int isValidLogin(String username, String password) {
        List<RegisteredUser> userList = PopulateFromDB.getRegisteredUserList();
        List<Customer> customerList = PopulateFromDB.getCustomers();
        List<SystemAdmin> adminList = PopulateFromDB.createSystemAdmins(userList);
        List<AirlineAgent> agents = PopulateFromDB.createAirlineAgents(userList);
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

        // Add login button event
        loginButton.addActionListener((ActionEvent e) -> {
            handleLogin();
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
}
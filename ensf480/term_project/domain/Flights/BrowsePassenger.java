package ensf480.term_project.domain.Flights;

import javax.swing.*;

import ensf480.term_project.domain.Boundaries.DatabaseManager;
import ensf480.term_project.domain.Boundaries.PopulateFromDB;
import ensf480.term_project.domain.Payments.Payment;
import ensf480.term_project.domain.Users.RegisteredUser;

import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class BrowsePassenger extends JPanel {
/*    private List<RegisteredUser> registeredUsers;
    private List<Payment> payments;
    private List<Seat> seats;
    private List<String> passengerNames;
    private List<String> seatNumbers;

    public BrowsePassenger() {
        DatabaseManager.connect("BILLING");
        Connection billingConnection = DatabaseManager.getConnection("BILLING");
        DatabaseManager.connect("AIRLINE");
        Connection airlineConnection = DatabaseManager.getConnection("AIRLINE");

        registeredUsers = PopulateFromDB.setRegisteredUsers();
        



        setLayout(new BorderLayout());

        // Create a panel to hold the list of passengers
        JPanel passengerListPanel = createPassengerListPanel();
        add(passengerListPanel, BorderLayout.CENTER);
    }

    private JPanel createPassengerListPanel() {
        JPanel passengerListPanel = new JPanel();
        passengerListPanel.setLayout(new BoxLayout(passengerListPanel, BoxLayout.Y_AXIS));
        passengerListPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Passenger List"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Use the lists of passengerNames and seat numbers
        for (int i = 0; i < passengerNames.size(); i++) {
            String name = passengerNames.get(i);
            String seatNumber = seatNumbers.get(i);

            String passengerInfo = "Name: " + name + ", Seat: " + seatNumber;
            JPanel passengerPanel = createPassengerPanel(passengerInfo);
            passengerListPanel.add(passengerPanel);

            // Add some spacing between passenger panels
            passengerListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        return passengerListPanel;
    }

    private JPanel createPassengerPanel(String passengerInfo) {
        JPanel passengerPanel = new JPanel();
        passengerPanel.setLayout(new BoxLayout(passengerPanel, BoxLayout.X_AXIS));
        passengerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Display relevant information about the passenger
        JLabel passengerLabel = new JLabel(passengerInfo);

        passengerPanel.add(passengerLabel);

        return passengerPanel;
    }

     */
}

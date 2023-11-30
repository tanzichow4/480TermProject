package ensf480.term_project.domain.Flights;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BrowsePassenger extends JPanel {
    private List<String> passengers;

    public BrowsePassenger() {
        // Fill the list with some random passenger information
        passengers = generateRandomPassengerList();

        setLayout(new BorderLayout());

        // Create a panel to hold the list of passengers
        JPanel passengerListPanel = createPassengerListPanel();
        add(passengerListPanel, BorderLayout.CENTER);
    }

    private List<String> generateRandomPassengerList() {
        List<String> passengerList = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i <= 10; i++) {
            String name = "Passenger " + i;
            int age = 20 + random.nextInt(30); // Random age between 20 and 49
            String seatNumber = "Seat " + (i * 2); // Assuming even seat numbers

            String passengerInfo = "Name: " + name + ", Age: " + age + ", Seat: " + seatNumber;
            passengerList.add(passengerInfo);
        }

        return passengerList;
    }

    private JPanel createPassengerListPanel() {
        JPanel passengerListPanel = new JPanel();
        passengerListPanel.setLayout(new BoxLayout(passengerListPanel, BoxLayout.Y_AXIS));
        passengerListPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Passenger List"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Use the list of passengers
        for (String passengerInfo : passengers) {
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
}

package ensf480.term_project.domain.Flights;

import ensf480.term_project.domain.Boundaries.DatabaseManager;
import ensf480.term_project.domain.Boundaries.PopulateFromDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class BrowsePassengerFlights extends JPanel {
    private List<Flight> flightsData;
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    private JTextField fromField;
    private JTextField destField;
    private JTextField departureDateField;
    private JTextField flightNumberField;

    private JPanel flightsListPanel;

    public BrowsePassengerFlights() {
        setLayout(new BorderLayout());
        DatabaseManager.connect("AIRLINE");
        Connection airlineConnection = DatabaseManager.getConnection("AIRLINE");

        // Retrieve flight data from PopulateFromDB
        flightsData = PopulateFromDB.setFlights();
        System.out.println("Flights Data: " + flightsData);

        // Create top bar
        JPanel topBar = createTopBar();
        add(topBar, BorderLayout.NORTH);

        // Filter box on the left
        JPanel filterBoxPanel = createFilterBoxPanel();
        add(filterBoxPanel, BorderLayout.WEST);

        // List of available flights on the right
        flightsListPanel = createFlightsListPanel();
        add(flightsListPanel, BorderLayout.CENTER);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel();
        topBar.setLayout(new BorderLayout());

        // Title in the center
        JLabel titleLabel = new JLabel("Airline");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        // Logout button on the right
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout button click
            }
        });

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();

        // Add components to the top bar
        topBar.add(titleLabel, BorderLayout.CENTER);
        topBar.add(logoutButton, BorderLayout.EAST);
        topBar.add(buttonPanel, BorderLayout.WEST);

        return topBar;
    }

    private JPanel createFilterBoxPanel() {
        JPanel filterBoxPanel = new JPanel();
        filterBoxPanel.setLayout(new BoxLayout(filterBoxPanel, BoxLayout.Y_AXIS));
        filterBoxPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Filters"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Initialize filter text fields
        fromField = new JTextField(10);
        destField = new JTextField(10);
        departureDateField = new JTextField(10);
        flightNumberField = new JTextField(10);

        // From field
        JPanel fromPanel = createFilterRow("From:", fromField);

        // Destination field
        JPanel destPanel = createFilterRow("Destination:", destField);

        // Departure Date field
        JPanel departureDatePanel = createFilterRow("Departure Date:", departureDateField);
        JPanel flightNumberPanel = createFilterRow("Flight Number:", flightNumberField);

        filterBoxPanel.add(fromPanel);
        filterBoxPanel.add(destPanel);
        filterBoxPanel.add(departureDatePanel);
        filterBoxPanel.add(flightNumberPanel);

        // Add some spacing before the button
        filterBoxPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Search Flights button
        JButton searchButton = new JButton("Search Flights");
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call a method to filter flights based on user input
                filterFlights();
            }
        });

        filterBoxPanel.add(searchButton);

        // Set preferred and maximum size to control the height
        filterBoxPanel.setPreferredSize(new Dimension(280, 260));
        filterBoxPanel.setMaximumSize(new Dimension(280, 260));

        return filterBoxPanel;
    }

    private JPanel createFilterRow(String label, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelComponent = new JLabel(label);
        panel.add(labelComponent);
        panel.add(component);
        return panel;
    }

    private JPanel createFlightsListPanel() {
        JPanel flightsListPanel = new JPanel();
        flightsListPanel.setLayout(new BoxLayout(flightsListPanel, BoxLayout.Y_AXIS));
        flightsListPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Available Flights"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Use the flightsData obtained from PopulateFromDB
        for (Flight flight : flightsData) {
            JPanel flightPanel = createFlightPanel(flight);
            flightsListPanel.add(flightPanel);

            // Add some spacing between flight panels
            flightsListPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        }

        return flightsListPanel;
    }

    private JPanel createFlightPanel(Flight flight) {
        JPanel flightPanel = new JPanel();
        flightPanel.setLayout(new BoxLayout(flightPanel, BoxLayout.X_AXIS));
        flightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(createFlightInfoLabel("Flight: " + flight.getFlightNumber()));
        leftPanel.add(createFlightInfoLabel("From: " + flight.getDepartureLocation()));

        JLabel departureLabel = createFlightInfoLabel(flight.getDepartureTime());
        departureLabel.setFont(new Font("Arial", Font.BOLD, 24));
        leftPanel.add(departureLabel);
        leftPanel.add(createFlightInfoLabel(flight.getDepartureDate()));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        rightPanel.add(createFlightInfoLabel("Aircraft: " + flight.getAircraftID()));
        rightPanel.add(createFlightInfoLabel("To: " + flight.getArrivalLocation()));

        JLabel arrivalLabel = createFlightInfoLabel(flight.getArrivalTime());
        arrivalLabel.setFont(new Font("Arial", Font.BOLD, 24));
        rightPanel.add(arrivalLabel);
        rightPanel.add(createFlightInfoLabel(flight.getArrivalDate()));

        flightPanel.add(leftPanel);
        flightPanel.add(Box.createRigidArea(new Dimension(500, 75)));
        flightPanel.add(rightPanel);

        JButton selectButton = new JButton("Select");
        selectButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(selectButton);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Assuming SeatSelector is the class for Seat Selector page
                JOptionPane.showMessageDialog(null, "Something happening");

                // Close the current BrowsePassengerFlights page
            }
        });

        return flightPanel;
    }

    private JLabel createFlightInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void filterFlights() {
        String from = fromField.getText().trim().toLowerCase();
        String dest = destField.getText().trim().toLowerCase();
        String departureDate = departureDateField.getText().trim();
        String flightNumber = flightNumberField.getText().trim().toLowerCase();

        // Use these values to filter the flightsData list
        // Implement your filtering logic here

        // For example, you could create a new list to store filtered flights
        List<Flight> filteredFlights = new ArrayList<>();

        for (Flight flight : flightsData) {
            // Add flights to the filtered list based on your criteria
            if (flight.getDepartureLocation().toLowerCase().contains(from)
                    && flight.getArrivalLocation().toLowerCase().contains(dest)
                    && flight.getDepartureDate().contains(departureDate)
                    && flight.getFlightNumber().toLowerCase().contains(flightNumber)) {
                filteredFlights.add(flight);
            }
        }

        // Clear the current flightsListPanel and populate it with filtered flights
        updateFlightsListPanel(filteredFlights);
    }

    private void updateFlightsListPanel(List<Flight> filteredFlights) {
        // Clear the current flightsListPanel
        flightsListPanel.removeAll();

        // Add filtered flights to the flightsListPanel
        for (Flight flight : filteredFlights) {
            JPanel flightPanel = createFlightPanel(flight);
            flightsListPanel.add(flightPanel);
            flightsListPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        }

        // Repaint and revalidate the panel to reflect changes
        flightsListPanel.repaint();
        flightsListPanel.revalidate();
    }
}
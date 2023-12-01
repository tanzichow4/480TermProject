package ensf480.term_project.domain.GUIPages;

import ensf480.term_project.domain.Boundaries.PopulateFromDB;
import ensf480.term_project.domain.Flights.Flight;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class BrowsePassengerFlightsGUI extends JPanel {
    private List<Flight> flightsData;
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    // Create a filtered list to store the filtered flights
    private List<Flight> filteredFlights;

    public BrowsePassengerFlightsGUI() {
        setLayout(new BorderLayout());

        // Retrieve flight data from PopulateFromDB
        flightsData = PopulateFromDB.setFlights();
        filteredFlights = new ArrayList<>(flightsData); // Initialize filteredFlights with all flights

        System.out.println("Flights Data: " + flightsData);

        // Create top bar
        JPanel topBar = createTopBar();
        add(topBar, BorderLayout.NORTH);

        // Filter box on the left
        JPanel filterBoxPanel = createFilterBoxPanel();
        add(filterBoxPanel, BorderLayout.WEST);

        // List of available flights on the right
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        JPanel flightsListPanel = createFlightsListPanel();
        cardPanel.add(flightsListPanel, "flightsList");

        add(cardPanel, BorderLayout.CENTER);
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
                JOptionPane.showMessageDialog(null, "Signing out...");
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "login");
            }
        });
    

        // Add components to the top bar
        topBar.add(titleLabel, BorderLayout.CENTER);
        topBar.add(logoutButton, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createFilterBoxPanel() {
        JPanel filterBoxPanel = new JPanel();
        filterBoxPanel.setLayout(new BoxLayout(filterBoxPanel, BoxLayout.Y_AXIS));
        filterBoxPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Filters"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // From field
        JTextField fromField = new JTextField(10);
        JPanel fromPanel = createFilterRow("From:", fromField);

        // Destination field
        JTextField destField = new JTextField(10);
        JPanel destPanel = createFilterRow("Destination:", destField);

        // Departure Date field
        JTextField departureDateField = new JTextField(10);
        JPanel departureDatePanel = createFilterRow("Departure Date:", departureDateField);

        JTextField flightIDField = new JTextField(10);
        JPanel flightIDPanel = createFilterRow("Flight ID:", flightIDField);

        filterBoxPanel.add(fromPanel);
        filterBoxPanel.add(destPanel);
        filterBoxPanel.add(departureDatePanel);
        filterBoxPanel.add(flightIDPanel);

        // Add some spacing before the button
        filterBoxPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Search Flights button
        JButton searchButton = new JButton("Search Flights");
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the filtered flights based on the user input
                filteredFlights = filterFlights(fromField.getText(), destField.getText(),
                        departureDateField.getText(), flightIDField.getText());
                // Update the flights list panel with the filtered flights
                updateFlightsListPanel();
            }
        });
        filterBoxPanel.add(searchButton);

        // Set preferred and maximum size to control the height
        filterBoxPanel.setPreferredSize(new Dimension(280, 260));
        filterBoxPanel.setMaximumSize(new Dimension(280, 260));

        return filterBoxPanel;
    }

    // Method to filter flights based on user input
    private List<Flight> filterFlights(String from, String dest, String departureDate, String flightID) {
        List<Flight> filteredList = new ArrayList<>();

        for (Flight flight : flightsData) {
            if ((from.isEmpty() || flight.getDepartureLocation().equalsIgnoreCase(from))
                    && (dest.isEmpty() || flight.getArrivalLocation().equalsIgnoreCase(dest))
                    && (departureDate.isEmpty() || flight.getDepartureDate().equalsIgnoreCase(departureDate))
                    && (flightID.isEmpty() || flight.getFlightNumber().equalsIgnoreCase(flightID))) {
                filteredList.add(flight);
            }
        }

        return filteredList;
    }

    // Method to update the flights list panel with the filtered flights
    private void updateFlightsListPanel() {
        cardPanel.removeAll();
        JPanel flightsListPanel = createFlightsListPanel();
        cardPanel.add(flightsListPanel, "flightsList");
        cardLayout.show(cardPanel, "flightsList");
        revalidate();
        repaint();
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

        // Use the filteredFlights obtained from filterFlights
        for (Flight flight : filteredFlights) {
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

        leftPanel.add(createPriceLabel(flight.getBasePrice()));

        JButton selectButton = new JButton("Select");
        selectButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(selectButton);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Implement the browse passenger thing
                new PassengerListGUI(flight.getFlightID());
            }
        });

        return flightPanel;
    }

    private JLabel createPriceLabel(BigDecimal price) {
        JLabel priceLabel = new JLabel("Starting at: $" + price.setScale(2, RoundingMode.HALF_UP));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return priceLabel;
    }

    private JLabel createFlightInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
}
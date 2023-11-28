package ensf480.term_project.domain.Flights;

import ensf480.term_project.domain.Boundaries.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class BrowseFlights extends JPanel {
    private List<Flight> flightsData;
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public BrowseFlights() {
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
        JPanel flightsListPanel = createFlightsListPanel();
        add(flightsListPanel, BorderLayout.CENTER);
    }

    private JPanel createTopBar() {
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

        // Logout button on the right
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout button click
            }
        });

        topBar.add(menuBar, BorderLayout.WEST);
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
        JPanel fromPanel = createFilterRow("From:", new JTextField(10));

        // Destination field
        JPanel destPanel = createFilterRow("Destination:", new JTextField(10));

        // Departure Date field
        JPanel departureDatePanel = createFilterRow("Departure Date:", new JTextField(10));
        JPanel flightIDPanel = createFilterRow("Flight ID:", new JTextField(10));

        filterBoxPanel.add(fromPanel);
        filterBoxPanel.add(destPanel);
        filterBoxPanel.add(departureDatePanel);
        filterBoxPanel.add(flightIDPanel);

        // Add some spacing before the button
        filterBoxPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Search Flights button
        JButton searchButton = new JButton("Search Flights");
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        rightPanel.add(createFlightInfoLabel("Aircraft: " + flight.getAircraftID()));
        rightPanel.add(createFlightInfoLabel("To: " + flight.getArrivalLocation()));

        JLabel arrivalLabel = createFlightInfoLabel(flight.getArrivalTime());
        arrivalLabel.setFont(new Font("Arial", Font.BOLD, 24));
        rightPanel.add(arrivalLabel);

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
                // Assuming SeatSelector is the class for Seat Selector page
                SeatSelector seatSelector = new SeatSelector(flight.getFlightNumber(), flight.getBasePrice());
                // Close the current BrowseFlights page
            }
        });

        return flightPanel;
    }

    private JLabel createPriceLabel(BigDecimal price) {
        JLabel priceLabel = new JLabel("Starting at: $" + price.setScale(2, BigDecimal.ROUND_HALF_UP));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return priceLabel;
    }

    private JLabel createFlightInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
}

package ensf480.term_project.domain.Flights;

import ensf480.term_project.domain.Boundaries.PopulateFromDB;

import ensf480.term_project.domain.Users.Customer;

import ensf480.term_project.domain.Boundaries.PromoDatabaseHandler;
import ensf480.term_project.domain.Controllers.EmailSender;
import ensf480.term_project.domain.Promos.Promo;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class BrowseFlights extends JPanel {
    private List<Flight> flightsData;
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    // Create a filtered list to store the filtered flights
    private List<Flight> filteredFlights;

    public BrowseFlights() {
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

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();

        // Manage Purchases button
        JButton managePurchasesButton = new JButton("Manage Purchases");
        managePurchasesButton.setFont(new Font("Arial", Font.PLAIN, 16));
        managePurchasesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer loggedInCustomer = Login.getLoggedInCustomer();
                List<Seat> bookedSeats = Customer.getSeatsByUserID(loggedInCustomer.getUserID());

                if (bookedSeats.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You have no booked seats.");
                } else {
                    // Create a new JFrame to display the booked seats
                    JFrame bookedSeatsFrame = new JFrame("Booked Seats");
                    bookedSeatsFrame.setSize(600, 400);
                    bookedSeatsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    // Create a DefaultTableModel to make cells non-editable
                    DefaultTableModel model = new DefaultTableModel() {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };

                    // Add columns to the model (including Flight ID, concatenated "Seat" column,
                    // Seat Type, and Total Price)
                    String[] columnNames = { "Flight ID", "Seat ID", "Seat", "Seat Type", "Flight Number",
                            "Total Price" };
                    model.setColumnIdentifiers(columnNames);

                    // Modify the rowData array to include Seat ID
                    for (Seat seat : bookedSeats) {
                        Flight flight = Flight.getFlightBySeatID(seat.getSeatId());
                        String seatInfo = seat.getSeatRow() + seat.getSeatNumber();
                        BigDecimal totalPrice = seat.getPaymentAmount(); // Replace with your method to get seat price
                        Object[] rowData = { flight.getFlightID(), seat.getSeatId(), seatInfo, seat.getSeatType(),
                                flight.getFlightNumber(), totalPrice };
                        model.addRow(rowData);
                    }

                    // Create a JTable with the non-editable model
                    JTable table = new JTable(model);

                    // Add a ListSelectionListener to detect row selection
                    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                            if (!e.getValueIsAdjusting()) {
                                // Get the selected row and retrieve flight ID and seat information
                                int selectedRow = table.getSelectedRow();
                                if (selectedRow != -1) {
                                    // Assuming the flight ID is in the first column (index 0)
                                    int flightId = (int) model.getValueAt(selectedRow, 0);
                                    int seatID = (int) model.getValueAt(selectedRow, 1);

                                    // Prompt the user for confirmation
                                    int confirm = JOptionPane.showConfirmDialog(null,
                                            "Are you sure you want to cancel this flight?", "Confirm Cancellation",
                                            JOptionPane.YES_NO_OPTION);

                                    if (confirm == JOptionPane.YES_OPTION) {

                                        // Send Cancellation email
                                        EmailSender.sendCancelledFlight(loggedInCustomer.getEmail(), seatID, flightId);
                                         // Call the cancelFlight method to remove the selected flight
                                        loggedInCustomer.cancelFlight(flightId, seatID);

                                        // Clear existing rows
                                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                                        model.setRowCount(0);

                                        // Update the table to reflect the changes
                                        List<Seat> updatedBookedSeats = Customer
                                                .getSeatsByUserID(loggedInCustomer.getUserID());
                                        for (Seat seat : updatedBookedSeats) {
                                            Flight flight = Flight.getFlightBySeatID(seat.getSeatId());
                                            String seatInfo = seat.getSeatRow() + seat.getSeatNumber();
                                            BigDecimal totalPrice = seat.getPaymentAmount(); // Replace with your method
                                                                                             // to get seat price
                                            Object[] rowData = { flight.getFlightID(), seat.getSeatId(), seatInfo,
                                                    seat.getSeatType(),
                                                    flight.getFlightNumber(), totalPrice };
                                            model.addRow(rowData);
                                        }
                                    }
                                }
                            }
                        }
                    });

                    JScrollPane scrollPane = new JScrollPane(table);

                    // Add the table to the frame (at BorderLayout.CENTER)
                    bookedSeatsFrame.add(scrollPane, BorderLayout.CENTER);

                    // Create a panel for the Cancel button
                    JPanel buttonPanel = new JPanel();
                    JButton closeButton = new JButton("Close");
                    closeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            bookedSeatsFrame.dispose();
                        }
                    });
                    buttonPanel.add(closeButton);

                    // Add the button panel to the frame (at BorderLayout.SOUTH)
                    bookedSeatsFrame.add(buttonPanel, BorderLayout.SOUTH);

                    // Set the frame to be visible
                    bookedSeatsFrame.setLocationRelativeTo(null);
                    bookedSeatsFrame.setVisible(true);
                }
            }
        });

        // Manage Account button
        JButton manageAccountButton = new JButton("Manage Account");
        manageAccountButton.setFont(new Font("Arial", Font.PLAIN, 16));
        manageAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showManageAccountDialog();
            }
        });

        // Add buttons to the panel
        buttonPanel.add(managePurchasesButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add some spacing between buttons
        buttonPanel.add(manageAccountButton);

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

    private void showManageAccountDialog() {
        // Create a dialog
        JDialog manageAccountDialog = new JDialog();
        manageAccountDialog.setTitle("Manage Account");
        manageAccountDialog.setSize(300, 150);
        manageAccountDialog.setResizable(false);
        manageAccountDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Create a panel for the content
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));

        // Add a checkbox for Promo news
        JCheckBox promoCheckBox = new JCheckBox("Receive Promo News");
        // Add your logic here to set the initial state of the checkbox based on user
        // preferences

        // Add a save button
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the "Receive Promo News" checkbox is selected
                if (promoCheckBox.isSelected()) {
                    // Get the user's email address (replace "getUserEmail()" with the actual method
                    // to get the email)
                    List<Promo> promoList = PromoDatabaseHandler.getAllPromos();
                    Customer customer = Login.getLoggedInCustomer();

                    EmailSender.sendPromoCodeEmail(customer.getEmail(), promoList);
                }
                manageAccountDialog.dispose();
            }
        });

        // Add components to the panel
        dialogPanel.add(promoCheckBox);
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some spacing
        dialogPanel.add(saveButton);

        // Add the panel to the dialog
        manageAccountDialog.add(dialogPanel);

        // Set the dialog to be visible
        manageAccountDialog.setLocationRelativeTo(null);
        manageAccountDialog.setVisible(true);
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
                // Assuming SeatSelector is the class for Seat Selector page
                new SeatSelector(flight.getFlightNumber(), flight.getBasePrice());
                // Close the current BrowseFlights page
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
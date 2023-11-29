package ensf480.term_project.domain.Flights;

import javax.swing.*;

import ensf480.term_project.domain.Controllers.EmailSender;
import ensf480.term_project.domain.Users.*;
import ensf480.term_project.domain.Payments.*;
import ensf480.term_project.domain.Boundaries.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;

public class PurchasePage extends JFrame {
    private Flight flight;
    private Seat selectedSeat;
    private BigDecimal seatPrice;

    private JLabel selectedSeatLabel;
    private JTextField creditCardNumberField; // Updated variable
    private JTextField expiryDateField;
    private JTextField securityCodeField;
    private JTextField promoCodeField; // New promo code field
    private Customer customer = Login.getLoggedInCustomer();

    public PurchasePage(String flightNumber, Seat selectedSeat, BigDecimal seatPrice, Flight flight) {
        this.selectedSeat = selectedSeat;
        this.seatPrice = seatPrice;
        this.flight = flight;

        initComponents();

        setTitle("Payment Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public BigDecimal getTotalPrice() {
        return seatPrice.add(seatPrice.multiply(new BigDecimal("0.05")));
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Flight Info Panel
        JPanel flightInfoPanel = createFlightInfoPanel();
        mainPanel.add(flightInfoPanel, BorderLayout.WEST);

        // Seat Info Panel
        JPanel seatInfoPanel = createSeatInfoPanel();
        mainPanel.add(seatInfoPanel, BorderLayout.EAST);

        // Price Info Panel
        JPanel priceInfoPanel = createPriceInfoPanel();
        mainPanel.add(priceInfoPanel, BorderLayout.NORTH);

        // Credit Card Info Panel
        JPanel creditCardInfoPanel = createCreditCardInfoPanel();
        mainPanel.add(creditCardInfoPanel, BorderLayout.CENTER);

        // Continue to Confirm Purchase button
        JButton confirmButton = new JButton("Confirm Purchase");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String creditCardNumber = creditCardNumberField.getText();
                String expiryDate = expiryDateField.getText();
                String securityCode = securityCodeField.getText();
                String promoCode = promoCodeField.getText();

                if (validateCreditCardInfo(creditCardNumber, expiryDate, securityCode)) {
                    // If credit card info is valid, proceed with confirmation

                    // Additional logic for promo code processing can be added here

                    // Check if a promo code is provided
                    if (!promoCode.isEmpty()) {
                        // Check if the promo code is valid (you need to implement this logic)
                        boolean isValidPromoCode = PromoDatabaseHandler.isPromoCodeValid(promoCode);

                        if (isValidPromoCode) {
                            // Send promo code confirmation email
                            // EmailSender.sendPromoStatusEmail(user.getEmail(), promoCode);
                            // EmailSender.sendPromoCodeEmail(customer.getEmail(), promoCode);

                            // Notify the user about promo code status
                            JOptionPane.showMessageDialog(PurchasePage.this,
                                    "Promo code '" + promoCode + "' applied successfully!", "Promo Code Applied",
                                    JOptionPane.INFORMATION_MESSAGE);

                            // Update the system to mark the promo code as used (you need to implement this
                            // logic)
                            PromoDatabaseHandler.markPromoCodeAsUsed(customer.getUserID(), promoCode);
                        } else {
                            // Notify the user that the promo code is invalid
                            JOptionPane.showMessageDialog(PurchasePage.this,
                                    "Invalid promo code. Please check and try again.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return; // Do not proceed with the purchase if the promo code is invalid
                        }
                    }

                    BigDecimal total = seatPrice.multiply(new BigDecimal(1.05));

                    // Create a Payment object
                    Payment payment = new Payment(Login.getLoggedInCustomer().getUserID(), flight.getFlightID(), total,
                            creditCardNumber, securityCode, expiryDate, selectedSeat.getSeatId());

                    // Save the payment to the database
                    if (payment.saveToDatabase()) {
                        // Show a confirmation dialog
                        updateSeatStatus(selectedSeat);
                        JOptionPane.showMessageDialog(PurchasePage.this, "Purchase confirmed!", "Confirmation",
                                JOptionPane.INFORMATION_MESSAGE);

                        dispose();
                        // Open a new BrowseFlights page
                        BrowseFlights browseFlights = new BrowseFlights();
                        browseFlights.setVisible(true);
                        // Send purchase confirmation email
                        EmailSender.sendPurchaseConfirmationEmail(
                                customer.getEmail(), flight.getFlightID(), selectedSeat.getSeatId());

                        // Close the PurchasePage window

                        // You may also want to close the current PurchasePage or navigate to another
                        // page
                        // depending on your application flow
                    } else {
                        // If saving to the database fails, display an error message
                        JOptionPane.showMessageDialog(PurchasePage.this,
                                "Failed to save payment to the database. Please try again.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    // Additional logic for promo code processing can be added here
                } else {
                    // If credit card info is not valid, display an error message
                    JOptionPane.showMessageDialog(PurchasePage.this,
                            "Invalid credit card information. Please check and try again.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mainPanel.add(confirmButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createFlightInfoPanel() {
        JPanel flightInfoPanel = new JPanel(new GridLayout(5, 2));
        flightInfoPanel.setBorder(BorderFactory.createTitledBorder("Flight Info"));

        flightInfoPanel.add(new JLabel("Flight Number: " + flight.getFlightNumber()));
        flightInfoPanel.add(new JLabel("Departure: " + flight.getDepartureLocation()));
        flightInfoPanel.add(new JLabel("Destination: " + flight.getArrivalLocation()));
        flightInfoPanel.add(new JLabel("Departure Date: " + flight.getDepartureDate()));
        flightInfoPanel.add(new JLabel("Arrival Date: " + flight.getArrivalDate()));

        return flightInfoPanel;
    }

    private JPanel createSeatInfoPanel() {
        JPanel seatInfoPanel = new JPanel(new GridLayout(2, 2));
        seatInfoPanel.setBorder(BorderFactory.createTitledBorder("Seat Info"));

        selectedSeatLabel = new JLabel("Selected Seat: "
                + (selectedSeat != null ? selectedSeat.getSeatRow() + selectedSeat.getSeatNumber() : ""));
        seatInfoPanel.add(selectedSeatLabel);
        seatInfoPanel.add(new JLabel("Seat Type: " + (selectedSeat != null ? selectedSeat.getSeatType() : "")));

        return seatInfoPanel;
    }

    private JPanel createPriceInfoPanel() {
        JPanel priceInfoPanel = new JPanel(new GridLayout(3, 2));
        priceInfoPanel.setBorder(BorderFactory.createTitledBorder("Price Info"));

        priceInfoPanel.add(new JLabel("Total Seat Price: $"));
        priceInfoPanel.add(new JLabel(seatPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        priceInfoPanel.add(new JLabel("GST (5%): $"));
        priceInfoPanel.add(new JLabel(
                seatPrice.multiply(new BigDecimal("0.05")).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        priceInfoPanel.add(new JLabel("Total Amount: $"));
        priceInfoPanel.add(new JLabel(seatPrice.add(seatPrice.multiply(new BigDecimal("0.05")))
                .setScale(2, BigDecimal.ROUND_HALF_UP).toString()));

        return priceInfoPanel;
    }

    private JPanel createCreditCardInfoPanel() {
        JPanel creditCardInfoPanel = new JPanel(new GridLayout(4, 2));
        creditCardInfoPanel.setBorder(BorderFactory.createTitledBorder("Payment Info"));

        creditCardNumberField = new JTextField(); // Updated variable
        expiryDateField = new JTextField();
        securityCodeField = new JTextField();
        promoCodeField = new JTextField(); // New promo code field

        creditCardInfoPanel.add(new JLabel("Credit Card Number:")); // Updated label
        creditCardInfoPanel.add(creditCardNumberField); // Updated variable
        creditCardInfoPanel.add(new JLabel("Expiry Date:"));
        creditCardInfoPanel.add(expiryDateField);
        creditCardInfoPanel.add(new JLabel("Security Code:"));
        creditCardInfoPanel.add(securityCodeField);
        creditCardInfoPanel.add(new JLabel("Promo Code:")); // Label for promo code
        creditCardInfoPanel.add(promoCodeField); // Promo code input field

        return creditCardInfoPanel;
    }

    private boolean validateCreditCardInfo(String creditCardNumber, String expiryDate, String securityCode) {
        // Implement your validation logic here
        // For simplicity, just check if the fields are not empty
        return !creditCardNumber.trim().isEmpty() && !expiryDate.trim().isEmpty() && !securityCode.trim().isEmpty();
    }

    // Update seat status in the database
    private void updateSeatStatus(Seat selectedSeat) {
        try {
            DatabaseManager.connect("AIRLINE");
            Connection connection = DatabaseManager.getConnection("AIRLINE");

            // Prepare the SQL query for updating the seat's booked status
            String updateQuery = "UPDATE Seats SET booked = ? WHERE seat_id = ?";

            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                // Set parameters for the update statement
                updateStatement.setBoolean(1, true);
                updateStatement.setInt(2, selectedSeat.getSeatId());

                // Execute the update statement
                int affectedRows = updateStatement.executeUpdate();

                // Check if the update was successful
                if (affectedRows > 0) {
                    System.out.println("Seat status updated in the database.");
                } else {
                    System.err.println("Failed to update seat status in the database.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

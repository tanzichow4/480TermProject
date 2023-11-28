package ensf480.term_project.domain.Flights;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class PurchasePage extends JFrame {
    private Flight flight;
    private Seat selectedSeat;
    private BigDecimal seatPrice;

    private JLabel selectedSeatLabel;
    private JTextField cardholderNameField;
    private JTextField expiryDateField;
    private JTextField securityCodeField;

    public PurchasePage(String flightNumber, Seat selectedSeat, BigDecimal seatPrice, Flight flight) {
        this.selectedSeat = selectedSeat;
        this.seatPrice = seatPrice;
        this.flight = flight;

        initComponents();

        setTitle("Purchase Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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
                String cardholderName = cardholderNameField.getText();
                String expiryDate = expiryDateField.getText();
                String securityCode = securityCodeField.getText();

                if (validateCreditCardInfo(cardholderName, expiryDate, securityCode)) {
                    // If credit card info is valid, proceed with confirmation
                    JOptionPane.showMessageDialog(PurchasePage.this, "Purchase confirmed!", "Confirmation",
                            JOptionPane.INFORMATION_MESSAGE);
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
        // flightInfoPanel.add(new JLabel("Selected Seat: "
        // + (selectedSeat != null ? selectedSeat.getSeatRow() +
        // selectedSeat.getSeatNumber() : "")));

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
        JPanel creditCardInfoPanel = new JPanel(new GridLayout(3, 2));
        creditCardInfoPanel.setBorder(BorderFactory.createTitledBorder("Credit Card Info"));

        cardholderNameField = new JTextField();
        expiryDateField = new JTextField();
        securityCodeField = new JTextField();

        creditCardInfoPanel.add(new JLabel("Cardholder Name:"));
        creditCardInfoPanel.add(cardholderNameField);
        creditCardInfoPanel.add(new JLabel("Expiry Date:"));
        creditCardInfoPanel.add(expiryDateField);
        creditCardInfoPanel.add(new JLabel("Security Code:"));
        creditCardInfoPanel.add(securityCodeField);

        return creditCardInfoPanel;
    }

    private boolean validateCreditCardInfo(String cardholderName, String expiryDate, String securityCode) {
        // Implement your validation logic here
        // For simplicity, just check if the fields are not empty
        return !cardholderName.trim().isEmpty() && !expiryDate.trim().isEmpty() && !securityCode.trim().isEmpty();
    }

}

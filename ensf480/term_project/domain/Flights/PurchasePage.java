package ensf480.term_project.domain.Flights;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class PurchasePage extends JFrame {
    private final String flightId;
    private final Seat selectedSeat;
    private final BigDecimal seatPrice;
    private final BigDecimal basePrice; // Added base price

    public PurchasePage(String flightId, Seat selectedSeat, BigDecimal seatPrice, BigDecimal basePrice) {
        this.flightId = flightId;
        this.selectedSeat = selectedSeat;
        this.seatPrice = seatPrice;
        this.basePrice = basePrice; // Initialize the base price

        initComponents();
    }

    private void initComponents() {
        setTitle("Purchase Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Purchase Confirmation");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel flightLabel = new JLabel("Flight ID: " + flightId);
        flightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel seatLabel = new JLabel("Selected Seat: " + selectedSeat.getSeatRow() + selectedSeat.getSeatNumber());
        seatLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel priceLabel = new JLabel("Seat Price: $" + seatPrice);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel basePriceLabel = new JLabel("Base Price: $" + basePrice);
        basePriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel totalPriceLabel = new JLabel("Total Price: $" + seatPrice.add(basePrice));
        totalPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel confirmationLabel = new JLabel("Thank you for your purchase!");
        confirmationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backButton = new JButton("Back to Flights");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate back to the BrowseFlights page or any other desired action
                dispose();
            }
        });

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(flightLabel);
        mainPanel.add(seatLabel);
        mainPanel.add(priceLabel);
        mainPanel.add(basePriceLabel);
        mainPanel.add(totalPriceLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(confirmationLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(backButton);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        // For testing purposes
        SwingUtilities.invokeLater(() -> new PurchasePage("FL123", new Seat(1, "A", "1", "Business", false, 1), BigDecimal.valueOf(50.00), BigDecimal.valueOf(100.00)));
    }
}

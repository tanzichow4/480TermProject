package ensf480.term_project.domain.Flights;

import ensf480.term_project.domain.Boundaries.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SeatSelector extends JFrame {
    private final String flightId;
    private final BigDecimal basePrice;
    private Flight flight;
    private Seat selectedSeat;
    private JLabel selectedSeatLabel;
    private JLabel seatPriceLabel;
    private JLabel selectedSeatTypeLabel; // Added for displaying seat type

    public SeatSelector(String flightId, BigDecimal basePrice) {
        this.flightId = flightId;
        this.basePrice = basePrice;
        this.selectedSeat = null;

        initComponents();
    }

    private void initComponents() {
        setTitle("Seat Selector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Box on the left showing price, selected seat, and seat type
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(200, getHeight()));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        selectedSeatLabel = new JLabel("Selected Seat: "
                + (selectedSeat != null ? selectedSeat.getSeatRow() + selectedSeat.getSeatNumber() : ""));
        selectedSeatLabel.setFont(new Font("Arial", Font.BOLD, 18));

        seatPriceLabel = new JLabel("Seat Price: $" + basePrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        seatPriceLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel seatTypeLabel = new JLabel("Seat Type: ");
        seatTypeLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        selectedSeatTypeLabel = new JLabel("");
        selectedSeatTypeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel flightIdLabel = new JLabel("Flight ID: " + flightId);
        leftPanel.add(selectedSeatLabel);
        leftPanel.add(seatPriceLabel);
        leftPanel.add(seatTypeLabel);
        leftPanel.add(selectedSeatTypeLabel);
        leftPanel.add(flightIdLabel);

        // Seat map on the right
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        // Fetch flight information including seats from the database
        fetchFlightInformation();

        List<List<Seat>> seatMap = generateSeatMap();
        for (List<Seat> section : seatMap) {
            JPanel sectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

            int seatsPerRow = 2; // Number of seats per row

            for (int i = 0; i < section.size(); i += seatsPerRow) {
                JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

                for (int j = i; j < i + seatsPerRow && j < section.size(); j++) {
                    Seat seat = section.get(j);
                    JButton seatButton = new JButton(seat.getSeatRow() + seat.getSeatNumber());
                    seatButton.setPreferredSize(new Dimension(80, 80));
                    seatButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            handleSeatClick(seat);
                        }
                    });

                    // Highlight the selected seat
                    if (seat.getSeatStatus()) {
                        seatButton.setBackground(Color.RED);
                        seatButton.setEnabled(false); // Disable booked seats
                    } else if (seat.equals(selectedSeat)) {
                        seatButton.setBackground(Color.GREEN);
                    }

                    rowPanel.add(seatButton);
                }

                sectionPanel.add(rowPanel);
            }

            rightPanel.add(sectionPanel);
        }

        // Continue to Purchase button
        JButton continueButton = new JButton("Continue to Purchase");
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedSeat != null) {
                    openPurchasePage(selectedSeat, selectedSeatTypeLabel);
                } else {
                    JOptionPane.showMessageDialog(SeatSelector.this, "Please select a seat.");
                }
            }
        });

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        mainPanel.add(continueButton, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchFlightInformation() {
        // Fetch flight information including seats from the database
        List<Flight> flightsData = PopulateFromDB.setFlights();
        for (Flight f : flightsData) {
            if (f.getFlightNumber().equals(flightId)) {
                flight = f;
                break;
            }
        }

        if (flight != null) {
            flight.populateSeats();
        }
    }

    private List<List<Seat>> generateSeatMap() {
        List<List<Seat>> seatMap = new ArrayList<>();

        // For simplicity, let's assume three sections
        seatMap.add(flight.getSeats().subList(0, 4));
        seatMap.add(flight.getSeats().subList(4, 8));
        seatMap.add(flight.getSeats().subList(8, 12));

        return seatMap;
    }

    private void handleSeatClick(Seat seat) {
        selectedSeat = seat;
        // Update the selected seat label
        selectedSeatLabel.setText("Selected Seat: "
                + (selectedSeat != null ? selectedSeat.getSeatRow() + selectedSeat.getSeatNumber() : ""));

        // Update the seat type label
        selectedSeatTypeLabel.setText("Seat Type: " + (selectedSeat != null ? selectedSeat.getSeatType() : ""));

        // Update the seat price label (you can implement your logic to calculate the
        // seat price)
        BigDecimal seatPrice = calculateSeatPrice(selectedSeat);
        seatPriceLabel.setText("Seat Price: $" + seatPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    private BigDecimal calculateSeatPrice(Seat seat) {
        BigDecimal BASE_PRICE = BigDecimal.valueOf(50.00); // Assuming a constant base price for now
        BigDecimal flightBasePrice = flight.getBasePrice();
        BigDecimal ticketPrice;

        String seatType = seat.getSeatType();

        switch (seatType) {
            case "Ordinary":
                ticketPrice = BASE_PRICE.add(flightBasePrice);
                break;
            case "Comfort":
                ticketPrice = BASE_PRICE.add(flightBasePrice).multiply(new BigDecimal("1.5"));
                break;
            case "Business":
                ticketPrice = BASE_PRICE.add(flightBasePrice).multiply(new BigDecimal("2.0"));
                break;
            default:
                // Handle unknown seat type, you can set a default price or throw an exception
                ticketPrice = BASE_PRICE.add(flightBasePrice);
        }

        return ticketPrice;
    }

    private void openPurchasePage(Seat selectedSeat, JLabel selectedSeatTypeLabel) {
        // Create an instance of the PurchasePage class and pass relevant information
        SwingUtilities
                .invokeLater(() -> new PurchasePage(flightId, selectedSeat, calculateSeatPrice(selectedSeat), flight));
        // Close the current SeatSelector page if needed
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SeatSelector("FL123", BigDecimal.valueOf(100.00)));
    }
}

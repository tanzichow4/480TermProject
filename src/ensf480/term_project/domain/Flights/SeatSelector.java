package ensf480.term_project.domain.Flights;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SeatSelector extends JFrame {
    private final String flightId;
    private String selectedSeat;
    private JLabel selectedSeatLabel;

    public SeatSelector(String flightId) {
        this.flightId = flightId;
        this.selectedSeat = null;

        initComponents();
    }

    private void initComponents() {
        setTitle("Seat Selector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Box on the left showing price and selected seat
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(200, getHeight()));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        selectedSeatLabel = new JLabel("Selected Seat: " + (selectedSeat != null ? selectedSeat : ""));
        selectedSeatLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel flightIdLabel = new JLabel("Flight ID: " + flightId);
        leftPanel.add(selectedSeatLabel);
        leftPanel.add(flightIdLabel);

        // Seat map on the right
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        List<List<String>> seatMap = generateSeatMap();
        for (List<String> section : seatMap) {
            JPanel sectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

            int seatsPerRow = 2; // Number of seats per row

            for (int i = 0; i < section.size(); i += seatsPerRow) {
                JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

                for (int j = i; j < i + seatsPerRow && j < section.size(); j++) {
                    String seat = section.get(j);
                    JButton seatButton = new JButton(seat);
                    seatButton.setPreferredSize(new Dimension(80, 80));
                    seatButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            handleSeatClick(seat);
                        }
                    });

                    // Highlight the selected seat
                    if (seat.equals(selectedSeat)) {
                        seatButton.setBackground(Color.GREEN);
                    }

                    rowPanel.add(seatButton);
                }

                sectionPanel.add(rowPanel);
            }

            rightPanel.add(sectionPanel);
        }

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private List<List<String>> generateSeatMap() {
        List<List<String>> seatMap = new ArrayList<>();

        // Generate four seats for each section
        seatMap.add(generateSeats("B", 4));
        seatMap.add(generateSeats("C", 4));
        seatMap.add(generateSeats("O", 4));

        return seatMap;
    }

    private List<String> generateSeats(String section, int numSeats) {
        List<String> seats = new ArrayList<>();
        for (int i = 1; i <= numSeats; i++) {
            seats.add(section + i);
        }
        return seats;
    }

    private void handleSeatClick(String seat) {
        selectedSeat = seat;
        // Update the selected seat label
        selectedSeatLabel.setText("Selected Seat: " + (selectedSeat != null ? selectedSeat : ""));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SeatSelector("FL123"));
    }
}




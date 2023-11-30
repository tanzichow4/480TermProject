package ensf480.term_project.domain.Flights;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BrowsePassenger extends JPanel {
    private String flightNumber;

    public BrowsePassenger(String flightNumber) {
        this.flightNumber = flightNumber;

        // Retrieve passenger information for the selected flight (assuming you have lists for passengers)
        List<String> passengerNames = getPassengerNamesForFlight(flightNumber);
        List<Integer> passengerAges = getPassengerAgesForFlight(flightNumber);
        List<String> seatNumbers = getSeatNumbersForFlight(flightNumber);

        // Create a JList to display passenger information
        JList<String> passengerList = new JList<>(getPassengerInfoArray(passengerNames, passengerAges, seatNumbers));

        // Create a scroll pane for the JList
        JScrollPane scrollPane = new JScrollPane(passengerList);

        // Create a popup frame and add the scroll pane
        JFrame popupFrame = new JFrame("Passenger List - Flight " + flightNumber);
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupFrame.getContentPane().add(scrollPane);
        popupFrame.setSize(300, 200);
        popupFrame.setLocationRelativeTo(null); // Center the frame on the screen
        popupFrame.setVisible(true);
    }

    private String[] getPassengerInfoArray(List<String> names, List<Integer> ages, List<String> seatNumbers) {
        int size = names.size();
        String[] passengerInfoArray = new String[size];

        for (int i = 0; i < size; i++) {
            passengerInfoArray[i] = "Name: " + names.get(i) + ", Age: " + ages.get(i) + ", Seat: " + seatNumbers.get(i);
        }

        return passengerInfoArray;
    }

    // Dummy methods, replace these with your actual data retrieval logic
    private List<String> getPassengerNamesForFlight(String flightNumber) {
        // Implement logic to retrieve passenger names for the specified flight
        return List.of("John", "Jane", "Bob");
    }

    private List<Integer> getPassengerAgesForFlight(String flightNumber) {
        // Implement logic to retrieve passenger ages for the specified flight
        return List.of(25, 30, 22);
    }

    private List<String> getSeatNumbersForFlight(String flightNumber) {
        // Implement logic to retrieve seat numbers for the specified flight
        return List.of("A1", "B2", "C3");
    }
}

package ensf480.term_project.domain.Flights;
import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import ensf480.term_project.domain.Boundaries.PopulateFromDB;
import ensf480.term_project.domain.Users.*;

public class FlightManager extends JPanel {

    private JTextArea flightTextArea;


    public FlightManager() {
        setLayout(new BorderLayout());

        List<Flight> displayingFlightList = PopulateFromDB.getFlightList();

        // Create a JTextArea to display the list of flights
        flightTextArea = new JTextArea();
        flightTextArea.setEditable(false); // Make it non-editable
        updateFlightTextArea(displayingFlightList); // Update the text area with the initial list

        JButton backButton = new JButton("Go Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Going Back...");
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "adminManager");
            }
        });
        JButton addFlightButton = new JButton("Add Flight");
        addFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Insert Add Flight Code
                JFrame frame = new JFrame("Insert Flight Details:");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                String newFlightNumber = showTextFieldPopup(frame, "Enter Flight Number:");
                String newDepartLocation = showTextFieldPopup(frame, "Enter Departure Location:");
                String newArrivalLocation = showTextFieldPopup(frame, "Enter Arrival Location:");
                String newDepartTime = showTextFieldPopup(frame, "Enter Departure Time:");
                String newArrivalTime = showTextFieldPopup(frame, "Enter Arrival Time:");
                String newDepartDate = showTextFieldPopup(frame, "Enter Departure Date:");
                String newArrivalDate = showTextFieldPopup(frame, "Enter Arrival Date:"); 
                String newAircraftID = showTextFieldPopup(frame, "Enter Aircraft ID:");
                String newPrice = showTextFieldPopup(frame, "Enter Price:");


                // Perform actions based on userInput (for example, pass it to your systemAdmin)
                if (newFlightNumber != null && newDepartLocation != null && newArrivalLocation != null && 
                newDepartTime != null && newArrivalTime != null && newDepartDate != null && 
                newArrivalDate != null && newAircraftID != null && newPrice != null) {
                    int intNewAircraftID = Integer.parseInt(newAircraftID);
                    double doubleNewPrice = Double.parseDouble(newPrice);
                    BigDecimal BDNewPrice = BigDecimal.valueOf(doubleNewPrice);

                    SystemAdmin systemAdmin = Login.getLoggedInAdmin();
                    Flight newFlight = new Flight(0, newFlightNumber, newDepartLocation, 
                    newArrivalLocation, newDepartTime, newArrivalTime, newDepartDate, newArrivalDate, 
                    intNewAircraftID, BDNewPrice);
                    systemAdmin.addFlight(newFlight);
                    
                }
            }
        });

        // Add components to the panel
        add(backButton, BorderLayout.SOUTH);
        add(addFlightButton, BorderLayout.NORTH);
    }

    private String showTextFieldPopup(JFrame parent, String message) {
        JTextField textField = new JTextField();

        Object[] messageArray = {message, textField};

        int option = JOptionPane.showOptionDialog(
                parent,
                messageArray,
                "Flight Popup",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);

        // Check if the user clicked "OK"
        if (option == JOptionPane.OK_OPTION) {
            return textField.getText();
        } else {
            return null; // User clicked "Cancel" or closed the dialog
        }
    }

    private void updateFlightTextArea(List<Flight> flights) {
        flightTextArea.setText(""); // Clear the text area
        for (Flight flight : flights) {
            // Append flight information to the text area
            flightTextArea.append(flight.toString() + "\n");
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flight Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlightManager FlightManager = new FlightManager();
        frame.add(FlightManager);

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

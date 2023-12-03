package ensf480.term_project.domain.GUIPages;

import javax.swing.*;

import ensf480.term_project.domain.Controllers.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PassengerListGUI extends JPanel {
    List<String> passengerNames = new ArrayList<>();
    List<String> seatRow = new ArrayList<>();
    List<String> seatNumbers = new ArrayList<>();

    Connection airlineConnection = DatabaseManager.getConnection("AIRLINE");
    Connection billingConnection = DatabaseManager.getConnection("BILLING");

    public PassengerListGUI(int flightId) { // Change the parameter type to int for flight_id

        // Retrieve passenger information for the selected flight (assuming you have lists for passengers)
        initList(flightId, passengerNames, seatRow, seatNumbers);

        // Create a JList to display passenger information
        JList<String> passengerList = new JList<>(getPassengerInfoArray(passengerNames, seatRow, seatNumbers));

        // Create a scroll pane for the JList
        JScrollPane scrollPane = new JScrollPane(passengerList);

        // Create a popup frame and add the scroll pane
        JFrame popupFrame = new JFrame("Passenger List - Flight ID " + flightId); // Change the title
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupFrame.getContentPane().add(scrollPane);
        popupFrame.setSize(300, 200);
        popupFrame.setLocationRelativeTo(null); // Center the frame on the screen
        popupFrame.setVisible(true);
    }

    private String[] getPassengerInfoArray(List<String> names, List<String> seatRow, List<String> seatNumbers) {
        int size = names.size();
        String[] passengerInfoArray = new String[size];

        for (int i = 0; i < size; i++) {
            passengerInfoArray[i] = "Name: " + names.get(i) + ", Seat: " + seatRow.get(i) + seatNumbers.get(i);
        }
        return passengerInfoArray;
    }

    // Inserting all data into the three lists
    private void initList(int flightId, List<String> passengerNames, List<String> seatRow, List<String> seatNumbers) {
        // Clear lists
        passengerNames.clear();
        seatRow.clear();
        seatNumbers.clear();

        // Initialize Proxy List
        List<Integer> userIDList = new ArrayList<>();
        List<Integer> seatIDList = new ArrayList<>();

        try {
            // BILLING connection
            DatabaseManager.connect("BILLING");
            Connection billingConnection = DatabaseManager.getConnection("BILLING");

            // Filling userIDList
            try (Connection connection = billingConnection) {
                String sqlQueryUserID = "SELECT user_id FROM Payments WHERE flight_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQueryUserID)) {
                    preparedStatement.setInt(1, flightId); // Use setInt for flight_id

                    try (ResultSet resultSetUserID = preparedStatement.executeQuery()) {
                        while (resultSetUserID.next()) {
                            int userID = resultSetUserID.getInt("user_id");
                            userIDList.add(userID);
                        }
                    }
                }
            }
    
            DatabaseManager.connect("BILLING");
            billingConnection = DatabaseManager.getConnection("BILLING");
    
            // Filling seatIDList
            try (Connection connection = billingConnection) {
                String sqlQuerySeatID = "SELECT DISTINCT seat_id FROM Payments WHERE flight_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuerySeatID)) {
                    preparedStatement.setInt(1, flightId);
    
                    try (ResultSet resultSetSeatID = preparedStatement.executeQuery()) {
                        while (resultSetSeatID.next()) {
                            int seatID = resultSetSeatID.getInt("seat_id");
                            seatIDList.add(seatID);
                        }
                    }
                }
            }
    
            // AIRLINE connection
            DatabaseManager.connect("AIRLINE");
            Connection airlineConnection = DatabaseManager.getConnection("AIRLINE");
    
            // Filling PassengerName list
            try (Connection connection = airlineConnection) {
                for (Integer id : userIDList) {
                    String username = null;
                    String sqlQueryPassengerName = "SELECT username FROM RegisteredUsers WHERE user_id = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQueryPassengerName)) {
                        preparedStatement.setInt(1, id);
    
                        try (ResultSet resultSetPassengerName = preparedStatement.executeQuery()) {
                            if (resultSetPassengerName.next()) {
                                username = resultSetPassengerName.getString("username");
                                passengerNames.add(username);
                            }
                        }
                    }
                }
            }

            DatabaseManager.connect("AIRLINE");
            airlineConnection = DatabaseManager.getConnection("AIRLINE");

            // Filling Seat Row and Number
            try (Connection connection = airlineConnection) {
                for (Integer id : seatIDList) {
                    String row = null;
                    String number = null;
                    String sqlQuerySeatRow = "SELECT seat_row FROM Seats WHERE seat_id = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuerySeatRow)) {
                        preparedStatement.setInt(1, id);
    
                        try (ResultSet resultSetSeatRow = preparedStatement.executeQuery()) {
                            if (resultSetSeatRow.next()) {
                                row = resultSetSeatRow.getString("seat_row");
                                seatRow.add(row);
                            }
                        }
                    }
    
                    String sqlQuerySeatNumber = "SELECT seat_number FROM Seats WHERE seat_id = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuerySeatNumber)) {
                        preparedStatement.setInt(1, id);
    
                        try (ResultSet resultSetSeatNumber = preparedStatement.executeQuery()) {
                            if (resultSetSeatNumber.next()) {
                                number = resultSetSeatNumber.getString("seat_number");
                                seatNumbers.add(number);
                            }
                        }
                    }
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
    
}    


package ensf480.term_project.domain.Flights;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ensf480.term_project.domain.Boundaries.DatabaseManager;

public class BrowsePassenger extends JPanel {
    private String flightNumber;
    List<String> passengerNames = new ArrayList<>();
    List<String> seatRow = new ArrayList<>();
    List<String> seatNumbers = new ArrayList<>();
    Connection airlineConnection = DatabaseManager.getConnection("AIRLINE");
    Connection billingConnection = DatabaseManager.getConnection("BILLING");

    public BrowsePassenger(String flightNumber) {
        this.flightNumber = flightNumber;
        // Retrieve passenger information for the selected flight (assuming you have lists for passengers)

        initList(flightNumber, passengerNames, seatRow, seatNumbers);
        // Create a JList to display passenger information
        JList<String> passengerList = new JList<>(getPassengerInfoArray(passengerNames, seatRow, seatNumbers));

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

    private String[] getPassengerInfoArray(List<String> names, List<String> seatRow, List<String> seatNumbers) {
        int size = names.size();
        String[] passengerInfoArray = new String[size];

        for (int i = 0; i < size; i++) {
            passengerInfoArray[i] = "Name: " + names.get(i) + ", Seat: " + seatRow.get(i) + seatNumbers.get(i);
        }
        return passengerInfoArray;
    }

    //Inserting all data into the three lists
    private void initList(String flightNumber, List<String> passengerNames, List<String> seatRow, List<String> seatNumbers){
        //Clear lists
        passengerNames.clear();
        seatRow.clear();
        seatNumbers.clear();

        //Initialize Proxy List
        List<String> userIDList = new ArrayList<>();
        List<String> seatIDList = new ArrayList<>();

        //Query List
        String sqlQueryUserID = "SELECT user_id FROM Payments WHERE flight_id = " + flightNumber;
        String sqlQuerySeatID = "SELECT DISTINCT seat_id FROM Payments WHERE flight_id = " + flightNumber;
        String sqlQueryPassengerName;
        String sqlQuerySeatRow;
        String sqlQuerySeatNumber;

        //Filling Proxy Lists
        try{
            //Filling userIDList
            PreparedStatement preparedStatement = billingConnection.prepareStatement(sqlQueryUserID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userID = resultSet.getInt("user_id");
                userIDList.add(Integer.toString(userID));
            }
            //Filling seatIDList
            preparedStatement = billingConnection.prepareStatement(sqlQuerySeatID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int seatID = resultSet.getInt("seat_id");
                seatIDList.add(Integer.toString(seatID));
            }

            //Filling PassengerName list
            for (String id : userIDList){
                String username = null;
                sqlQueryPassengerName = "SELECT username FROM RegisteredUsers WHERE user_id = " + id;
                preparedStatement = airlineConnection.prepareStatement(sqlQueryPassengerName);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    username = resultSet.getString("username");
                    passengerNames.add(username);
                }
            }

            //Filling Seat Row and Number
            for (String id : seatIDList){
                String row = null;
                String number = null;
                sqlQuerySeatRow = "SELECT seat_row FROM Seats WHERE seat_id = " + id;
                preparedStatement = airlineConnection.prepareStatement(sqlQuerySeatRow);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    row = resultSet.getString("seat_row");
                    seatRow.add(row);
                }
                sqlQuerySeatNumber = "SELECT seat_number FROM Seats WHERE seat_id = " + id;
                preparedStatement = airlineConnection.prepareStatement(sqlQuerySeatNumber);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    number = resultSet.getString("seat_number");
                    seatNumbers.add(number);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
       

    }
}

package ensf480.term_project.domain.Users;

import ensf480.term_project.domain.Boundaries.*;
import ensf480.term_project.domain.Flights.*;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class Customer extends RegisteredUser {

    public Customer(int userID, String username, String password, String email, boolean isloggedin) {
        super(userID, username, password, email, isloggedin, 0); // Set user type to 0 for Customer
    }

    public Customer() {
        super();
    }

    public static List<Seat> getSeatsByUserID(int userID) {
        List<Seat> bookedSeats = new ArrayList<>();
        DatabaseManager.connect("BILLING");
        try (Connection billingConnection = DatabaseManager.getConnection("BILLING")) {
            String billingQuery = "SELECT * FROM Payments WHERE user_id = ?";
            try (PreparedStatement billingStatement = billingConnection.prepareStatement(billingQuery)) {
                billingStatement.setInt(1, userID);
                try (ResultSet billingResultSet = billingStatement.executeQuery()) {
                    while (billingResultSet.next()) {
                        int seatId = billingResultSet.getInt("seat_id");

                        // Now, query the AIRLINE database for seat details
                        DatabaseManager.connect("AIRLINE");
                        try (Connection airlineConnection = DatabaseManager.getConnection("AIRLINE")) {
                            String airlineQuery = "SELECT * FROM Seats WHERE seat_id = ?";
                            try (PreparedStatement airlineStatement = airlineConnection
                                    .prepareStatement(airlineQuery)) {
                                airlineStatement.setInt(1, seatId);
                                try (ResultSet airlineResultSet = airlineStatement.executeQuery()) {
                                    if (airlineResultSet.next()) {
                                        String seatRow = airlineResultSet.getString("seat_row");
                                        String seatNumber = airlineResultSet.getString("seat_number");
                                        String seatType = airlineResultSet.getString("seat_type");
                                        boolean booked = airlineResultSet.getBoolean("booked");
                                        int flightId = airlineResultSet.getInt("flight_id");

                                        Seat seat = new Seat(seatId, seatRow, seatNumber, seatType, booked, flightId);
                                        bookedSeats.add(seat);
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        DatabaseManager.close("AIRLINE");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseManager.close("BILLING");

        return bookedSeats;
    }
    public void cancelFlight(int flight_id) {

        
        try (Connection connection = DatabaseManager.getConnection("BILLING")) {
            String deleteQuery = "DELETE FROM Payments " +
                    "WHERE flight_id = ? AND user_id = ?";

            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.setInt(1, flight_id);
                deleteStatement.setInt(2, this.getUserID());

                int rowsAffected = deleteStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Flight canceled successfully.");
                } else {
                    System.out.println("Failed to cancel the flight or no payment found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

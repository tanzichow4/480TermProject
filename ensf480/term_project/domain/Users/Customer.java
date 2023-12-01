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

    public Customer(String username, String password, String email, boolean isloggedin) {
        super(username, password, email, isloggedin, 0);
        
        PopulateFromDB.createSystemCustomers(PopulateFromDB.setRegisteredUsers());
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


    public void cancelFlight(int flight_id, int seat_id) {

        DatabaseManager.connect("BILLING");
        try (Connection connection = DatabaseManager.getConnection("BILLING")) {
            connection.setAutoCommit(false);

            // Find the payment with the matching seat_id
            String paymentQuery = "SELECT payment_id, payment_amount FROM Payments WHERE seat_id = ?";
            try (PreparedStatement paymentStatement = connection.prepareStatement(paymentQuery)) {
                paymentStatement.setInt(1, seat_id);
                DatabaseManager.connect("BILLING");
                try (ResultSet paymentResult = paymentStatement.executeQuery()) {
                    if (paymentResult.next()) {
                        int payment_id = paymentResult.getInt("payment_id");
                        paymentResult.getBigDecimal("payment_amount");

                        // Delete the payment
                        String deletePaymentQuery = "DELETE FROM Payments WHERE payment_id = ?";
                        try (PreparedStatement deletePaymentStatement = connection
                                .prepareStatement(deletePaymentQuery)) {
                            deletePaymentStatement.setInt(1, payment_id);
                            int rowsAffected = deletePaymentStatement.executeUpdate();

                            if (rowsAffected > 0) {
                        
                                // Update the seat "booked" status
                                DatabaseManager.connect("AIRLINE");
                                String updateSeatQuery = "UPDATE Seats SET booked = FALSE WHERE seat_id = ?";
                                try (Connection connectionA = DatabaseManager.getConnection("AIRLINE");
                                        PreparedStatement updateSeatStatement = connectionA
                                                .prepareStatement(updateSeatQuery)) {
                                    updateSeatStatement.setInt(1, seat_id);
                                    int seatUpdateRows = updateSeatStatement.executeUpdate();

                                    if (seatUpdateRows > 0) {
                                        System.out.println("Seat status updated successfully.");
                                    } else {
                                        System.out.println("Failed to update seat status.");
                                    }
                                }
                            } else {
                                System.out.println("Failed to cancel payment.");
                            }
                        }
                    } else {
                        System.out.println("No payment found for the seat.");
                    }
                }
            }

            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }         
             
}

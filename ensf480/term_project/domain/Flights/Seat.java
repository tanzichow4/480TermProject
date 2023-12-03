package ensf480.term_project.domain.Flights;

import java.util.List;

import ensf480.term_project.domain.Controllers.*;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.*;

public class Seat {
    private int seatId;
    private String seatRow;
    private String seatNumber;
    private String seatType;
    private int flightId;
    private boolean booked;

    // Constructor
    public Seat(int seatId, String seatRow, String seatNumber, String seatType, boolean booked, int flightId) {
        this.seatId = seatId;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.flightId = flightId;
        this.booked = booked;
    }

    // Getters
    public int getSeatId() {
        return seatId;
    }

    public String getSeatRow() {
        return seatRow;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getSeatType() {
        return seatType;
    }

    public int getFlightId() {
        return flightId;
    }

    public boolean getSeatStatus() {
        return booked;
    }

    // Method to get seats by user ID
    public static List<Seat> getSeatsByUserID(int userID) {
        List<Seat> bookedSeats = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
            String query = "SELECT * FROM Seats WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int seatId = resultSet.getInt("seat_id");
                        String seatRow = resultSet.getString("seat_row");
                        String seatNumber = resultSet.getString("seat_number");
                        String seatType = resultSet.getString("seat_type");
                        boolean booked = resultSet.getBoolean("booked");
                        int flightId = resultSet.getInt("flight_id");

                        Seat seat = new Seat(seatId, seatRow, seatNumber, seatType, booked, flightId);
                        bookedSeats.add(seat);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookedSeats;
    }

    public BigDecimal getPaymentAmount() {
        // Use the seat_id to fetch the payment_amount from the Payments table in the
        // BILLING database
        DatabaseManager.connect("BILLING");
        try (Connection connection = DatabaseManager.getConnection("BILLING")) {
            String query = "SELECT payment_amount FROM Payments WHERE seat_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.getSeatId());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return (resultSet.getBigDecimal("payment_amount"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If no payment record is found, return a default value (you can adjust this
        // accordingly)
        return BigDecimal.ZERO;
    }

    public boolean unbookSeat() {
        if (!booked) {
            // Seat is not booked, no need to unbook
            return false;
        }
        DatabaseManager.connect("AIRLINE");
        try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
            String updateQuery = "UPDATE Seats SET booked = false, user_id = NULL WHERE seat_id = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setInt(1, seatId);
                int rowsUpdated = updateStatement.executeUpdate();

                // If the update was successful, set the booked status to false
                if (rowsUpdated > 0) {
                    booked = false;
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If the update was not successful, return false
        return false;
    }
}

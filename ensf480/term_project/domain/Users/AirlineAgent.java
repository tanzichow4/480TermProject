package ensf480.term_project.domain.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ensf480.term_project.domain.Controllers.DatabaseManager;

public class AirlineAgent extends RegisteredUser {

    public AirlineAgent(int userID, String username, String password, String email, boolean isloggedin) {
        super(userID, username, password, email, isloggedin, 3); // Set user type to 1 for System Admin
    }

    public void cancelFlight(int flight_id, int seat_id) {

        DatabaseManager.connect("BILLING");
        try (Connection connection = DatabaseManager.getConnection("BILLING")) {
            connection.setAutoCommit(false);

            // Find the payment with the matching seat_id
            String paymentQuery = "SELECT payment_id, payment_amount FROM Payments WHERE seat_id = ?";
            try (PreparedStatement paymentStatement = connection.prepareStatement(paymentQuery)) {
                paymentStatement.setInt(1, seat_id);

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
                                System.out.println("Payment canceled successfully.");

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

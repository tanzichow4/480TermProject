package ensf480.term_project.domain.Payments;

import ensf480.term_project.domain.Boundaries.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.math.BigDecimal;

public class Payment {
    private int paymentID;
    private int userID;
    private int flightID;
    private BigDecimal paymentAmount;
    private String paymentCardNumber;
    private String cardCVV;
    private String expiryDate;
    private int seatID;

    public Payment(int userID, int flightID, BigDecimal paymentAmount, String paymentCardNumber, String cardCVV,
            String expiryDate, int seatID) {
        this.userID = userID;
        this.flightID = flightID;
        this.paymentAmount = paymentAmount;
        this.paymentCardNumber = paymentCardNumber;
        this.cardCVV = cardCVV;
        this.expiryDate = expiryDate;
        this.seatID = seatID;
    }

    // Getter methods for retrieving information about the payment
    public int getPaymentID() {
        return paymentID;
    }

    public int getUserID() {
        return userID;
    }

    public int getFlightID() {
        return flightID;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public String getPaymentCard() {
        return paymentCardNumber;
    }

    public boolean saveToDatabase() {
        try {
            DatabaseManager.connect("BILLING");
            Connection connection = DatabaseManager.getConnection("BILLING");

            // Prepare the SQL query for inserting a new payment
            String query = "INSERT INTO Payments (seat_id, user_id, flight_id, payment_amount, credit_card_number, expiration_date, CVV) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS)) {
                // Set parameters for the prepared statement
                preparedStatement.setInt(1, seatID);
                preparedStatement.setInt(2, userID);
                preparedStatement.setInt(3, flightID);
                preparedStatement.setBigDecimal(4, paymentAmount);
                preparedStatement.setString(5, paymentCardNumber);
                preparedStatement.setString(6, expiryDate);
                preparedStatement.setString(7, cardCVV);

                // Execute the insert statement
                int affectedRows = preparedStatement.executeUpdate();

                // Check if the insertion was successful
                if (affectedRows > 0) {
                    System.out.println("Payment saved to the database successfully.");

                    // Retrieve the auto-generated payment_id
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            paymentID = generatedKeys.getInt(1);
                        }
                    }

                    return true; // Return true if the save operation was successful
                } else {
                    System.err.println("Failed to save payment to the database.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Return false if the save operation failed
    }

    public boolean existsInDatabase() {
        try {
            Connection connection = DatabaseManager.getConnection("BILLING");

            // Prepare the SQL query to check if the payment exists
            String query = "SELECT COUNT(*) FROM Payments WHERE payment_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, paymentID);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Check if any rows were returned (indicating existence)
                if (resultSet.next()) {
                    int rowCount = resultSet.getInt(1);
                    return rowCount > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // In case of an exception or if no rows were returned
        return false;
    }
}
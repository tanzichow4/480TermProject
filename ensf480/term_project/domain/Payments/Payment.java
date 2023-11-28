package ensf480.term_project.domain.Payments;

import ensf480.term_project.domain.Boundaries.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private int paymentID;
    private int userID;
    private int flightID;
    private BigDecimal paymentAmount;
    private LocalDateTime paymentDate;
    private PaymentCard paymentCard;

    public Payment(int paymentID, int userID, int flightID, BigDecimal paymentAmount, LocalDateTime paymentDate, PaymentCard paymentCard) {
        this.paymentID = paymentID;
        this.userID = userID;
        this.flightID = flightID;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
        this.paymentCard = paymentCard;
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

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public PaymentCard getPaymentCard() {
        return paymentCard;
    }

    public void saveToDatabase() {
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");

            // Prepare the SQL query for inserting a new payment
            String query = "INSERT INTO Payments (user_id, flight_id, payment_amount, payment_date, credit_card_number, expiration_date) " +
                           "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                // Set parameters for the prepared statement
                preparedStatement.setInt(1, userID);
                preparedStatement.setInt(2, flightID);
                preparedStatement.setBigDecimal(3, paymentAmount);
                preparedStatement.setObject(4, paymentDate);
                preparedStatement.setString(5, paymentCard.getCardNumber());
                preparedStatement.setString(6, paymentCard.getExpirationDate());

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
                } else {
                    System.err.println("Failed to save payment to the database.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existsInDatabase() {
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");

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

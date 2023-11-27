package ensf480.term_project.domain.Payments;

import ensf480.term_project.domain.Boundaries.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ticket {

    private int ticketID;
    private int userID;
    private int seatID;
    private int flightID;
    private BigDecimal ticketPrice;

    public Ticket(int ticketID, double seatPrice, int userID, int seatID, int flightID) {
        this.ticketID = ticketID;
        this.userID = userID;
        this.seatID = seatID;
        this.flightID = flightID;
        saveToDatabase();
    }

    public int getTicketID() {
        return ticketID;
    }

    public int getUserID() {
        return userID;
    }

    public int getSeatID() {
        return seatID;
    }

    public int getFlightID() {
        return flightID;
    }
    // Method to save the ticket to the database
    public void saveToDatabase() {
        try {
            Connection connection = DatabaseManager.getConnection("BILLING");

            String query = "INSERT INTO Tickets (flight_id, user_id, seat_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, flightID);
                preparedStatement.setInt(2, userID);
                preparedStatement.setInt(3, seatID);
                preparedStatement.setBigDecimal(4, ticketPrice);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void priceCalculator(String type) {

    }
}

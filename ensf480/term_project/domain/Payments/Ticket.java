package ensf480.term_project.domain.Payments;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ensf480.term_project.domain.Boundaries.DatabaseManager;
import ensf480.term_project.domain.Boundaries.FlightSeatTicketGets;
import ensf480.term_project.domain.Flights.Flight;
import ensf480.term_project.domain.Flights.Seat;

public class Ticket {

    private int ticketID;
    private int userID;
    private int seatID;
    private int flightID;
    // Assume you have references to Flight and Seat objects
    private Flight flight;
    private Seat seat;

    private static final BigDecimal BASE_PRICE = new BigDecimal("50.00");
    private static final BigDecimal COMFORT_PRICE_MULTIPLIER = new BigDecimal("1.5");
    private static final BigDecimal FIRST_CLASS_PRICE_MULTIPLIER = new BigDecimal("2.0");

    // Price calculated based on seat type
    private BigDecimal ticketPrice;


    public Ticket(int ticketID, int userID, int seatID, int flightID) {
        this.ticketID = ticketID;
        this.userID = userID;
        this.seatID = seatID;
        this.flightID = flightID;

        // Assuming you have a method to retrieve Flight and Seat objects
        this.flight = getFlightDetails(flightID);
        this.seat = getSeatDetails(seatID);
        this.ticketPrice = calculatePrice();
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

    // Getters for flight information
    public String getFlightNumber() {
        return flight.getFlightNumber();
    }

    public String getDepartureLocation() {
        return flight.getDepartureLocation();
    }

    public String getArrivalLocation() {
        return flight.getArrivalLocation();
    }

    public String getDepartureTime() {
        // You might want to format the LocalDateTime as a String
        return flight.getDepartureTime().toString();
    }

    // Getters for seat information
    public String getSeatNumber() {
        return seat.getSeatNumber();
    }

    public String getSeatType() {
        return seat.getSeatType();
    }

    // Assume you have methods to retrieve Flight and Seat details
    private Flight getFlightDetails(int flightID) {
        return FlightSeatTicketGets.getFlightDetails(flightID);
    }

    private Seat getSeatDetails(int seatID) {
        return FlightSeatTicketGets.getSeatDetails(seatID);

    }
    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    // Calculate ticket price based on seat type
    private BigDecimal calculatePrice() {
        String seatType = seat.getSeatType();

        switch (seatType) {
            case "Ordinary":
                ticketPrice = BASE_PRICE;
                break;
            case "Comfort":
                ticketPrice = BASE_PRICE.multiply(COMFORT_PRICE_MULTIPLIER);
                break;
            case "Business":
                ticketPrice = BASE_PRICE.multiply(FIRST_CLASS_PRICE_MULTIPLIER);
                break;
            default:
                // Handle unknown seat type, you can set a default price or throw an exception
                ticketPrice = BASE_PRICE;
        }
        return ticketPrice;
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
}

package ensf480.term_project.domain.Payments;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import ensf480.term_project.domain.Boundaries.DatabaseManager;
import ensf480.term_project.domain.Boundaries.FlightSeatTicketGets;
import ensf480.term_project.domain.Flights.Flight;
import ensf480.term_project.domain.Flights.Seat;
import ensf480.term_project.domain.Promos.Promo;

public class Ticket {

    private int ticketID;
    private int userID;
    private int seatID;
    private int flightID;
    // Assume you have references to Flight and Seat objects
    private Flight flight;
    private Seat seat;

    private static final BigDecimal BASE_PRICE = new BigDecimal("50.00");
    private BigDecimal flightBasePrice;

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
        this.flightBasePrice = flight.getBasePrice();
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

    public BigDecimal getFlightPrice(){
        return flight.getBasePrice();
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

    public void applyPromoCode(String promoCode) {
        // Check if the promo code is valid and not used
        Promo promo = getPromoDetails(promoCode);

        if (promo != null && !promo.isUsed()) {
            // Calculate the discounted price
            BigDecimal discountedPrice = ticketPrice.subtract(new BigDecimal(promo.getDiscountAmount()));

            // Save the discounted price to the database
            savePaymentToDatabase(promoCode, promoCode, flightID, discountedPrice);

            // Mark the promo code as used
            promo.setUsed(true);
            promo.saveToDatabase();

            System.out.println("Promo code applied successfully. Discounted price: " + discountedPrice);
        } else {
            System.out.println("Invalid or used promo code. No discount applied.");
        }
    }

    private void savePaymentToDatabase(String creditCardNumber, String expirationDate, int CVV, BigDecimal totalPrice) {
        try (Connection connection = DatabaseManager.getConnection("BILLING")) {
            String query = "INSERT INTO Payments (user_id, flight_id, payment_amount, payment_date, credit_card_number, expiration_date, CVV) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, userID);
                preparedStatement.setInt(2, flightID);
                preparedStatement.setInt(3, seatID);
                preparedStatement.setBigDecimal(4, ticketPrice);
                preparedStatement.setString(5, creditCardNumber);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Promo getPromoDetails(String promoCode) {
        try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
            String query = "SELECT * FROM Promos WHERE promo_code = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, promoCode);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int userID = resultSet.getInt("user_id");
                        int discountAmount = resultSet.getInt("discount_amount");
                        boolean used = resultSet.getBoolean("used");

                        return new Promo(userID, promoCode, discountAmount, used);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Promo code not found
    }

    // Calculate ticket price based on seat type
    private BigDecimal calculatePrice() {
        String seatType = seat.getSeatType();

        switch (seatType) {
            case "Ordinary":
                ticketPrice = BASE_PRICE.add(flightBasePrice);
                break;
            case "Comfort":
                ticketPrice = BASE_PRICE.multiply((new BigDecimal("1.5")).add(flightBasePrice));
                break;
            case "Business":
                ticketPrice = BASE_PRICE.multiply((new BigDecimal("2.0")).add(flightBasePrice));
                break;
            default:
                // Handle unknown seat type, you can set a default price or throw an exception
                ticketPrice = BASE_PRICE.add(flightBasePrice);
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
    
    public void markSeatAsBooked() {
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");

            // Prepare the SQL query to update the seat status
            String query = "UPDATE Seats SET booked = true WHERE seat_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, seatID);

                // Execute the update statement
                int affectedRows = preparedStatement.executeUpdate();

                // Check if the update was successful
                if (affectedRows > 0) {
                    System.out.println("Seat marked as booked in the database.");
                } else {
                    System.err.println("Failed to mark seat as booked in the database.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

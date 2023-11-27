package ensf480.term_project.domain.Boundaries;

import ensf480.term_project.domain.Flights.Flight;
import ensf480.term_project.domain.Flights.Seat;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class FlightSeatTicketGets {

    public static Flight getFlightDetails(int flightID) {
        Flight flight = null;
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");
            String query = "SELECT * FROM Flights WHERE flight_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, flightID);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int flightId = resultSet.getInt("flight_id");
                    String flightNumber = resultSet.getString("flight_number");
                    String departureLocation = resultSet.getString("departure_location");
                    String arrivalLocation = resultSet.getString("arrival_location");
                    LocalDateTime departureTime = resultSet.getTimestamp("departure_time").toLocalDateTime();
                    LocalDateTime arrivalTime = resultSet.getTimestamp("arrival_time").toLocalDateTime();
                    int aircraftId = resultSet.getInt("aircraft_id");
                    BigDecimal basePrice = resultSet.getBigDecimal("base_price");

                    flight = new Flight(flightId, flightNumber, departureLocation, arrivalLocation, departureTime, arrivalTime, aircraftId, basePrice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flight;
    }

    public static Seat getSeatDetails(int seatID) {
        Seat seat = null;
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");
            String query = "SELECT * FROM Seats WHERE seat_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, seatID);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int seatId = resultSet.getInt("seat_id");
                    String seatRow = resultSet.getString("seat_row");
                    String seatNumber = resultSet.getString("seat_number");
                    String seatType = resultSet.getString("seat_type");
                    boolean booked = resultSet.getBoolean("booked");
                    int flightId = resultSet.getInt("flight_id");

                    seat = new Seat(seatId, seatRow, seatNumber, seatType, booked, flightId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seat;
    }
}

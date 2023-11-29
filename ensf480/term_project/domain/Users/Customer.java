package ensf480.term_project.domain.Users;

import ensf480.term_project.domain.Boundaries.*;
import ensf480.term_project.domain.Flights.*;
import java.util.List;

import java.sql.*;

public class Customer extends RegisteredUser {

    public Customer(int userID, String username, String password, String email, boolean isloggedin) {
        super(userID, username, password, email, isloggedin, 0); // Set user type to 0 for Customer
    }

    public Customer() {
        super();
    }

    public void displayFlights() {
        // Fetch booked seats associated with the user
        List<Seat> bookedSeats = Seat.getSeatsByUserID(this.getUserID());

        if (bookedSeats.isEmpty()) {
            System.out.println("You have no booked flights.");
        } else {
            System.out.println("Your booked flights:");

            for (Seat seat : bookedSeats) {
                Flight flight = Flight.getFlightBySeatID(seat.getSeatId());

                if (flight != null) {
                    System.out.println("Flight ID: " + flight.getFlightID());
                    System.out.println("Flight Number: " + flight.getFlightNumber());
                    System.out.println("Departure Location: " + flight.getDepartureLocation());
                    System.out.println("Arrival Location: " + flight.getArrivalLocation());
                    System.out.println("Departure Time: " + flight.getDepartureTime());
                    System.out.println("Arrival Time: " + flight.getArrivalTime());
                    System.out.println("Aircraft ID: " + flight.getAircraftID());
                    System.out.println("Base Price: " + flight.getBasePrice());
                    System.out.println("--------------------------");
                }
            }
        }
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

package ensf480.term_project.domain.Users;

import ensf480.term_project.domain.Flights.*;
import ensf480.term_project.domain.Boundaries.*;

// Import necessary libraries
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BrowsePassengers {
    

    /**
     * THIS MAY NOT WORK - HAS NOT BEEN TESTED
     */
    public List<Customer> browsePassengers(Flight flight) {
        List<Customer> passengers = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
            String query = "SELECT ru.user_id " +
                           "FROM RegisteredUsers ru " +
                           "JOIN Tickets t ON ru.user_id = t.user_id " +
                           "JOIN Seats s ON t.seat_id = s.seat_id " +
                           "WHERE t.flight_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, flight.getFlightID());

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int userId = resultSet.getInt("user_id");

                        // Assuming you have a method to get the customer list from the system
                        List<Customer> customerList = PopulateFromDB.getCustomers();

                        // Find the customer with the matching user_id
                        Customer passenger = customerList.stream()
                                .filter(customer -> customer.getUserID() == userId)
                                .findFirst()
                                .orElse(null);

                        if (passenger != null) {
                            passengers.add(passenger);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passengers;
    }
}

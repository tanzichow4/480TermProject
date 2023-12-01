package ensf480.term_project.domain.Users;

import ensf480.term_project.domain.Boundaries.*;
import ensf480.term_project.domain.Flights.Flight;
import ensf480.term_project.domain.Users.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;


public class SystemAdmin extends RegisteredUser {

    public SystemAdmin(int userID, String username, String password, String email, boolean isloggedin) {
        super(userID, username, password, email, isloggedin, 1); // Set user type to 1 for System Admin
    }

    public void executeCommand(AdminCommand command) {
        command.execute();
    }

    public void addFlight(Flight flight) {
        try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
            // Insert flight
            String flightQuery = "INSERT INTO Flights (flight_number, departure_location, arrival_location, " +
                    "departure_time, arrival_time, departure_date, arrival_date, aircraft_id, base_price) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
            try (PreparedStatement flightStatement = connection.prepareStatement(flightQuery, Statement.RETURN_GENERATED_KEYS)) {
                flightStatement.setString(1, flight.getFlightNumber());
                flightStatement.setString(2, flight.getDepartureLocation());
                flightStatement.setString(3, flight.getArrivalLocation());
                flightStatement.setString(4, flight.getDepartureTime());
                flightStatement.setString(5, flight.getArrivalTime());
                flightStatement.setString(6, flight.getDepartureDate());
                flightStatement.setString(7, flight.getArrivalDate());
                flightStatement.setInt(8, flight.getAircraftID());
                flightStatement.setBigDecimal(9, flight.getBasePrice());
    
                int affectedRows = flightStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating flight failed, no rows affected.");
                }
    
                try (ResultSet generatedKeys = flightStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        flight.setFlightID(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating flight failed, no ID obtained.");
                    }
                }
            }
    
            // Insert seats
            String seatQuery = "INSERT INTO Seats (seat_row, seat_number, seat_type, flight_id, booked) VALUES (?, ?, ?, ?, FALSE)";

            try (PreparedStatement seatStatement = connection.prepareStatement(seatQuery)) {
                int totalSeatsToAdd = 12;  // Change this value if you want to add a different number of seats
            
                int seatsPerRow = totalSeatsToAdd / 6;  // 2 rows each for A, B, C, D, E, F
                char[] rows = new char[]{'A', 'B', 'C', 'D', 'E', 'F'};
            
                for (char row : rows) {
                    for (int seatNumber = 1; seatNumber <= seatsPerRow; seatNumber++) {
                        String seatType;
            
                        if (row == 'A' || row == 'B') {
                            seatType = "Business";
                        } else if (row == 'C' || row == 'D') {
                            seatType = "Comfort";
                        } else {  // 'E' or 'F'
                            seatType = "Ordinary";
                        }
            
                        seatStatement.setString(1, Character.toString(row));
                        seatStatement.setString(2, Integer.toString(seatNumber));
                        seatStatement.setString(3, seatType);
                        seatStatement.setInt(4, flight.getFlightID());
            
                        seatStatement.addBatch();
                    }
                }
                seatStatement.executeBatch();
                System.out.println("Flight and seats added to the database successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // private String determineSeatType(int seatNumber) {
    //     // Assign the first 4 seats as "Business," the next 4 seats as "Comfort," and the last 4 seats as "Ordinary" for each row
    //     if (seatNumber <= 4) {
    //         return "Business";
    //     } else if (seatNumber <= 8) {
    //         return "Comfort";
    //     } else {
    //         return "Ordinary";
    //     }
    // }
    
    public void insertAircraft(String aircraftName) {
        DatabaseManager.connect("AIRLINE");
        try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
            String query = "INSERT INTO Aircrafts (aircraft_name) VALUES (?)";
    
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, aircraftName);
                preparedStatement.executeUpdate();
                System.out.println("Aircraft added to the database successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public void printUsers() {
        List<RegisteredUser> users = PopulateFromDB.setRegisteredUsers();

        System.out.println("Regsitered Users in System:\n");
        for (RegisteredUser user : users) {
            System.out.println("User " + user.getUserID()+ ": Username: " + user.getUsername() + ", Email: " + user.getEmail());
        }
    }
}


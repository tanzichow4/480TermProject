package ensf480.term_project.domain.Boundaries;

import ensf480.term_project.domain.Users.*;
import ensf480.term_project.domain.Flights.*;

// Import necessary libraries
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class PopulateFromDB {

    private static List<Flight> flightList = new ArrayList<>();
    private static List<RegisteredUser> registeredUserList = new ArrayList<>();
    private static List<Aircraft> aircraftList = new ArrayList<>();
    private static List<Customer> customerList = new ArrayList<>();

    // Method to populate ArrayList from Users table
    // Gets all the RegisteredUsers currently in the DB table
    // Method to populate ArrayList from Users table
    // Gets all the RegisteredUsers currently in the DB table
    public static List<RegisteredUser> setRegisteredUsers() {
        List<RegisteredUser> userList = new ArrayList<>();
        DatabaseManager.connect("AIRLINE");
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE"); // Use the connection from DatabaseManager
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT user_id, username, pass, email, user_type, is_logged_in FROM RegisteredUsers");
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("pass");
                String email = resultSet.getString("email");
                int userType = resultSet.getInt("user_type");
                boolean isloggedin = resultSet.getBoolean("is_logged_in");

                RegisteredUser user = new RegisteredUser(userId, username, password, email, isloggedin, userType);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        registeredUserList = userList;
        return registeredUserList;
    }

    // Method to create SystemAdmin instances for users with user_type = 1
    public static List<SystemAdmin> createSystemAdmins(List<RegisteredUser> registeredUsers) {
        List<SystemAdmin> systemAdminList = new ArrayList<>();
        DatabaseManager.connect("AIRLINE");

        // Clear the existing list before adding administrators
        systemAdminList.clear();

        for (RegisteredUser user : registeredUsers) {
            if (user.getUserType() == 1) { // Check if user_type is 1 (System Admin)
                SystemAdmin systemAdmin = new SystemAdmin(
                        user.getUserID(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getLoggedIN());
                systemAdminList.add(systemAdmin);
            }
        }

        return systemAdminList;
    }

    public static List<Customer> createSystemCustomers(List<RegisteredUser> registeredUsers) {

        customerList.clear();
        DatabaseManager.connect("AIRLINE");
        for (RegisteredUser user : registeredUsers) {
            if (user.getUserType() == 0) { // Check if user_type is 1 (System Admin)
                Customer customer = new Customer(
                        user.getUserID(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getLoggedIN());
                customerList.add(customer);
            }
        }

        return customerList;
    }

    // Method to populate ArrayList from Flights table
    // Call set flights every time the system admin adds a flight
    public static List<Flight> setFlights() {
        List<Flight> flights = new ArrayList<>();
        DatabaseManager.connect("AIRLINE");
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Flights");
            while (resultSet.next()) {
                int flightId = resultSet.getInt("flight_id");
                String flightNumber = resultSet.getString("flight_number");
                String departureLocation = resultSet.getString("departure_location");
                String arrivalLocation = resultSet.getString("arrival_location");
                String departureTime = resultSet.getString("departure_time");
                String arrivalTime = resultSet.getString("arrival_time");
                String departureDate = resultSet.getString("departure_date");
                String arrivalDate = resultSet.getString("arrival_date");

                int aircraftId = resultSet.getInt("aircraft_id");
                BigDecimal basePrice = resultSet.getBigDecimal("base_price");

                Flight flight = new Flight(flightId, flightNumber, departureLocation, arrivalLocation, departureTime,
                        arrivalTime, departureDate, arrivalDate, aircraftId, basePrice);

                // Add Flight object to the list
                flights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        flightList = flights;
        return flightList;
    }

    public static List<Aircraft> setAircrafts() {
        List<Aircraft> aircraftList = new ArrayList<>();
        DatabaseManager.connect("AIRLINE");

        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Aircrafts");

            while (resultSet.next()) {
                int aircraftId = resultSet.getInt("aircraft_id");
                String aircraftName = resultSet.getString("aircraft_name");

                Aircraft aircraft = new Aircraft(aircraftId, aircraftName);
                aircraftList.add(aircraft);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aircraftList;
    }

    // Method to update ArrayList from Users table
    public static void updateRegisteredUsers() {
        registeredUserList.clear(); // Clear the existing list
        DatabaseManager.connect("AIRLINE");

        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE"); // Use the connection from DatabaseManager
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("pass");
                String email = resultSet.getString("email");
                int userType = resultSet.getInt("user_type");
                boolean isloggedin = resultSet.getBoolean("is_logged_in");

                RegisteredUser user = new RegisteredUser(userId, username, password, email, isloggedin, userType);
                registeredUserList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Call this function whenever you go to update the flights in any way in the
     * DB.
     * Ensures the lists stay up to date with the current DB
     */
    // public static void updateFlights() {
    // flightList.clear(); // Clear the existing list

    // try {
    // Connection connection = DatabaseManager.getConnection("AIRLINE");
    // Statement statement = connection.createStatement();
    // ResultSet resultSet = statement.executeQuery("SELECT * FROM Flights");
    // while (resultSet.next()) {
    // int flightId = resultSet.getInt("flight_id");
    // String flightNumber = resultSet.getString("flight_number");
    // String departureLocation = resultSet.getString("departure_location");
    // String arrivalLocation = resultSet.getString("arrival_location");
    // LocalDateTime departureTime =
    // resultSet.getTimestamp("departure_time").toLocalDateTime();
    // LocalDateTime arrivalTime =
    // resultSet.getTimestamp("arrival_time").toLocalDateTime();
    // int aircraftId = resultSet.getInt("aircraft_id");
    // BigDecimal basePrice = resultSet.getBigDecimal("base_price");

    // // Assuming you have a Flight constructor that takes these parameters
    // Flight flight = new Flight(flightId, flightNumber, departureLocation,
    // arrivalLocation, departureTime, arrivalTime, aircraftId, basePrice);

    // // Add Flight object to the list
    // flightList.add(flight);
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }

    public static void updateSeats() {

    }

    // Basic list getters

    public static List<Flight> getFlightList() {
        return flightList;
    }

    public static List<RegisteredUser> getRegisteredUserList() {
        return registeredUserList;
    }

    public static List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    public static List<Customer> getCustomers() {
        return customerList;
    }

    public static int getNumberOfFlights() {
        DatabaseManager.connect("AIRLINE");
        int numberOfFlights = 0;

        try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
            String query = "SELECT COUNT(*) AS numFlights FROM Flights";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        numberOfFlights = resultSet.getInt("numFlights");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return numberOfFlights;
    }
}
package ensf480.term_project.domain.Boundaries;

import ensf480.term_project.domain.Users.*;
import ensf480.term_project.domain.Flights.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PopulateFromDB {

    private static List<Flight> flightList = new ArrayList<>();
    private static List<RegisteredUser> registeredUserList = new ArrayList<>();
    private static List<Aircraft> aircraftList = new ArrayList<>();

    // Method to populate ArrayList from Users table
    // Gets all the RegisteredUsers currently in the DB table
    public static List<RegisteredUser> getRegisteredUsers() {
        List<RegisteredUser> userList = new ArrayList<>();
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
                boolean isloggedin = resultSet.getBoolean("logged_in");

                RegisteredUser user = new RegisteredUser(userId, username, password, email, isloggedin, userType);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        registeredUserList = userList;
        return registeredUserList;
    }

    // Method to populate ArrayList from Flights table
    public static List<Flight> getFlights() {
        List<Flight> flights = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Flights");
            while (resultSet.next()) {
                int flightId = resultSet.getInt("flight_id");
                String flightNumber = resultSet.getString("flight_number");
                String departureLocation = resultSet.getString("departure_location");
                String arrivalLocation = resultSet.getString("arrival_location");
                LocalDateTime departureTime = resultSet.getTimestamp("departure_time").toLocalDateTime();
                LocalDateTime arrivalTime = resultSet.getTimestamp("arrival_time").toLocalDateTime();
                int aircraftId = resultSet.getInt("aircraft_id");
                BigDecimal basePrice = resultSet.getBigDecimal("base_price");
    
                Flight flight = new Flight(flightId, flightNumber, departureLocation, arrivalLocation, departureTime, arrivalTime, aircraftId, basePrice);
    
                // Add Flight object to the list
                flights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        flightList = flights;
        return flightList;
    }

    public static List<Aircraft> getAircrafts() {
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Aircrafts");
    
            while (resultSet.next()) {
                int aircraftId = resultSet.getInt("aircraft_id");
                String aircraftName = resultSet.getString("aircraft_name");
                int numberOfSeats = resultSet.getInt("number_of_seats");
    
                Aircraft aircraft = new Aircraft(aircraftId, aircraftName, numberOfSeats);
                aircraftList.add(aircraft);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aircraftList;

    public static List<Flight> getFlightList() {
        return flightList;
    }


    }

    // Method to update ArrayList from Users table
    public static void updateRegisteredUsers() {
        registeredUserList.clear(); // Clear the existing list

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
                boolean isloggedin = resultSet.getBoolean("logged_in");

                RegisteredUser user = new RegisteredUser(userId, username, password, email, isloggedin, userType);
                registeredUserList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Call this function whenever you go to update the flights in any way in the DB. 
     * Ensures the lists stay up to date with the current DB
     */
    public static void updateFlights() {
        flightList.clear(); // Clear the existing list
    
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Flights");
            while (resultSet.next()) {
                int flightId = resultSet.getInt("flight_id");
                String flightNumber = resultSet.getString("flight_number");
                String departureLocation = resultSet.getString("departure_location");
                String arrivalLocation = resultSet.getString("arrival_location");
                LocalDateTime departureTime = resultSet.getTimestamp("departure_time").toLocalDateTime();
                LocalDateTime arrivalTime = resultSet.getTimestamp("arrival_time").toLocalDateTime();
                int aircraftId = resultSet.getInt("aircraft_id");
                BigDecimal basePrice = resultSet.getBigDecimal("base_price");
    
                // Assuming you have a Flight constructor that takes these parameters
                Flight flight = new Flight(flightId, flightNumber, departureLocation, arrivalLocation, departureTime, arrivalTime, aircraftId, basePrice);
    
                // Add Flight object to the list
                flightList.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    
}
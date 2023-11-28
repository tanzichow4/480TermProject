package ensf480.term_project.domain.Flights;

import ensf480.term_project.domain.Boundaries.*;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    
    private int flightID;
    private String flightNumber;
    private String departureLocation; 
    private String arrivalLocation;
    private String departureTime;
    private String arrivalTime;
    private String departureDate;
    private String arrivalDate;
    private int aircraftID;
    private BigDecimal basePrice;
    private List<Seat> seatList = new ArrayList<>();

    public Flight(int flightID, String flightNumber, String departureLocation, String arrivalLocation, String dTime, String aTime, String dDate, String aDate, int aircraftID, BigDecimal price) {
        this.flightID = flightID;
        this.flightNumber = flightNumber;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureTime = dTime;
        this.arrivalTime = aTime;
        this.departureDate = dDate;
        this.arrivalDate = aDate;
        this.aircraftID = aircraftID;
        this.basePrice = price;
        // Populate the seats of the flight
        populateSeats();
        // Update arraylist every time a new flight is added.
    }

    public int getFlightID() {
        return flightID;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureDate() {
        return departureTime;
    }

    public String getArrivalDate() {
        return arrivalTime;
    }

    public int getAircraftID() {
        return aircraftID;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public List<Seat> getSeats() {
        return seatList;
    }

    public void populateSeats() {
        seatList.clear(); // Clear the existing list
    
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");
            // Modify the SQL query to only fetch seats for the current flight
            String query = "SELECT * FROM Seats WHERE flight_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, flightID);
                ResultSet resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                    int seatId = resultSet.getInt("seat_id");
                    String seatRow = resultSet.getString("seat_row");
                    String seatNumber = resultSet.getString("seat_number");
                    String seatType = resultSet.getString("seat_type");
                    boolean booked = resultSet.getBoolean("booked");
                    int flightId = resultSet.getInt("flight_id");
    
                    Seat seat = new Seat(seatId, seatRow, seatNumber, seatType, booked, flightId);
                    seatList.add(seat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void saveToDatabase() {
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");

            // Prepare the SQL query for inserting a new flight
            String query = "INSERT INTO Flights (flight_number, departure_location, arrival_location, departure_time, arrival_time, aircraft_id, base_price) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                // Set parameters for the prepared statement
                preparedStatement.setString(1, flightNumber);
                preparedStatement.setString(2, departureLocation);
                preparedStatement.setString(3, arrivalLocation);
                preparedStatement.setObject(4, departureTime);
                preparedStatement.setObject(5, arrivalTime);
                preparedStatement.setObject(6, departureDate);
                preparedStatement.setObject(7, arrivalDate);
                preparedStatement.setInt(8, aircraftID);
                preparedStatement.setBigDecimal(9, basePrice);

                // Execute the insert statement
                int affectedRows = preparedStatement.executeUpdate();

                // Check if the insertion was successful
                if (affectedRows > 0) {
                    System.out.println("Flight saved to the database successfully.");

                    // Retrieve the auto-generated flight_id
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            flightID = generatedKeys.getInt(1);
                        }
                    }
                } else {
                    System.err.println("Failed to save flight to the database.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get flight by seat ID
    public static Flight getFlightBySeatID(int seatID) {
        Flight flight = null;

        try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
            String query = "SELECT * FROM Flights f " +
                           "JOIN Seats s ON f.flight_id = s.flight_id " +
                           "WHERE s.seat_id = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, seatID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int flightID = resultSet.getInt("flight_id");
                        String flightNumber = resultSet.getString("flight_number");
                        String departureLocation = resultSet.getString("departure_location");
                        String arrivalLocation = resultSet.getString("arrival_location");
                        String departureTime = resultSet.getString("departure_time");
                        String arrivalTime = resultSet.getString("arrival_time");
                        String departureDate = resultSet.getString("departure_date");
                        String arrivalDate = resultSet.getString("arrival_date");
                        int aircraftID = resultSet.getInt("aircraft_id");
                        BigDecimal basePrice = resultSet.getBigDecimal("base_price");

                        flight = new Flight(flightID, flightNumber, departureLocation, arrivalLocation,
                                departureTime, arrivalTime, departureDate, arrivalDate, aircraftID, basePrice);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flight;
    }

    public void setFlightID(int id) {
        this.flightID = id;
    }

}

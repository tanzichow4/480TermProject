package ensf480.term_project.domain.Flights;

import ensf480.term_project.domain.Boundaries.*;
import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    
    private int flightID;
    private String flightNumber;
    private String departureLocation; 
    private String arrivalLocation;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int aircraftID;
    private BigDecimal basePrice;
    private List<Seat> seatList = new ArrayList<>();

    public Flight(int flightID, String flightNumber, String departureLocation, String arrivalLocation, LocalDateTime dTime, LocalDateTime aTime, int aircraftID, BigDecimal price) {
        this.flightID = flightID;
        this.flightNumber = flightNumber;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureTime = dTime;
        this.arrivalTime = aTime;
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

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
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

    private void populateSeats() {
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
                preparedStatement.setInt(6, aircraftID);
                preparedStatement.setBigDecimal(7, basePrice);

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

    
}

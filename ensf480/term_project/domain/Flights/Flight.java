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
        populateSeats();
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
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Seats");
    
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
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   
}

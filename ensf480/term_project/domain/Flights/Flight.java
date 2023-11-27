package ensf480.term_project.domain.Flights;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Flight {
    
    private int flightID;
    private String flightNumber;
    private String departureLocation; 
    private String arrivalLocation;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int aircraftID;
    private BigDecimal basePrice;

    public Flight(int flightID, String flightNumber, String departureLocation, String arrivalLocation, LocalDateTime dTime, LocalDateTime aTime, int aircraftID, BigDecimal price) {
        this.flightID = flightID;
        this.flightNumber = flightNumber;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureTime = dTime;
        this.arrivalTime = aTime;
        this.aircraftID = aircraftID;
        this.basePrice = price;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    

}

package ensf480.term_project.domain.Flights;

public class Seat {
    private int seatId;
    private String seatRow;
    private String seatNumber;
    private String seatType;
    private int flightId;
    private boolean booked;

    // Constructor
    public Seat(int seatId, String seatRow, String seatNumber, String seatType, boolean booked, int flightId) {
        this.seatId = seatId;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.flightId = flightId;
        this.booked = booked;
    }

    // Getters
    public int getSeatId() {
        return seatId;
    }

    public String getSeatRow() {
        return seatRow;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getSeatType() {
        return seatType;
    }

    public int getFlightId() {
        return flightId;
    }

    public boolean getSeatStatus() {
        return booked;
    }
}

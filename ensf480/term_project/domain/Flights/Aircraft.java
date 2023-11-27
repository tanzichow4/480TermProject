package ensf480.term_project.domain.Flights;

public class Aircraft {

    private int aircraftID;
    private String aircraftName;
    private int numberOfSeats;

    public Aircraft(int aircraftID, String aircraftName, int numberOfSeats) {
        this.aircraftID = aircraftID;
        this.aircraftName = aircraftName;
        this.numberOfSeats = numberOfSeats;
    }
}

package ensf480.term_project.domain.Flights;

public class Aircraft {

    private int aircraftID;
    private String aircraftName;

    public Aircraft(int aircraftID, String aircraftName) {
        this.aircraftID = aircraftID;
        this.aircraftName = aircraftName;
    }

    public int getAircraftID() {
        return aircraftID;
    }

    public String getAircraftName() {
        return aircraftName;
    }
}

package ensf480.term_project.domain.Flights;

import ensf480.term_project.domain.Users.*;

public class AddAircraftCommand implements AdminCommand {
    private String aircraftName;
    private SystemAdmin systemAdmin;

    public AddAircraftCommand(SystemAdmin systemAdmin, String aircraftName) {
        this.systemAdmin = systemAdmin;
        this.aircraftName = aircraftName;
    }

    @Override
    public void execute() {
        systemAdmin.insertAircraft(aircraftName);
    }
}

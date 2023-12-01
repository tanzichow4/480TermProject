package ensf480.term_project.domain.AdminStrategies;

import ensf480.term_project.domain.Flights.Flight;
import ensf480.term_project.domain.GUIPages.LoginGUI;
import ensf480.term_project.domain.Users.*;

public class AddFlightCommand implements AdminCommand {
    private Flight flight;

    public AddFlightCommand(Flight flight) {
        this.flight = flight;
    }

    @Override
    public void execute() {

        SystemAdmin systemAdmin = LoginGUI.getLoggedInAdmin();
        systemAdmin.addFlight(flight);
    }
}

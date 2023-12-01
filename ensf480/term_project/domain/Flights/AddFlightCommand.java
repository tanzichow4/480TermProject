package ensf480.term_project.domain.Flights;

import ensf480.term_project.domain.Users.*;

public class AddFlightCommand implements AdminCommand {
    private Flight flight;

    public AddFlightCommand(Flight flight) {
        this.flight = flight;
    }

    @Override
    public void execute() {

        SystemAdmin systemAdmin = Login.getLoggedInAdmin();
        systemAdmin.addFlight(flight);
    }
}

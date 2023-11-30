package ensf480.term_project.domain.Users;

public class FlightAttendant extends RegisteredUser {

    public FlightAttendant(int userID, String username, String password, String email, boolean isloggedin) {
        super(userID, username, password, email, isloggedin, 2); // Set user type to 3 for Flight Attendant
    }

}

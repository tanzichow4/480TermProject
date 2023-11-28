package ensf480.term_project.domain.Users;

public class AirlineAgent extends RegisteredUser{

    public AirlineAgent(int userID, String username, String password, String email, boolean isloggedin) {
        super(userID, username, password, email, isloggedin, 2); // Set user type to 1 for System Admin
    }





}

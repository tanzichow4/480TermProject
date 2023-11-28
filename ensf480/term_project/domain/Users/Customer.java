package ensf480.term_project.domain.Users;

public class Customer extends RegisteredUser {

    public Customer(int userID, String username, String password, String email, boolean isloggedin) {
        super(userID, username, password, email, isloggedin, 0); // Set user type to 0 for Customer
    }

    public Customer() {
        super();
    }
}

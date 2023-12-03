package ensf480.term_project.domain.Singleton;

import ensf480.term_project.domain.Users.*;

public class Signup {

    // The single instance of the class
    private static Signup instance;
    private Customer user;

    // Private constructor to prevent instantiation outside of the class
    private Signup() {
        // Create a new registered user instance
        user = new Customer();
    }

    public void sign_up(String username, String password, String email) {
        // Set user details
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        // Save user information to the database
        user.saveUserInfoToDB();

        // Set the user as logged in after successful signup
        user.setLoggedIN(true);
    }

    // Public method to get the single instance of the class
    public static Signup getInstance() {
        if (instance == null) {
            synchronized (Signup.class) {
                if (instance == null) {
                    instance = new Signup();
                }
            }
        }
        return instance;
    }

    public boolean login(String enteredUsername, String enteredPassword) {
        // Fetch user information from the database using the provided username
        user.fetchUserInfoFromDB(enteredUsername);

        // Check if the entered credentials match the stored credentials
        if (enteredUsername.equals(user.getUsername()) && enteredPassword.equals(user.getPassword())) {
            user.setLoggedIN(true);
            return true;
        } else {
            return false;
        }
    }

    public void logout() {
        user.setLoggedIN(false); // Set the user as not logged in
    }

}

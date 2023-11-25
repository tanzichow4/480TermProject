package ensf480.term_project.domain.Singleton;
import ensf480.term_project.domain.Users.RegisteredUser;


public class Login {

    // The single instance of the class
    private static Login instance;
    private String username;
    private String password;
    private RegisteredUser user;

    // Private constructor to prevent instantiation outside of the class
    private Login() {
        // Create new registered user
        user = new RegisteredUser();
       
    }

    // Public method to get the single instance of the class
    public static Login getInstance() {
        if (instance == null) {
            synchronized (Login.class) {
                if (instance == null) {
                    instance = new Login();
                }
            }
        }
        return instance;
    }

    // Setters for username and password
    public void setUsername(String username) {
        this.username = username;
        user.setUsername(this.username);

    }

    public void setPassword(String password) {
        this.password = password;
        user.setPassword(this.password);

    }    
    // public void login() {
    //     // Implement login logic
    //     System.out.println("Logging in as " + username);
    // }

    // public void logout() {
    //     // Implement logout logic
    //     System.out.println("Logging out");
    // }

}


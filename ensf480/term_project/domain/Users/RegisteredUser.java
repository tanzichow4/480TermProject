/**
 * Class RegisteredUser
 * Holds the information for every user registered website. Stored in AirlineDB
 * 
 */


package ensf480.term_project.domain.Users; 

import ensf480.term_project.domain.Boundaries.*;

import java.sql.*;

public class RegisteredUser {

    private int userID;
    private String username;
    private String password;
    private String email;
    private boolean isloggedin;
    private int userType; // 0 = Customer, 1 = System Admin, 1 = Flight Attendant, 2 = Airline Agent
    
    public RegisteredUser() {

    }
    
    public RegisteredUser(int userID, String username, String password, String email, boolean isloggedin, int userType) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isloggedin = isloggedin;
        this.userType = userType;
    }

    // Getter and setter methods
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public boolean getLoggedIN() {
        return isloggedin;
    }

    public void setLoggedIN(boolean value) {
        isloggedin = value;
    }

    // Method to retrieve user information from the database
    public void fetchUserInfoFromDB(String username) {
        try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
            String query = "SELECT * FROM Users WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        this.userID = resultSet.getInt("user_id");
                        this.username = resultSet.getString("username");
                        this.password = resultSet.getString("pass");
                        this.email = resultSet.getString("email");
                        this.userType = resultSet.getInt("user_type");
                        this.isloggedin = resultSet.getBoolean("logged_in");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to save user information to the database
    public void saveUserInfoToDB() {
        try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
            String query = "INSERT INTO RegisteredUsers (username, pass, email, is_member) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, this.username);
                preparedStatement.setString(2, this.password);
                preparedStatement.setString(3, this.email);
                preparedStatement.setInt(1, this.userType);
                preparedStatement.setBoolean(5, this.isloggedin);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PopulateFromDB.updateUsers();
    }
}

package ensf480.term_project.domain.Users; 

import java.sql.*;

import ensf480.term_project.domain.Controllers.*;

public class RegisteredUser {

    private int userID;
    private String username;
    private String password;
    private String email;
    private boolean isloggedin;
    private int userType; // 0 = Customer, 1 = System Admin, 2 = Flight Attendant, 3 = Airline Agent
    
    public RegisteredUser(int userID, String username, String password, String email, boolean isloggedin, int userType) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isloggedin = isloggedin;
        this.userType = userType;
    }

    public RegisteredUser() {
        
    }

    public RegisteredUser(String username, String password, String email, boolean isloggedin, int userType) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.isloggedin = isloggedin;
        this.userType = userType;
        saveUserInfoToDB();

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

    public void saveUserInfoToDB() {
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");
            String query = "INSERT INTO RegisteredUsers (username, pass, email, user_type, is_logged_in) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, this.username);
                preparedStatement.setString(2, this.password);
                preparedStatement.setString(3, this.email);
                preparedStatement.setInt(4, this.userType);
                preparedStatement.setBoolean(5, this.isloggedin);
    
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }
    
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.userID = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

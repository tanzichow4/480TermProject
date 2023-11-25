/**
 * Class RegisteredUser
 * Holds the information for every user registered website. Stored in AirlineDB
 * 
 */

package ensf480.term_project.domain.Users; 

import java.sql.*;

public class RegisteredUser {

    private int userID;
    private String username;
    private String password;
    private String email;
    private boolean isMember;

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/AIRLINE";
    private static final String USER = "your_username"; // set to your username
    private static final String PASSWORD = "your_password"; // set to your root

    public RegisteredUser() {

    }
    
    public RegisteredUser(String username, String password, String email, boolean isMember) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isMember = isMember;
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

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    // Method to retrieve user information from the database
    public void fetchUserInfoFromDB(int userID) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM Users WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        this.userID = resultSet.getInt("user_id");
                        this.username = resultSet.getString("username");
                        this.password = resultSet.getString("pass");
                        this.email = resultSet.getString("email");
                        this.isMember = resultSet.getBoolean("is_member");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to save user information to the database
    public void saveUserInfoToDB() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO Users (username, pass, email, is_member) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, this.username);
                preparedStatement.setString(2, this.password);
                preparedStatement.setString(3, this.email);
                preparedStatement.setBoolean(4, this.isMember);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

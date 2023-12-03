package ensf480.term_project.domain.Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    // Specify the path to the MySQL Connector/J JAR file
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    private static Map<String, Connection> connections = new HashMap<>();

    static {
        try {
            // Load the JDBC driver when the class is loaded
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load JDBC driver. Check the driver location and classpath.");
        }
    }

    // Method to establish a connection to a specific database
    public static void connect(String dbName) {
        try {
            if (!connections.containsKey(dbName) || connections.get(dbName).isClosed()) {
                Connection connection = DriverManager.getConnection(URL + dbName, USER, PASSWORD);
                connections.put(dbName, connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get the existing connection for a specific database
    public static Connection getConnection(String dbName) {
        return connections.get(dbName);
    }

    // Close the connection for a specific database
    public static void close(String dbName) {
        try {
            Connection connection = connections.get(dbName);
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

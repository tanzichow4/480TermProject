package ensf480.term_project.domain.Controllers;

import ensf480.term_project.domain.Boundaries.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightController {
    

    // Method to get a list of flight IDs from the Flights table
    public static List<Integer> getFlightIds() {
        List<Integer> flightIds = new ArrayList<>();

        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT flight_id FROM Flights");

            while (resultSet.next()) {
                int flightId = resultSet.getInt("flight_id");
                flightIds.add(flightId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flightIds;
    }

    


}

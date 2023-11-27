/**
 * TO RUN THIS MAIN CLASS
 * command: java -cp .:lib/mysql-connector-java-8.0.23.jar ensf480.term_project.domain.main
 * ** delete your class files **
 */

package ensf480.term_project.domain;

import ensf480.term_project.domain.Boundaries.*;
import ensf480.term_project.domain.Flights.*;
import ensf480.term_project.domain.Payments.*;
import ensf480.term_project.domain.Singleton.*;
import ensf480.term_project.domain.Users.*;
import ensf480.term_project.domain.Views.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class main {

    public static void main(String[] args) {
        // Connect to AIRLINE database
        DatabaseManager.connect("AIRLINE");
        Connection airlineConnection = DatabaseManager.getConnection("AIRLINE");
        
        List<Flight> flights = PopulateFromDB.getFlights();

        for (Flight element : flights) {
            System.out.println(element.getFlightNumber());
        }

        
        // Connect to BILLING database
        DatabaseManager.connect("BILLING");
        Connection billingConnection = DatabaseManager.getConnection("BILLING");



        // Close connections
        DatabaseManager.close("AIRLINE");
        DatabaseManager.close("BILLING");
    }
}
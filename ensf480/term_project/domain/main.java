/**
 * TO RUN THIS MAIN CLASS
 * command: java -cp .:lib/mysql-connector-java-8.0.23.jar ensf480.term_project.domain.main
 * Compile first !!!! javac ensf480/term_project/domain/main.java
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
import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class main {
    public static void main(String[] args) {
        // Connect to AIRLINE database
        DatabaseManager.connect("AIRLINE");
        Connection airlineConnection = DatabaseManager.getConnection("AIRLINE");
        
        List<Flight> flights = PopulateFromDB.setFlights();

        for (Flight element : flights) {
            System.out.println("Flight Number: " + element.getFlightNumber());

            List<Seat> seats = element.getSeats();
            for (Seat seat : seats) {
                System.out.println("Seat Row + Number in flight " + element.getFlightID() + ": " + seat.getSeatRow() + seat.getSeatNumber());
            }
        }

        // Set the components of the LocalDateTime
        int year = 2023;
        int month = 11;
        int day = 27;
        int hour = 15;
        int minute = 30;
        int second = 0;

        // Create a LocalDateTime object
        LocalDateTime dTime = LocalDateTime.of(year, month, day, hour, minute, second);

        hour = 18;
        LocalDateTime aTime = LocalDateTime.of(year, month, day, hour, minute, second);
        
        BigDecimal basePrice = new BigDecimal("150.00");
        Flight added = new Flight(3, "FL1110", "Calgary", "Vancouver", dTime, aTime, 1, basePrice);
        
        added.saveToDatabase();

        flights = PopulateFromDB.setFlights();

        for (Flight element : flights) {
            System.out.println("Flight Number: " + element.getFlightNumber());
        }

        // Connect to BILLING database
        DatabaseManager.connect("BILLING");
        Connection billingConnection = DatabaseManager.getConnection("BILLING");

        // Close connections
        DatabaseManager.close("AIRLINE");
        DatabaseManager.close("BILLING");
    }
}
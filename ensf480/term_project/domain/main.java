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

import javax.swing.*;

import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class main {
    public static void main(String[] args) {
        // Connect to AIRLINE database
        DatabaseManager.connect("AIRLINE");
        Connection airlineConnection = DatabaseManager.getConnection("AIRLINE");

        List<Flight> flights = PopulateFromDB.setFlights();
        List<RegisteredUser> registeredUsers = PopulateFromDB.setRegisteredUsers();
        for (Flight element : flights) {
            System.out.println("Flight Number: " + element.getFlightNumber());

            List<Seat> seats = element.getSeats();
            for (Seat seat : seats) {
                System.out.println("Seat Row + Number in flight " + element.getFlightID() + ": " + seat.getSeatRow()
                        + seat.getSeatNumber());
            }
        }

        // Connect to BILLING database
        DatabaseManager.connect("BILLING");
        Connection billingConnection = DatabaseManager.getConnection("BILLING");

        SwingUtilities.invokeLater(() -> MainGUI.createAndShowGUI());
        // Close connections
        DatabaseManager.close("AIRLINE");
        DatabaseManager.close("BILLING");

        // Show the GUI after connecting to databases

    }
}

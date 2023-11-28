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
import java.math.BigDecimal;

import javax.swing.*;

import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class main {
    public static void main(String[] args) {

        // Connect to AIRLINE database
        DatabaseManager.connect("AIRLINE");
        Connection airlineConnection = DatabaseManager.getConnection("AIRLINE");

        // Initialize the list of system users existing in the database
        List<RegisteredUser> users = PopulateFromDB.setRegisteredUsers();

        // Initialize the system admins in the database - this will now be updated and is only initialized once.
        List<SystemAdmin> admins = PopulateFromDB.createSystemAdmins(users);
        // Initialize the customers in the system
        List<Customer> customers = PopulateFromDB.createSystemCustomers(users);

        for (RegisteredUser user : users) {
            System.out.println("User: " + user.getUsername());
        }

        // Initialize the flights in the database
        List<Flight> flights = PopulateFromDB.setFlights();

        System.out.println("Printing the System Admins:");
        for (SystemAdmin admin : admins) {
            System.out.println("Admin name: " + admin.getUsername());
        }

        /**
         * System Admin adding a flight functionality:
         */

        BigDecimal price = new BigDecimal(60.00);

        System.out.println("Testing system admin adding a flight:");
        SystemAdmin first = admins.getFirst();
        Flight newFlight = new Flight(0, "FL19999", "California", "New York", "8:00", "12:00", "2023-11-28", "2023-11-28", 1, price);
        first.addFlight(newFlight);

        List<Aircraft> aircrafts = PopulateFromDB.setAircrafts();

        for (Aircraft craft : aircrafts) {
            System.out.println("Aircraft: "+ craft.getAircraftName());
        }

        admins.getFirst().insertAircraft("Dreamliner");


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

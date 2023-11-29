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
import ensf480.term_project.domain.Promos.Promo;
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

        DatabaseManager.connect("BILLING");
        Connection billingConnection = DatabaseManager.getConnection("BILLING");

        // Initialize the list of system users existing in the database
        List<RegisteredUser> users = PopulateFromDB.setRegisteredUsers();

        // Initialize the system admins in the database - this will now be updated and
        // is only initialized once.
        List<SystemAdmin> admins = PopulateFromDB.createSystemAdmins(users);
        // Initialize the customers in the system
        List<Customer> customers = PopulateFromDB.createSystemCustomers(users);

        List<AirlineAgent> airlineAgents = PopulateFromDB.createAirlineAgents(users);

        System.out.println("Printing the Airline Agents:");
        for (AirlineAgent admin : airlineAgents) {
            System.out.println("Admin name: " + admin.getUsername());
        }
        
        // for (RegisteredUser user : users) {
        //     System.out.println("User: " + user.getEmail());
        // }

        // Initialize the flights in the database
        List<Flight> flights = PopulateFromDB.setFlights();

        System.out.println("Printing the System Admins:");
        for (SystemAdmin admin : admins) {
            System.out.println("Admin name: " + admin.getUsername());
        }

        // /**
        // * System Admin adding a flight functionality:
        // */

        // BigDecimal price = new BigDecimal(60.00);

        // System.out.println("Testing system admin adding a flight:");
        // SystemAdmin first = admins.get(0);
        // Flight newFlight = new Flight(0, "FL19999", "California", "New York", "8:00",
        // "12:00", "2023-11-28", "2023-11-28", 1, price);
        // first.addFlight(newFlight);

        // List<Aircraft> aircrafts = PopulateFromDB.setAircrafts();

        // for (Aircraft craft : aircrafts) {
        // System.out.println("Aircraft: "+ craft.getAircraftName());
        // }


        // System admin browsing passengers:

       
        // admins.get(0).insertAircraft("Dreamliner");


        // for (Flight element : flights) {
        // System.out.println("Flight Number: " + element.getFlightNumber());

        // List<Seat> seats = element.getSeats();
        // for (Seat seat : seats) {
        // System.out.println("Seat Row + Number in flight " + element.getFlightID() +
        // ": " + seat.getSeatRow()
        // + seat.getSeatNumber());
        // }
        // }

        // testUserSignup();

        // Connect to BILLING database

        SwingUtilities.invokeLater(() -> MainGUI.createAndShowGUI());
        // Close connections

        // List<Customer> browse = admins.getFirst().browsePassengers(flights.getFirst());

        // for (Customer elem : browse) {
        //     System.out.println("Customer on flight " + flights.getFirst().getFlightNumber() + ": " + elem.getUsername());
        // }

        DatabaseManager.close("AIRLINE");
        DatabaseManager.close("BILLING");

        // Show the GUI after connecting to databases

    }
    // private static void testUserSignup() {
    // Signup signup = Signup.getInstance();

    // // Replace these with unique values for each test
    // String testUsername = "testUser";
    // String testPassword = "testPassword";
    // String testEmail = "test@email.com";

    // // Ensure the user does not exist before signing up
    // if (!signup.login(testUsername, testPassword)) {
    // System.out.println("Test Passed: User does not exist before signing up");
    // } else {
    // System.out.println("Test Failed: User exists before signing up");
    // }

    // // Sign up the user
    // signup.sign_up(testUsername, testPassword, testEmail);

    // // Ensure the user now exists and is logged in
    // if (signup.login(testUsername, testPassword)) {
    // System.out.println("Test Passed: User exists and is logged in after signup");
    // } else {
    // System.out.println("Test Failed: User does not exist or is not logged in
    // after signup");
    // }

    // if (signup.isUserLoggedIn()) {
    // System.out.println("Test Passed: User is logged in after signup");
    // } else {
    // System.out.println("Test Failed: User is not logged in after signup");
    // }

    // // Log out the user
    // signup.logout();

    // if (!signup.isUserLoggedIn()) {
    // System.out.println("Test Passed: User is not logged in after logout");
    // } else {
    // System.out.println("Test Failed: User is still logged in after logout");
    // }
    // }
}
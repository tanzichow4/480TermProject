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

public class main {

    public static void main(String[] args) {
        // Connect to AIRLINE database
        DatabaseManager.connect("AIRLINE");
        Connection airlineConnection = DatabaseManager.getConnection("AIRLINE");


        // Connect to BILLING database
        DatabaseManager.connect("BILLING");
        Connection billingConnection = DatabaseManager.getConnection("BILLING");



        // Close connections
        DatabaseManager.close("AIRLINE");
        DatabaseManager.close("BILLING");
    }
}
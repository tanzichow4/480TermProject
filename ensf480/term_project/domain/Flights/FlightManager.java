package ensf480.term_project.domain.Flights;

import ensf480.term_project.domain.Boundaries.DatabaseManager;
import ensf480.term_project.domain.Boundaries.PopulateFromDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class FlightManager extends JPanel {
    private List<Flight> flightsData;

    private JTextField fromField;
    private JTextField destField;
    private JTextField departureDateField;
    private JTextField flightNumberField;

    private JPanel flightsListPanel;

    public FlightManager() {
        setLayout(new BorderLayout());
        DatabaseManager.connect("AIRLINE");
        Connection airlineConnection = DatabaseManager.getConnection("AIRLINE");

        // Retrieve flight data from PopulateFromDB
        flightsData = PopulateFromDB.setFlights();
        System.out.println("Flights Data: " + flightsData);

        JButton addFlightButton = new JButton("Add Flight");

        addFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to be performed when the button is clicked
                System.out.println("Add Flight button clicked!");
                // Add your logic here for adding a flight
            }
        });

        // Add components to the panel
        add(addFlightButton, BorderLayout.NORTH);
    }
}

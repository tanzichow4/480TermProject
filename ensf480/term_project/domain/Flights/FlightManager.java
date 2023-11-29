package ensf480.term_project.domain.Flights;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlightManager extends JPanel{

    public FlightManager(){
        setLayout(new BorderLayout());

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
    


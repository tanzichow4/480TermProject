package ensf480.term_project.domain.Flights;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlightManager extends JPanel{

    public FlightManager(){
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton addFlightButton = new JButton("Add Flight");

        addFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Insert Add Flight Code
            }
        });

        // Add components to the panel
        add(addFlightButton, BorderLayout.NORTH);
    }
    }
    


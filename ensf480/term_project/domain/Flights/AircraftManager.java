package ensf480.term_project.domain.Flights;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AircraftManager extends JPanel{

    public AircraftManager(){
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton addFlightButton = new JButton("Add Aircraft");

        addFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Insert Add Aircraft Code
            }
        });

        // Add components to the panel
        add(addFlightButton, BorderLayout.NORTH);
    }
    }
    


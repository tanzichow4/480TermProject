package ensf480.term_project.domain.Flights;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AirlineAgentPortal extends JPanel {

    public AirlineAgentPortal() {
        // Set up the main panel
        setLayout(new BorderLayout());

        // Create components
        JPanel topBarPanel = createTopBarPanel();
        JPanel gridPanel = createGridPanel();

        // Add components to the main panel
        add(topBarPanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
    }

    private JPanel createTopBarPanel() {
        JPanel topBarPanel = new JPanel();
        topBarPanel.setBackground(Color.LIGHT_GRAY);

        // Create sign-out button
        JButton signOutButton = new JButton("Sign Out");
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform sign-out actions
                JOptionPane.showMessageDialog(null, "Signing out...");
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "login");
                // You can add more logic here as needed
            }
        });

        topBarPanel.add(signOutButton);

        return topBarPanel;
    }

    private JPanel createGridPanel() {
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create buttons for each option
        JButton bookFlightButton = new JButton("Book a Flight");
        JButton browsePassengersButton = new JButton("Browse List of Passengers");


        // Add action listeners to the buttons
        bookFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to handle "Manage Flight" action
                JOptionPane.showMessageDialog(null, "Displaying flights...");
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "browseFlight");
            }
        });

        browsePassengersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to handle "Manage Aircraft" action
                JOptionPane.showMessageDialog(null, "Browsing flights...");
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                
            }
        });

       
        // Add buttons to the grid panel
        gridPanel.add(bookFlightButton);
        gridPanel.add(browsePassengersButton);


        return gridPanel;
    }
}

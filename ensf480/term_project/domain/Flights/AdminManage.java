package ensf480.term_project.domain.Flights;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminManage extends JFrame {

    public AdminManage() {
        // Set up the main frame
        setTitle("Flight Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JPanel topBarPanel = createTopBarPanel();
        JPanel gridPanel = createGridPanel();

        // Set layout manager
        setLayout(new BorderLayout());

        // Add components to the main frame
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
        JButton manageFlightButton = new JButton("Manage Flight");
        JButton manageAircraftButton = new JButton("Manage Aircraft");
        JButton printPassengerListButton = new JButton("Print List of Passengers");
        JButton browsePassengersButton = new JButton("Browse Passengers");

        // Add action listeners to the buttons
        manageFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to handle "Manage Flight" action
                JOptionPane.showMessageDialog(null, "Managing Flight...");
            }
        });

        manageAircraftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to handle "Manage Aircraft" action
                JOptionPane.showMessageDialog(null, "Managing Aircraft...");
            }
        });

        printPassengerListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to handle "Print List of Passengers" action
                JOptionPane.showMessageDialog(null, "Saved printed list");
            }
        });

        browsePassengersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to handle "Browse Passengers" action
                JOptionPane.showMessageDialog(null, "Browsing Passengers...");
            }
        });

        // Add buttons to the grid panel
        gridPanel.add(manageFlightButton);
        gridPanel.add(manageAircraftButton);
        gridPanel.add(printPassengerListButton);
        gridPanel.add(browsePassengersButton);

        return gridPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminManage().setVisible(true);
            }
        });
    }
}


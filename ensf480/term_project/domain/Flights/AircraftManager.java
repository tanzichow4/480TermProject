package ensf480.term_project.domain.Flights;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ensf480.term_project.domain.Boundaries.PopulateFromDB;
import ensf480.term_project.domain.Users.*;

public class AircraftManager extends JPanel {

    public AircraftManager(CardLayout cardLayout, JPanel parentPanel) {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton addAircraftButton = new JButton("Add Aircraft");
        addAircraftButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set the font size
        addAircraftButton.setPreferredSize(new Dimension(150, 40)); // Set the preferred size

        // Create a "Go Back" button
        // Create and customize the "Go Back" button
        JButton goBackButton = new JButton("Go Back");
        goBackButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set the font size
        goBackButton.setPreferredSize(new Dimension(150, 40)); // Set the preferred size
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to the previous page ("adminManage")
                cardLayout.show(parentPanel, "adminManage");
            }
        });
        addAircraftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Insert Add Aircraft Code
                JFrame frame = new JFrame("Insert Aircraft Name:");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                String userInput = showTextFieldPopup(frame, "Enter Aircraft Name:");

                // Perform actions based on userInput (for example, pass it to your systemAdmin)
                if (userInput != null) {
                    SystemAdmin systemAdmin = Login.getLoggedInAdmin();
                    // systemAdmin.insertAircraft(userInput);
                    AdminCommand addAircraftCommand = new AddAircraftCommand(Login.getLoggedInAdmin(), userInput);
                        systemAdmin.executeCommand(addAircraftCommand);
                    PopulateFromDB.setAircrafts();
                }
            }
        });

        // Add components to the panel
        JPanel topBarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add some padding
        topBarPanel.add(addAircraftButton);
        topBarPanel.add(goBackButton);
        add(topBarPanel, BorderLayout.NORTH);
    }

    private String showTextFieldPopup(JFrame parent, String message) {
        JTextField textField = new JTextField();

        Object[] messageArray = {message, textField};

        int option = JOptionPane.showOptionDialog(
                parent,
                messageArray,
                "Aircraft Popup",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);

        // Check if the user clicked "OK"
        if (option == JOptionPane.OK_OPTION) {
            return textField.getText();
        } else {
            return null; // User clicked "Cancel" or closed the dialog
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Aircraft Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel parentPanel = new JPanel(new CardLayout());
        CardLayout cardLayout = (CardLayout) parentPanel.getLayout();

        AircraftManager aircraftManager = new AircraftManager(cardLayout, parentPanel);
        frame.add(aircraftManager);

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

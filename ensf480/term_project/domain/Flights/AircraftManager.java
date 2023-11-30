package ensf480.term_project.domain.Flights;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ensf480.term_project.domain.Users.*;

public class AircraftManager extends JPanel {

    public AircraftManager() {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton addAircraftButton = new JButton("Add Aircraft");
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
                    systemAdmin.insertAircraft(userInput);
                }
            }
        });

        // Add components to the panel
        add(addAircraftButton, BorderLayout.NORTH);
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

        AircraftManager aircraftManager = new AircraftManager();
        frame.add(aircraftManager);

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

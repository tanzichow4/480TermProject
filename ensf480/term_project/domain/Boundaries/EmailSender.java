package ensf480.term_project.domain.Boundaries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import ensf480.term_project.domain.Payments.Ticket;

public class EmailSender {
    public static void main(String[] args) {
        String smtpServer = "your_smtp_server";
        int smtpPort = 25; // Standard SMTP port

        String from = "your_email@example.com";
        String to = "recipient@example.com";
        String subject = "Flight Ticket Information";
        
        // Assuming you have a Ticket object with appropriate methods
        Ticket ticket = getTicketDetails();

        // Construct the email body with ticket information
        String body = "Dear passenger,\n\n"
                + "Thank you for booking your flight with us. Below are the details of your ticket:\n\n"
                + "Flight Number: " + ticket.getFlightNumber() + "\n"
                + "Departure Location: " + ticket.getDepartureLocation() + "\n"
                + "Arrival Location: " + ticket.getArrivalLocation() + "\n"
                + "Departure Time: " + ticket.getDepartureTime() + "\n"
                + "Seat Number: " + ticket.getSeatNumber() + "\n"
                + "Seat Type: " + ticket.getSeatType() + "\n"
                + "Thank you for choosing our airline. Safe travels!";

        try (Socket socket = new Socket(smtpServer, smtpPort);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             OutputStream outputStream = socket.getOutputStream()) {

            // Read the server's welcome message
            System.out.println(reader.readLine());

            // Send HELO command
            sendCommand(outputStream, "HELO " + smtpServer);

            // Send MAIL FROM command
            sendCommand(outputStream, "MAIL FROM:<" + from + ">");

            // Send RCPT TO command
            sendCommand(outputStream, "RCPT TO:<" + to + ">");

            // Send DATA command
            sendCommand(outputStream, "DATA");

            // Send email content
            sendCommand(outputStream, "From: " + from);
            sendCommand(outputStream, "To: " + to);
            sendCommand(outputStream, "Subject: " + subject);
            sendCommand(outputStream, "");
            sendCommand(outputStream, body);

            // End the email data with a period
            sendCommand(outputStream, ".");

            // Close the connection
            sendCommand(outputStream, "QUIT");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendCommand(OutputStream outputStream, String command) throws IOException {
        System.out.println("Sending: " + command);
        outputStream.write((command + "\r\n").getBytes());
        outputStream.flush();
    }

    // Replace this with your actual method to get ticket details
    private static Ticket getTicketDetails() {
        // Implement your logic to retrieve ticket details
        // This could involve querying the database or using some other source
        // Return a Ticket object with the necessary details
        return new Ticket(/* provide necessary parameters */);
    }
}

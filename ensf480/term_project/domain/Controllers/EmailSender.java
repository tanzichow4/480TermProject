package ensf480.term_project.domain.Controllers;

import ensf480.term_project.domain.Flights.Flight;
import ensf480.term_project.domain.Flights.Seat;
import ensf480.term_project.domain.Promos.Promo;
import ensf480.term_project.domain.Boundaries.DatabaseManager;
import ensf480.term_project.domain.Boundaries.FlightSeatTicketGets;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.List;
import java.util.Properties;

public class EmailSender {

    // Method to send an email/notification about a ticket purchase
    public static void sendPurchaseConfirmationEmail(String userEmail, int flightID, int seatID) {
        // Retrieve flight and seat details using FlightSeatTicketGets class
        Flight flight = FlightSeatTicketGets.getFlightDetails(flightID);
        Seat seat = FlightSeatTicketGets.getSeatDetails(seatID);

        // Set up properties for the email server
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your email server host
        properties.put("mail.smtp.port", "465"); // Replace with your email server port
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");

        // Set up the email session
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("airlinecompany23@gmail.com", "lzib kwuj equd bofd"); // Replace with
                                                                                                        // your email
                                                                                                        // credentials
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress("airlinecompany23@gmail.com")); // Replace with your email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));

            // Set the email subject and content
            message.setSubject("Ticket Purchase Confirmation");

            // Build the email content with flight and seat details
            String emailContent = buildEmailContent(flight, seat);

            message.setText(emailContent);

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // Add this method to your EmailSender class
    private static String buildEmailContent(Flight flight, Seat seat) {
        StringBuilder content = new StringBuilder();

        content.append("Dear Passenger,\n\n");
        content.append("Thank you for your ticket purchase. Below are the details of your flight:\n\n");

        content.append("Flight Details:\n");
        content.append("Flight Number: ").append(flight.getFlightNumber()).append("\n");
        content.append("Departure Location: ").append(flight.getDepartureLocation()).append("\n");
        content.append("Arrival Location: ").append(flight.getArrivalLocation()).append("\n");
        content.append("Departure Time: ").append(flight.getDepartureTime()).append("\n");
        content.append("Arrival Time: ").append(flight.getArrivalTime()).append("\n");

        content.append("\nSeat Details:\n");
        content.append("Seat Row: ").append(seat.getSeatRow()).append("\n");
        content.append("Seat Number: ").append(seat.getSeatNumber()).append("\n");
        content.append("Seat Type: ").append(seat.getSeatType()).append("\n");

        content.append("\nTotal Price: ").append(seat.getPaymentAmount()).append("\n\n");

        content.append("Thank you for choosing our airline. Have a safe journey!\n\n");
        content.append("Best regards,\nThe Airline Team");

        return content.toString();
    }

    public static void sendPromoCodeEmail(String userEmail, List<Promo> promoList) {
        // Set up properties for the email server
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your email server host
        properties.put("mail.smtp.port", "465"); // Replace with your email server port
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");

        // Set up the email session
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("airlinecompany23@gmail.com", "lzib kwuj equd bofd"); // Replace with
                                                                                                        // your email
                                                                                                        // credentials
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress("airlinecompany23@gmail.com")); // Replace with your email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));

            // Set the email subject and content
            message.setSubject("Promo Code Notification");

            // Build the email content with promo code details
            String emailContent = buildPromoCodeEmailContent(promoList);

            message.setText(emailContent);

            // Send the email
            Transport.send(message);

            System.out.println("Promo code email sent successfully.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static String buildPromoCodeEmailContent(List<Promo> promoList) {
        StringBuilder content = new StringBuilder();

        content.append("Dear Passenger,\n\n");
        content.append("Congratulations! You have received promo codes:\n\n");

        // Loop through the promo list and add each promo code to the email content
        for (Promo promo : promoList) {
            content.append("Promo Name: ").append(promo.getPromoName()).append("\n");
            content.append("Promo Code: ").append(promo.getPromoCode()).append("\n");
            content.append("Discount Amount: ").append(promo.getDiscountAmount()).append("\n\n");
        }

        content.append("These codes can be used to avail discounts on your future bookings.\n\n");
        content.append("Thank you for choosing our airline. Enjoy your journey!\n\n");
        content.append("Best regards,\nThe Airline Team");

        return content.toString();
    }

    public static void sendCancelledFlight(String userEmail, int seat_id, int flight_id) {
        // Retrieve flight and seat details using FlightSeatTicketGets class
        DatabaseManager.connect("AIRLINE");
        Flight flight = FlightSeatTicketGets.getFlightDetails(flight_id);
        DatabaseManager.connect("AIRLINE");
        Seat seat = FlightSeatTicketGets.getSeatDetails(seat_id);

        // Set up properties for the email server
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your email server host
        properties.put("mail.smtp.port", "465"); // Replace with your email server port
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");

        // Set up the email session
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("airlinecompany23@gmail.com", "lzib kwuj equd bofd"); // Replace with
                                                                                                        // your email
                                                                                                        // credentials
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress("airlinecompany23@gmail.com")); // Replace with your email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));

            // Set the email subject and content
            message.setSubject("Ticket Purchase Cancellation Confirmation");

            // Build the email content with flight and seat details
            String emailContent = buildCancellationContent(flight, seat);

            message.setText(emailContent);

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static String buildCancellationContent(Flight flight, Seat seat) {
        StringBuilder content = new StringBuilder();

        content.append("Dear Passenger,\n\n");
        content.append("Your following flight has been cancelled:\n\n");

        content.append("Flight Details:\n");
        content.append("Flight Number: ").append(flight.getFlightNumber()).append("\n");
        content.append("Departure Location: ").append(flight.getDepartureLocation()).append("\n");
        content.append("Arrival Location: ").append(flight.getArrivalLocation()).append("\n");
        content.append("Departure Time: ").append(flight.getDepartureTime()).append("\n");
        content.append("Arrival Time: ").append(flight.getArrivalTime()).append("\n");

        content.append("\nSeat Details:\n");
        content.append("Seat Row: ").append(seat.getSeatRow()).append("\n");
        content.append("Seat Number: ").append(seat.getSeatNumber()).append("\n");
        content.append("Seat Type: ").append(seat.getSeatType()).append("\n");

        content.append("\nTotal Refund Price: ").append(seat.getPaymentAmount()).append("\n\n");

        content.append("We hope to see you book with our Airline again... :(!\n\n");
        content.append("Best regards,\nThe Airline Team");

        return content.toString();
    }

}

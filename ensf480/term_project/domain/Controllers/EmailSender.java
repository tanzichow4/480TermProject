package ensf480.term_project.domain.Controllers;

import ensf480.term_project.domain.Flights.Flight;
import ensf480.term_project.domain.Flights.Seat;
import ensf480.term_project.domain.Boundaries.FlightSeatTicketGets;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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

    public static void sendPromoCodeEmail(String userEmail, String promoCode) {
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
            String emailContent = buildPromoCodeEmailContent(promoCode);

            message.setText(emailContent);

            // Send the email
            Transport.send(message);

            System.out.println("Promo code email sent successfully.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static String buildPromoCodeEmailContent(String promoCode) {
        StringBuilder content = new StringBuilder();

        content.append("Dear Passenger,\n\n");
        content.append("Congratulations! You have received a promo code.\n\n");
        content.append("Promo Code: ").append(promoCode).append("\n");
        content.append("This code can be used to avail discounts on your future bookings.\n\n");
        content.append("Thank you for choosing our airline. Enjoy your journey!\n\n");
        content.append("Best regards,\nThe Airline Team");

        return content.toString();
    }

}

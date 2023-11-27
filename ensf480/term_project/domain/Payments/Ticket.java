package ensf480.term_project.domain.Payments;

public class Ticket {

    private int ticketID;
    private double seatPrice;
    private int userID;
    private int seatID;
    private int flightID;

    public Ticket(int ticketID, double seatPrice, int userID, int seatID, int flightID) {
        this.ticketID = ticketID;
        this.seatPrice = seatPrice;
        this.userID = userID;
        this.seatID = seatID;
        this.flightID = flightID;
    }

    public int getTicketID() {
        return ticketID;
    }

    public double getSeatPrice() {
        return seatPrice;
    }

    public int getUserID() {
        return userID;
    }

    public int getSeatID() {
        return seatID;
    }

    public int getFlightID() {
        return flightID;
    }
}

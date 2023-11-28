package ensf480.term_project.domain.Payments;

import java.time.LocalDate;

public class PaymentCard {
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;

    public PaymentCard(String cardNumber, String cardHolderName, String expirationDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public static PaymentCard fillPaymentCardForm(String cardNumber, String cardHolderName, String expirationDate, String cvv) {
        // Create a new PaymentCard object with the provided details
        return new PaymentCard(cardNumber, cardHolderName, expirationDate, cvv);
    }
}

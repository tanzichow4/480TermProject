package ensf480.term_project.domain.Promos;

public class Promo {
    private String promoName;
    private String promoCode;
    private int discountAmount;

    // Constructor
    public Promo(String promoName, String promoCode, int discountAmount) {
        this.promoName = promoName;
        this.promoCode = promoCode;
        this.discountAmount = discountAmount;
    }

    // Getter and setter for promoID

    public String getPromoName(){
        return promoName;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

}

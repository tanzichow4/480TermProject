package ensf480.term_project.domain.Promos;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ensf480.term_project.domain.Boundaries.DatabaseManager;

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

package ensf480.term_project.domain.Controllers;

import ensf480.term_project.domain.Promos.Promo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PromoDatabaseHandler {

    // Method to get all promos from the database
    public static List<Promo> getAllPromos() {
        List<Promo> promoList = new ArrayList<>();

        DatabaseManager.connect("AIRLINE");

        try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
            String sql = "SELECT * FROM Promos";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String promoName = resultSet.getString("promoName");
                        String promoCode = resultSet.getString("promo_code");
                        int discountAmount = resultSet.getInt("discount_amount");

                        Promo promo = new Promo(promoName, promoCode, discountAmount);
                        promoList.add(promo);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return promoList;
    }
    
    // Method to validate a promo code
    public static boolean isPromoCodeValid(String promoCode) {
        boolean isValid = false;

        DatabaseManager.connect("AIRLINE");
        try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
            String query = "SELECT * FROM Promos WHERE promo_code = ?";
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, promoCode);

                try (ResultSet resultSet = statement.executeQuery()) {
                    // Perform validation logic
                    // For example, check if the result set has any rows
                    isValid = resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return isValid;
    }
    public static BigDecimal getPromoAmount(String promoCode) {
        DatabaseManager.connect("AIRLINE");
        BigDecimal promoAmount = BigDecimal.ZERO;

        try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
            String query = "SELECT discount_amount FROM Promos WHERE promo_code = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, promoCode);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        promoAmount = resultSet.getBigDecimal("discount_amount");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return promoAmount;
    } 
}

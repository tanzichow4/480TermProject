package ensf480.term_project.domain.Boundaries;

import ensf480.term_project.domain.Promos.Promo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PromoDatabaseHandler {

    private static List<Promo> promoList = new ArrayList<>();

    // Method to populate ArrayList from Promos table for a specific user
    public static List<Promo> getPromosForUser(int userID) {
        List<Promo> promos = new ArrayList<>();
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");
            String query = "SELECT * FROM Promos WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userID);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String promoCode = resultSet.getString("promo_code");
                    int discountAmount = resultSet.getInt("discount_amount");
                    boolean used = resultSet.getBoolean("used");

                    Promo promo = new Promo(userID, promoCode, discountAmount, used);
                    promos.add(promo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        promoList = promos;
        return promoList;
    }

    // Method to view promo codes for a specific user
    public static void viewPromoCodes(int userID) {
        List<Promo> promos = getPromosForUser(userID);
        if (promos.isEmpty()) {
            System.out.println("No promo codes available for the user.");
        } else {
            System.out.println("Promo codes for user " + userID + ":");
            for (Promo promo : promos) {
                System.out.println("Promo Code: " + promo.getPromoCode() +
                        ", Discount Amount: " + promo.getDiscountAmount() +
                        ", Used: " + promo.isUsed());
            }
        }
    }

    public static void markPromoCodeAsUsed(int userID, String promoCode) {
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");
            String query = "UPDATE Promos SET used = true WHERE user_id = ? AND promo_code = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userID);
                preparedStatement.setString(2, promoCode);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Promo code " + promoCode + " marked as used for user " + userID + ".");
                } else {
                    System.out.println("Promo code " + promoCode + " not found for user " + userID + ".");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean isPromoCodeValid(String promoCode) {
        try {
            Connection connection = DatabaseManager.getConnection("AIRLINE");
            String query = "SELECT * FROM Promos WHERE promo_code = ? AND used = false";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, promoCode);
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next(); // If there is a result, the promo code is valid and unused
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to save the generated promo code to the database
    public static void savePromoCode(int userID, String promoCode, int discountAmount) {
        try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
            String query = "INSERT INTO Promos (user_id, promo_code, discount_amount, used) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userID);
                preparedStatement.setString(2, promoCode);
                preparedStatement.setInt(3, discountAmount);
                preparedStatement.setBoolean(4, false); // The promo code is initially not used
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

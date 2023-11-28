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

    // Method to update ArrayList from Promos table for a specific user
    public static void updatePromosForUser(int userID) {
        promoList.clear(); // Clear the existing list

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
                    promoList.add(promo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

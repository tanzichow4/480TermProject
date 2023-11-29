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
    private int userID;
    private String promoCode;
    private int discountAmount;
    private boolean used;

    // Constructor
    public Promo(int userID, String promoCode, int discountAmount, boolean used) {
        this.userID = userID;
        this.promoCode = promoCode;
        this.discountAmount = discountAmount;
        this.used = used;
    }

    // Getter and setter for promoID

    public String getPromoCode() {
        return promoCode;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public static String generatePromoCode() {
        // Implement your promo code generation logic here
        // For example, you can use a combination of letters and numbers

        // In this example, a simple code of length 8 is generated
        int codeLength = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder code = new StringBuilder();

        SecureRandom random = new SecureRandom();
        for (int i = 0; i < codeLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            code.append(characters.charAt(randomIndex));
        }

        return code.toString();
    }

    // Method to save the generated promo code to the database
    public void saveToDatabase() {
        try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
            String query = "INSERT INTO Promos (user_id, promo_code, discount_amount, used) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, this.userID);  // Assuming user_id is set in the Promo constructor
                preparedStatement.setString(2, this.promoCode);
                preparedStatement.setInt(3, this.discountAmount);
                preparedStatement.setBoolean(4, this.used);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Add a method to update the list of promos in memory

    // Method to generate a seat promo
    public static Promo generateSeatPromo(int userID) {
        String promoCode = generatePromoCode();
        int discountAmount = 20; // Specify the discount amount for seat promo
        Promo seatPromo = new Promo(userID, promoCode, discountAmount, false);
        return seatPromo;
    }

    // Method to generate a flight promo
    public static Promo generateFlightPromo(int userID) {
        String promoCode = generatePromoCode();
        int discountAmount = 10; // Specify the discount amount for flight promo
        Promo flightPromo = new Promo(userID, promoCode, discountAmount, false);
        return flightPromo;
    }

    public static List<Promo> getPromosForUser(int userID) {
            List<Promo> promoList = new ArrayList<>();

            try (Connection connection = DatabaseManager.getConnection("AIRLINE")) {
                String query = "SELECT * FROM Promos WHERE user_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, userID);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            int promoID = resultSet.getInt("promo_id"); // Assuming you have a column named "promo_id"
                            String promoCode = resultSet.getString("promo_code");
                            int discountAmount = resultSet.getInt("discount_amount");
                            boolean used = resultSet.getBoolean("used");

                            Promo promo = new Promo(userID, promoCode, discountAmount, used);
                            promoList.add(promo);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return promoList;
        }}

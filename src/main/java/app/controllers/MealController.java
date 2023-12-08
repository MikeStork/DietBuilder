package app.controllers;

import app.database.Database;
import app.model.Meal;
import app.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import app.database.Database;
import app.model.Meal;
import app.model.Product;

import java.sql.*;
import java.util.ArrayList;

public class MealController {
    String table_listing_query = "SELECT m.type AS rodzaj_posiłku, m.name AS nazwa_posiłku, p.name AS nazwa_produktu, p.carbs, p.fats, p.proteins, p.weight FROM meals m JOIN products p ON m.product_id = p.id ORDER BY m.type, m.name, p.name; ";

        public static void save(Meal meal, int productId) {
            String mealQuery = "INSERT INTO `meals` (`name`, `type`, `amount`, `product_id`, `weight`) " +
                    "VALUES (?, ?, ?, ?, ?)";
            Connection conn = Database.GetConnection();

            try (PreparedStatement mealStatement = conn.prepareStatement(mealQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                mealStatement.setString(1, meal.getName());
                mealStatement.setString(2, meal.getType());
                mealStatement.setInt(3, meal.getAmount());
                mealStatement.setInt(4, productId);
                mealStatement.setInt(5, meal.getWeight());

                int affectedRows = mealStatement.executeUpdate();
                if (affectedRows > 0) {
                    ResultSet generatedKeys = mealStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int mealId = generatedKeys.getInt(1);
                        meal.setId(mealId);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static ArrayList<Meal> list() {
            String query = "SELECT * FROM `meals` GROUP BY `name` ORDER BY `type`";
            ArrayList<Meal> meals = new ArrayList<>();
            Connection conn = Database.GetConnection();

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.execute();
                ResultSet rs = preparedStatement.getResultSet();
                while (rs.next()) {
                    meals.add(new Meal(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getInt("amount"),
                            rs.getInt("product_id"),
                            rs.getInt("weight")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return meals;
        }

        public static ArrayList<Product> productsForMeal(int mealId) {
            String query = "SELECT p.* FROM `products` p " +
                    "JOIN `meals` m ON p.id = m.product_id " +
                    "WHERE m.id = ?";
            ArrayList<Product> products = new ArrayList<>();
            Connection conn = Database.GetConnection();

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, mealId);
                preparedStatement.execute();
                ResultSet rs = preparedStatement.getResultSet();
                while (rs.next()) {
                    products.add(new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getFloat("carbs"),
                            rs.getFloat("fats"),
                            rs.getFloat("proteins"),
                            rs.getBoolean("active")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return products;
        }

        public static void delete(int mealId) {
            String query = "DELETE FROM `meals` WHERE `id` = ?";
            Connection conn = Database.GetConnection();

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, mealId);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

}


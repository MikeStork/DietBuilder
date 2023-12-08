package app.controllers;

import app.database.Database;
import app.model.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductController {
    public static ArrayList<Product> list() {
        String query = "SELECT * FROM `products`";
        ArrayList<Product> products = new ArrayList<>();
        Connection conn = Database.GetConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next()){
                if(rs.getBoolean("active")){
                    products.add(new Product(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getFloat("carbs"),
                            rs.getFloat("fats"),
                            rs.getFloat("proteins"),
                            rs.getBoolean("active")
                            ));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return products;
    }
    public static boolean save(Product product) {
        String query = "INSERT INTO `products` (`name`, `carbs`, `fats`, `proteins`, `active`) " +
                "VALUES (?, ?, ?, ?, ?)";
        Connection conn = Database.GetConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setFloat(2, product.getCarbs());
            preparedStatement.setFloat(3, product.getFats());
            preparedStatement.setFloat(4, product.getProteins());
            preparedStatement.setBoolean(5, product.isActive());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int productId = generatedKeys.getInt(1);
                    product.setId(productId);
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void delete(int productId) {
        String query = "UPDATE `products` SET `active` = 0 WHERE `id` = ?";
        Connection conn = Database.GetConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

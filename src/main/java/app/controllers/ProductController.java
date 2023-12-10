package app.controllers;

import app.database.Database;
import app.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        Database.StopConnection(conn);
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
                Database.StopConnection(conn);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Database.StopConnection(conn);
        return false;
    }

    public static boolean edit(Product product) {
        String query = "UPDATE `products` SET `name` = ?, `carbs` = ?, `fats` = ?, `proteins` = ?, `active` = ? WHERE `id` = ?";
        Connection conn = Database.GetConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setFloat(2, product.getCarbs());
            preparedStatement.setFloat(3, product.getFats());
            preparedStatement.setFloat(4, product.getProteins());
            preparedStatement.setBoolean(5, product.isActive());
            preparedStatement.setInt(6, product.getId());

            int affectedRows = preparedStatement.executeUpdate();
            Database.StopConnection(conn);
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Database.StopConnection(conn);
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
        Database.StopConnection(conn);
    }
}

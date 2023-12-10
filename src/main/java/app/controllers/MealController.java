package app.controllers;

import app.database.Database;
import app.model.Item;
import app.model.Meal;
import app.model.Product;
import app.model.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealController {
    String table_listing_query = "SELECT m.type AS rodzaj_posiłku, m.name AS nazwa_posiłku, p.name AS nazwa_produktu, p.carbs, p.fats, p.proteins, p.weight FROM meals m JOIN products p ON m.product_id = p.id ORDER BY m.type, m.name, p.name; ";

        public static boolean save(Meal meal, int productId) {
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
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
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

        public static ArrayList<Product> productsForMeal(String name) {
            String query = "SELECT m.id AS mealProdId, p.* FROM `products` p JOIN `meals` m ON p.id = m.product_id WHERE m.name = ?";
            ArrayList<Product> products = new ArrayList<>();
            Connection conn = Database.GetConnection();

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.execute();
                ResultSet rs = preparedStatement.getResultSet();
                while (rs.next()) {
                    products.add(new Product(
                            rs.getInt("mealProdId"),
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
    public static void deleteMealsByName(String mealName) {
        String query = "DELETE FROM `meals` WHERE `name` = ?";
        Connection conn = Database.GetConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, mealName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean editNameAndType(String oldName, String newName, String newType) {
        String query = "UPDATE `meals` SET `name` = ?, `type` = ? WHERE `name` = ?";
        Connection conn = Database.GetConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newType);
            preparedStatement.setString(3, oldName);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Recipe> getPDFModel(ArrayList<String> mealNames) {
        String query = "SELECT meals.type, meals.name, products.name, meals.weight, meals.amount, ROUND(products.carbs * (meals.weight / 100),2) as \"carbs\", ROUND(products.fats * (meals.weight / 100),2) as \"fats\", ROUND(products.proteins * (meals.weight / 100),2) as \"proteins\" FROM meals JOIN products ON meals.product_id = products.id";
        Connection conn = Database.GetConnection();
        List<Recipe>resultList = new ArrayList<>();
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                if (mealNames.contains(resultSet.getString("name"))) {
                    String type = resultSet.getString("type");
                    String mealName = resultSet.getString("name");
                    String productName = resultSet.getString("products.name");
                    double weight = resultSet.getDouble("weight");
                    double amount = resultSet.getDouble("amount");
                    double carbs = resultSet.getDouble("carbs");
                    double fats = resultSet.getDouble("fats");
                    double proteins = resultSet.getDouble("proteins");

                    Recipe existingRecipe = null;
                    for (Recipe recipe : resultList) {
                        if (recipe.getType().equals(type) && recipe.getName().equals(mealName)) {
                            existingRecipe = recipe;
                            break;
                        }
                    }

                    if (existingRecipe == null) {
                        Recipe newRecipe = new Recipe(type, mealName);
                        Item newItem = new Item(productName, (int) weight, (int) amount, (float) carbs, (float) fats, (float) proteins);
                        newRecipe.addItem(newItem);
                        resultList.add(newRecipe);
                    } else {
                        Item newItem = new Item(productName, (int) weight, (int) amount, (float) carbs, (float) fats, (float) proteins);
                        existingRecipe.addItem(newItem);
                    }
                }
            }

            } catch(SQLException e){
                e.printStackTrace();
            }


        return resultList;
    }

}




package app.views;

import app.controllers.MealController;
import app.controllers.ProductController;
import app.model.Meal;
import app.model.MyTableModel;
import app.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainForm extends JFrame {
    private JPanel mainPanel;
    private JTable productsTable;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JTable mealsTable;

    public MainForm() throws HeadlessException {
        populateProductsTable();
        populateMealsTable();
        setContentPane(mainPanel);
        setTitle("DietBuilder");
        setSize(600,400);
        setVisible(true);
    }
    void populateProductsTable(){
        ArrayList<Product> productList = ProductController.list();
        Object[][] data = productList.stream().map(Product -> new Object[]{
                Product.getId(),
                Product.getName(),
                Product.getCarbs(),
                Product.getFats(),
                Product.getProteins(),
                Product.getActive()
        }).toArray(Object[][]::new);
        DefaultTableModel productsModel = new MyTableModel(data, new String[]{"Id","Name","Carbs","Fats","Proteins"});

        productsTable.setModel(productsModel);
    }
    void populateMealsTable(){
        ArrayList<Meal> mealsList = MealController.list();
        Object[][] data = mealsList.stream().map(Meal -> new Object[]{
                Meal.getId(),
                Meal.getName(),
                Meal.getType()
        }).toArray(Object[][]::new);
        DefaultTableModel mealsModel = new MyTableModel(data, new String[]{"Id","Name","Type"});
        mealsTable.setModel(mealsModel);
    }

}

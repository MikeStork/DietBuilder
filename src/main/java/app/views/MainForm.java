package app.views;

import app.controllers.ProductController;
import app.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainForm extends JFrame {
    private JPanel mainPanel;
    private JTable productsTable;

    public MainForm() throws HeadlessException {
        populateProductsTable();
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
        DefaultTableModel productsModel = new DefaultTableModel(data, new String[]{"Id","Name","Carbs","Fats","Proteins"});
        productsTable.setModel(productsModel);
    }
}

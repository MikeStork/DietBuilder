package app.views;

import app.controllers.MealController;
import app.controllers.ProductController;
import app.model.Meal;
import app.model.MyTableModel;
import app.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainForm extends JFrame {
    private JPanel mainPanel;
    private JTable productsTable;
    private JButton addProdButton;
    private JButton deleteProdButton;
    private JButton editProdButton;
    private JButton addMealButton;
    private JButton ExportButton;
    private JTable mealsTable;
    private JButton deleteMealButton;
    private JButton editMealButton;
    private JScrollPane productsScrollPane;
    private JScrollPane mealsScrollPane;
    public Integer SelectedProduct;
    public Integer SelectedMeal;
    private MyTableModel MealsModel;
    private MyTableModel ProductsModel;
    public MainForm() throws HeadlessException {
        populateProductsTable();
        populateMealsTable();
        productsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mealsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setContentPane(mainPanel);
        setTitle("DietBuilder");
        setSize(600,400);
        setVisible(true);

        productsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                SelectedProduct = Integer.parseInt(productsTable.getModel().getValueAt(productsTable.getSelectedRow(), 0).toString());
                System.out.println("SelProd: "+SelectedProduct);
            }
        });
        mealsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                SelectedMeal = Integer.parseInt(mealsTable.getModel().getValueAt(mealsTable.getSelectedRow(), 0).toString());
                System.out.println("SelMeal:"+SelectedMeal);
            }
        });

        addProdButton.addActionListener(new ActionListener() {
            /**
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SwingUtilities.invokeLater(()->{
                    new AddProdDialog(ProductsModel);
                });

            }
        });
        deleteProdButton.addActionListener(new ActionListener() {
            /**
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ProductController.delete(SelectedProduct);
                populateProductsTable();
            }
        });
        deleteMealButton.addActionListener(new ActionListener() {
            /**
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MealController.delete(SelectedMeal);
                populateMealsTable();
            }
        });
    }
    public void populateProductsTable(){
        ArrayList<Product> productList = ProductController.list();
        Object[][] data = productList.stream().map(Product -> new Object[]{
                Product.getId(),
                Product.getName(),
                Product.getCarbs(),
                Product.getFats(),
                Product.getProteins(),
                Product.getActive()
        }).toArray(Object[][]::new);
        ProductsModel = new MyTableModel(data, new String[]{"Id","Name","Carbs","Fats","Proteins"});
        ProductsModel.fireTableDataChanged();
        productsTable.setModel(ProductsModel);
    }
    public void populateMealsTable(){
        ArrayList<Meal> mealsList = MealController.list();
        Object[][] data = mealsList.stream().map(Meal -> new Object[]{
                Meal.getId(),
                Meal.getName(),
                Meal.getType()
        }).toArray(Object[][]::new);
        MealsModel = new MyTableModel(data, new String[]{"Id","Name","Type"});
        MealsModel.fireTableDataChanged();
        mealsTable.setModel(MealsModel);
    }

}

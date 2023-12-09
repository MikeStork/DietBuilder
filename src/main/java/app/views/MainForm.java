package app.views;

import app.controllers.MealController;
import app.controllers.ProductController;
import app.model.Meal;
import app.model.MyTableModel;
import app.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
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
    public Integer SelectedProduct = -1;
    public Integer SelectedMeal = -1;
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
        setAlwaysOnTop(true);
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
                    AddProdDialog d = new AddProdDialog(ProductsModel);
                });


            }
        });
        deleteProdButton.addActionListener(new ActionListener() {
            /**
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (SelectedProduct == -1){
                    JOptionPane.showMessageDialog(MainForm.this, "Product not selected");
                }else {
                ProductController.delete(SelectedProduct);
                populateProductsTable();
                }
            }
        });
        deleteMealButton.addActionListener(new ActionListener() {
            /**
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (SelectedMeal == -1){
                    JOptionPane.showMessageDialog(MainForm.this, "Meal not selected", "An error occured", JOptionPane.ERROR_MESSAGE);
                }else {
                MealController.deleteMealsByName(String.valueOf(MealsModel.getValueAt(mealsTable.getSelectedRow(),1)));
                populateMealsTable();
                }
            }
        });
        addMealButton.addActionListener(new ActionListener() {
            /**
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SwingUtilities.invokeLater(()->{
                    AddMealDialog d = new AddMealDialog(MealsModel, ProductsModel);
                });
            }
        });
        editProdButton.addActionListener(new ActionListener() {
            /**
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (SelectedProduct == -1){
                    JOptionPane.showMessageDialog(MainForm.this, "Product not selected", "An error occured", JOptionPane.ERROR_MESSAGE);
                }else {
                    SwingUtilities.invokeLater(()->{
                        EditProdDialog d = new EditProdDialog(ProductsModel,productsTable.getSelectedRow());
                    });
                }
            }
        });
        editMealButton.addActionListener(new ActionListener() {
            /**
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (SelectedMeal == -1){
                    JOptionPane.showMessageDialog(MainForm.this, "Meal not selected", "An error occured", JOptionPane.ERROR_MESSAGE);
                }else {
                    ArrayList<Product> prodsForMeal = MealController.productsForMeal(String.valueOf(MealsModel.getValueAt(mealsTable.getSelectedRow(),1)));
                    Object[][] data = prodsForMeal.stream().map(Product -> new Object[]{
                            Product.getId(),
                            Product.getName(),
                            Product.getCarbs(),
                            Product.getFats(),
                            Product.getProteins(),
                            Product.getActive()
                    }).toArray(Object[][]::new);
                    MyTableModel ProdsForMealModel = new MyTableModel(data, new String[]{"Id","Name","Carbs","Fats","Proteins"});
                    SwingUtilities.invokeLater(()->{
                        EditMealDialog editMealDialog = new EditMealDialog(MealsModel, ProductsModel, ProdsForMealModel, mealsTable.getSelectedRow());
                        editMealDialog.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                populateProductsTable();
                                populateMealsTable();

                                mealsTable.setModel(MealsModel);
                                ProductsModel.fireTableDataChanged();
                                mealsTable.scrollRectToVisible(mealsTable.getCellRect(mealsTable.getSelectedRow(), 0, true));
                            }
                        });
                    });
                }
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
        SwingUtilities.invokeLater(()->{
            ProductsModel.fireTableDataChanged();
            productsTable.setModel(ProductsModel);
        });
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
        SwingUtilities.invokeLater(()->{
            MealsModel.fireTableDataChanged();
            mealsTable.setModel(MealsModel);
        });
    }

}

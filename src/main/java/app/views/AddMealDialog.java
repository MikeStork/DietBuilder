package app.views;

import app.controllers.MealController;
import app.controllers.ProductController;
import app.model.Meal;
import app.model.MyTableModel;
import app.model.Product;

import javax.swing.*;
import java.awt.event.*;

public class AddMealDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane productsScrollPane;
    private JTable productsTable;
    private JTextField WeightField;
    private JTextField AmountField;
    private JTextField NameField;
    private JTextField TypeField;
    private MyTableModel mealsModel;

    public Boolean getResult() {
        return result;
    }

    private Boolean result = false;
    private Integer selectedProduct;

    public AddMealDialog(MyTableModel MealsModel, MyTableModel ProductsModel) {
        productsTable.setModel(ProductsModel);
        mealsModel = MealsModel;
        setContentPane(contentPane);
        setAlwaysOnTop(true);
        setVisible(true);
        setModal(true);
        setSize(500, 500);
        getRootPane().setDefaultButton(buttonOK);
        productsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                selectedProduct = Integer.parseInt(productsTable.getModel().getValueAt(productsTable.getSelectedRow(), 0).toString());
                System.out.println("SelProd: "+selectedProduct);
            }
        });

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        Meal meal = new Meal(null,NameField.getText(),TypeField.getText(),Integer.parseInt(WeightField.getText()),Integer.parseInt(AmountField.getText()),selectedProduct);
        Boolean insertedSuccesfully = MealController.save(meal, meal.getProduct_id());
        if (insertedSuccesfully) {
            mealsModel.addRow(new Object[]{meal.getId(), meal.getName(),meal.getType()});
            mealsModel.fireTableDataChanged();
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}

package app.views;

import app.controllers.MealController;
import app.controllers.ValidationController;
import app.model.Meal;
import app.model.MyTableModel;

import javax.swing.*;
import java.awt.event.*;

public class AddProdToMeal extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane productsScrollPane;
    private JTextField WeightField;
    private JTextField AmountField;
    private JTable productsTable;
    private Integer selectedProduct;
    private Integer selectedRow;
    private MyTableModel mealsModel;
    private MyTableModel ProdsForMealModel;
    private MyTableModel productsModel;

    public AddProdToMeal(MyTableModel MealsModel, MyTableModel ProdsForMealModel, MyTableModel productsModel, Integer selectedRow) {
        mealsModel = MealsModel;
        this.ProdsForMealModel = ProdsForMealModel;
        this.selectedRow = selectedRow;
        this.productsModel = productsModel;
        productsTable.setModel(productsModel);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setSize(500, 520);
        setVisible(true);

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
        Meal newProductInMeal = new Meal(null, String.valueOf(mealsModel.getValueAt(selectedRow,1)),String.valueOf(mealsModel.getValueAt(selectedRow,2)), Integer.parseInt(WeightField.getText()), Integer.parseInt(AmountField.getText()), selectedProduct);
        Boolean addedSuccessfully = MealController.save(newProductInMeal, newProductInMeal.getProduct_id());
        if(addedSuccessfully){
            ProdsForMealModel.addRow(new Object[]{newProductInMeal.getId(),
                    productsModel.getValueAt(productsTable.getSelectedRow(),1),
                    productsModel.getValueAt(productsTable.getSelectedRow(),2),
                    productsModel.getValueAt(productsTable.getSelectedRow(),3),
                    productsModel.getValueAt(productsTable.getSelectedRow(),4)
            });
            ProdsForMealModel.fireTableDataChanged();
        }else{
            JOptionPane.showMessageDialog(null, "Product to meal insertion unsuccessful", "An error occured", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}

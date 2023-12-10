package app.views;

import app.controllers.MealController;
import app.controllers.ValidationController;
import app.model.MyTableModel;

import javax.swing.*;
import java.awt.event.*;

public class EditMealDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton AddToMealButton;
    private JButton DeleteFromMealButton;
    private JTable productsTable;
    private JScrollPane productsScrollPane;
    private JTextField NameField;
    private JTextField TypeField;
    private  MyTableModel mealsModel;
    private  MyTableModel productsModel;
    private  MyTableModel prodsForMealModel;
    private Integer selectedProduct = -1;
    private Integer mealId;
    private Integer selectedRow;

    public EditMealDialog(MyTableModel MealsModel, MyTableModel ProductsModel, MyTableModel  ProdsForMealModel, Integer selectedRow) {
        productsModel = ProductsModel;
        prodsForMealModel = ProdsForMealModel;
        productsTable.setModel(prodsForMealModel);
        mealsModel = MealsModel;
        this.selectedRow = selectedRow;
        NameField.setText(String.valueOf(mealsModel.getValueAt(selectedRow, 1)));
        TypeField.setText(String.valueOf(MealsModel.getValueAt(selectedRow, 2)));
        mealId = Integer.parseInt(String.valueOf(MealsModel.getValueAt(selectedRow, 0)));
        setContentPane(contentPane);
        setAlwaysOnTop(true);
        setVisible(true);
//        setModal(true);
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
        DeleteFromMealButton.addActionListener(new ActionListener() {
            /**
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MealController.delete(selectedProduct);
                ProdsForMealModel.removeRow(productsTable.getSelectedRow());
                ProdsForMealModel.fireTableDataChanged();
            }
        });
        AddToMealButton.addActionListener(new ActionListener() {
            /**
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SwingUtilities.invokeLater(()->{
                    new AddProdToMeal(MealsModel,ProdsForMealModel, ProductsModel, selectedRow);
                });
            }
        });
    }

    private void onOK() {
        setTitle("");
        if(false == (ValidationController.isString(this, NameField) && ValidationController.isString(this, TypeField))){
            setTitle("Invalid data entered");
            return;
        }
        Boolean editedSuccesfully = MealController.editNameAndType(String.valueOf(mealsModel.getValueAt(selectedRow,1)), NameField.getText(), TypeField.getText());
        if (editedSuccesfully) {
            mealsModel.removeRow(selectedRow);
            mealsModel.insertRow(selectedRow,new Object[]{mealId, NameField.getText(),TypeField.getText()});
            mealsModel.fireTableDataChanged();
            SwingUtilities.invokeLater(()->{
                mealsModel.fireTableDataChanged();
            });
        }else{
            JOptionPane.showMessageDialog(null, "Meal update unsuccessful", "An error occured", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}

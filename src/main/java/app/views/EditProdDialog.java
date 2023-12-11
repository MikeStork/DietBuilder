package app.views;

import app.controllers.ProductController;
import app.controllers.ValidationController;
import app.model.MyTableModel;
import app.model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditProdDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel idField;
    private JTextField CarbsField;
    private JTextField FatsField;
    private JTextField ProteinsField;
    private JTextField NameField;
    private JLabel infoLabel;
    private MyTableModel productsModel;
    private Integer editedRow;

    public EditProdDialog(MyTableModel ProductsModel, Integer editedRow) {
        this.productsModel = ProductsModel;
        this.editedRow = editedRow;
        idField.setText(String.valueOf(ProductsModel.getValueAt(editedRow, 0)));
        NameField.setText(String.valueOf(ProductsModel.getValueAt(editedRow, 1)));
        CarbsField.setText(String.valueOf(ProductsModel.getValueAt(editedRow, 2)));
        FatsField.setText(String.valueOf(ProductsModel.getValueAt(editedRow, 3)));
        ProteinsField.setText(String.valueOf(ProductsModel.getValueAt(editedRow, 4)));
        setContentPane(contentPane);
        setAlwaysOnTop(true);
        setVisible(true);
        setModal(true);
        setSize(500, 500);
        getRootPane().setDefaultButton(buttonOK);

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
        setTitle("");
        if (false == (ValidationController.isString(this, NameField) && ValidationController.isFloat(this, CarbsField) && ValidationController.isFloat(this, FatsField) && ValidationController.isFloat(this, ProteinsField))) {
            setTitle("Invalid data entered");
            return;
        }
        Product product = new Product(Integer.parseInt(idField.getText()), NameField.getText(), Float.parseFloat(CarbsField.getText()), Float.parseFloat(FatsField.getText()), Float.parseFloat(ProteinsField.getText()), true);
        Boolean editedSuccesfully = ProductController.edit(product);
        if (editedSuccesfully) {
            productsModel.removeRow(editedRow);
            productsModel.insertRow(editedRow, new Object[]{product.getId(), product.getName(), product.getCarbs(), product.getFats(), product.getProteins()});
            productsModel.fireTableDataChanged();
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}

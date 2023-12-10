package app.views;

import app.controllers.ProductController;
import app.controllers.ValidationController;
import app.model.MyTableModel;
import app.model.Product;

import javax.swing.*;
import java.awt.event.*;

public class AddProdDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField NameField;
    private JTextField CarbsField;
    private JTextField FatsField;
    private JTextField ProteinsField;
    private MyTableModel productsModel;

    public Boolean getResult() {
        return result;
    }

    private Boolean result = false;

    public AddProdDialog(MyTableModel ProductsModel) {
        productsModel = ProductsModel;
        setContentPane(contentPane);
        setAlwaysOnTop(true);
        setVisible(true);
        setSize(500, 500);
        getRootPane().setDefaultButton(buttonOK);
        setFocusableWindowState(true);
        setModal(true);

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
        if(false == (ValidationController.isString(this, NameField) && ValidationController.isFloat(this, CarbsField) && ValidationController.isFloat(this, FatsField) && ValidationController.isFloat(this, ProteinsField))){
            setTitle("Invalid data entered");
            return;
        }
        Product product = new Product(null, NameField.getText(), Float.parseFloat(CarbsField.getText()), Float.parseFloat(FatsField.getText()), Float.parseFloat(ProteinsField.getText()), true);
        Boolean insertedSuccesfully = ProductController.save(product);
        if (insertedSuccesfully) {
            productsModel.addRow(new Object[]{product.getId(), product.getName(), product.getCarbs(), product.getFats(), product.getProteins()});
            productsModel.fireTableDataChanged();
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
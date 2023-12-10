package app.views;

import app.controllers.MealController;
import app.model.Item;
import app.model.Meal;
import app.model.MyTableModel;
import app.model.Recipe;
import app.pdf.PDFWorker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.stream.Collectors;

public class ExportToPDFDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable ExportTable;
    private JButton AddToExport;
    private JComboBox mealsComboBox;
    private Integer selectedMeal = 0;
    private Recipe[] dataToExportTable;
    public ExportToPDFDialog() {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setAlwaysOnTop(true);
        setSize(500, 500);
        ArrayList<Meal> mealsList = MealController.list();
        var dataToComboBox = mealsList.stream().map(Meal -> Meal.getType()+" | "+Meal.getName()).toArray();
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<>(dataToComboBox);
        mealsComboBox.setModel(comboBoxModel);
        comboBoxModel.setSelectedItem(comboBoxModel.getElementAt(0));
        dataToExportTable = mealsList.stream().map(Meal -> new Recipe( Meal.getType(), Meal.getName())).toArray(Recipe[]::new);
        ExportTable.setModel(new MyTableModel(null, new String[]{"Name", "Type"}));
        setVisible(true);
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
        AddToExport.addActionListener(new ActionListener() {
            /**
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MyTableModel mtm = (MyTableModel) ExportTable.getModel();
                mtm.addRow(new Object[]{dataToExportTable[selectedMeal].getName(),dataToExportTable[selectedMeal].getType()});
                ExportTable.setModel(mtm);
                mtm.fireTableDataChanged();
            }
        });
        mealsComboBox.addActionListener(new ActionListener() {
            /**
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedMeal = mealsComboBox.getSelectedIndex();
            }
        });
    }

    private void onOK() {
        MyTableModel tableModel = (MyTableModel) ExportTable.getModel();
        ArrayList<String> MealsToExport = (ArrayList<String>) tableModel.getDataVector().stream().map((Row)->Row.get(0).toString()).collect(Collectors.toList());
        Boolean exportSuccessful = false;
        exportSuccessful = PDFWorker.exportToPDF(MealController.getPDFModel(MealsToExport));
        if(!exportSuccessful){
            JOptionPane.showMessageDialog(null, "PDF export was unsuccessful", "An error occured", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}

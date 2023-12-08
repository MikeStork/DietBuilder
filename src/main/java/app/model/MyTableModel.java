package app.model;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class MyTableModel extends DefaultTableModel {
    public MyTableModel() {
    }

    public MyTableModel(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }

    public MyTableModel(Vector<?> columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public MyTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public MyTableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
    }

    public MyTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

package cn.edu.sdu.uims.component.table;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * @author zzy
 */
class RowHeaderModel extends AbstractTableModel {

    // TableModel model;
    Object[] data;

    public RowHeaderModel(TableModel model) {
        data = new Object[model.getRowCount()];
        for (int i = 0; i < data.length; i++)
            data[i] = new Boolean(false);

    }

    public void setRelatedTableModel(TableModel model) {
        data = new Object[model.getRowCount()];
        for (int i = 0; i < data.length; i++) {
            data[i] = new Boolean(false);
        }

    }

    public int getRowCount() {
        return data.length;
        // return model.getRowCount();
    }

    public int getColumnCount() {
        return 1;
    }

    public String getColumnName(int columnIndex) {
        return "checkbox";
    }

    public void setValueAt(Object value, int row, int col) {
        if(col>0)
            return;
        data[row] = value;
        fireTableCellUpdated(row, col);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex>0)
            return null;
        return data[rowIndex];
    }

    /*
     * JTable uses this method to determine the default renderer/ editor for
     * each cell. If we didn't implement this method, then the last column would
     * contain text ("true"/"false"), rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, 0).getClass();
    }

    public boolean isCellEditable(int row, int column) {
        return true;
    }
}

package cn.edu.sdu.uims.component.table;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

public class RowHeaderTable extends JTable implements ListSelectionListener {

    UInnerTable relatedTablePanel;

    public static Color background = new Color(240, 245, 255);

    public RowHeaderTable(UInnerTable table) {
        relatedTablePanel = table;
        RowHeaderModel listModel = new RowHeaderModel(
                table.getTable().getModel());
        this.setModel(listModel);
        this.setRowHeight(table.getTable().getRowHeight());
        this.setBackground(background);
        this.setAutoscrolls(false);
        this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.addMouseListener(new JTableButtonMouseListener(this));
        //this.getSelectionModel().addListSelectionListener(this);
    }

    public void setAllChecked(Boolean bool) {
        if (this.getColumnCount() < 1)
            return;
        TableModel model = this.getModel();
        for (int i = 0; i < this.getRowCount(); i++) {
            model.setValueAt(bool, i, 0);
        }
    }

    public void setCheckedRow(int i) {
        if (this.getColumnCount() < 1)
            return;
        this.getModel().setValueAt(true, i, 0);
    }
    public int[] getCheckedRows() {
        int length = this.getRowCount();
        TableModel model = this.getModel();
        ArrayList list = new ArrayList();
        int count = 0;
        for (int i = 0; i < length; i++) {
            if ((Boolean) model.getValueAt(i, 0) == true) {
                count++;
            }
        }
        int[] index = new int[count];
        count = 0;
        for (int i = 0; i < length; i++) {
            if ((Boolean) model.getValueAt(i, 0) == true) {
                index[count++] = i;
            }
        }
        return index;
    }

    public UInnerTable getRelatedTablePanel() {
        return relatedTablePanel;
    }

    /**
     * 清楚table的�?�?
     */
    public void clearSelection() {
        super.clearSelection();
        // //////有checkbox的table///
        if (this.getColumnCount() == 1)
            this.setAllChecked(false);
    }
    
    void clearColor(){
        super.clearSelection();
    }
    
    public void valueChanged(ListSelectionEvent e) {
        /*relatedTablePanel.clearSelection();

        int[] rowIndex = this.getSelectedRows();

        relatedTablePanel.setSelectedRows(rowIndex);

        // //////有checkbox的table///
        if (this.getColumnCount() == 2)

            for (int i = 0; i < rowIndex.length; i++)
                this.setValueAt(true, rowIndex[i], 1);
*/
    }
}

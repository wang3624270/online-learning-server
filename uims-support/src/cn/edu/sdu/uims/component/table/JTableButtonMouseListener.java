package cn.edu.sdu.uims.component.table;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: table中相应button点击事件的Listener
 * </p>
 * 
 * @author zzy
 */
class JTableButtonMouseListener implements MouseListener {
    private RowHeaderTable table;

    private UInnerTable mytable;

    public JTableButtonMouseListener(RowHeaderTable table) {
        this.table = table;
        this.mytable = table.getRelatedTablePanel();
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
        if (table instanceof RowHeaderTable) {
            table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        // table.setCellSelectionEnabled(false);
        /*
         * int[] rowIndex = table.getSelectedRows(); //for(int i=0;i<rowIndex.length;i++)
         * //System.out.println(rowIndex[i]); if (table instanceof
         * RowHeaderTable) { if (e.isShiftDown()) {
         * mytable.setSelectionInterval(rowIndex[0], rowIndex[rowIndex.length -
         * 1]); } else if (e.isControlDown()) { for (int i = 0; i <
         * rowIndex.length; i++) mytable.setSelectedRows(rowIndex); } else {
         * //mytable.setSelectedRow(rowIndex[0]);
         * mytable.setSelectionInterval(rowIndex[0], rowIndex[rowIndex.length -
         * 1]); }
         *  }
         */
        // for (int i = 0; i < rowIndex.length; i++)
        // System.out.println(rowIndex[i]);
    }

    public void mouseReleased(MouseEvent e) {
        int[] rowIndex = table.getSelectedRows();
        // for(int i=0;i<rowIndex.length;i++)
        // System.out.println(rowIndex[i]);
        if (table instanceof RowHeaderTable) {
            if (e.isShiftDown()) {
                mytable.setSelectionInterval(rowIndex[0],
                        rowIndex[rowIndex.length - 1]);
                /*if (table.getColumnCount() == 2)
                    for (int i = 0; i < rowIndex.length; i++) {
                        Boolean b = (Boolean) table.getValueAt(rowIndex[i], 1);
                        table.setValueAt(!b, rowIndex[i], 1);
                    }*/
                table.repaint();
            } else if (e.isControlDown()) {
                // for (int i = 0; i < rowIndex.length; i++)
                mytable.setSelectedRows(rowIndex);

                /*if (table.getColumnCount() == 2)
                    for (int i = 0; i < rowIndex.length; i++) {
                        Boolean b = (Boolean) table.getValueAt(rowIndex[i], 1);
                        table.setValueAt(!b, rowIndex[i], 1);
                    }*/
                table.repaint();
            } else {
                // mytable.setSelectedRow(rowIndex[0]);
                mytable.setSelectionInterval(rowIndex[0],
                        rowIndex[rowIndex.length - 1]);
                /*
                if (table.getColumnCount() == 2)
                    for (int i = 0; i < rowIndex.length; i++) {
                        Boolean b = (Boolean) table.getValueAt(rowIndex[i], 1);
                        table.setValueAt(!b, rowIndex[i], 1);
                    }*/
                table.repaint();
            }

        }
    }
}
package cn.edu.sdu.uims.component.table;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.table.AbstractTableModel;

import cn.edu.sdu.uims.component.tree.UTreeNodeMenu;
import cn.edu.sdu.uims.constant.UimsConstants;

public class AuthorizationTableModel extends AbstractTableModel {

    // Names of the columns.
    static protected String[] columnNames = { "Menu", "可读可写", "只读" };

    // Types of the columns.
    static protected Class[] cTypes = { String.class, Boolean.class,
            Boolean.class, Boolean.class };

    Object[][] data;

    ArrayList objList = new ArrayList();

    public AuthorizationTableModel(UTreeNodeMenu root) {
        dfsParseTreeToObjList(root);

        data = new Object[objList.size()][getColumnCount()];

        updateTableWithObjList();
    }

    public void updateTableWithObjList() {
        for (int i = 0; i < data.length; i++) {
            UTreeNodeMenu nod = (UTreeNodeMenu) objList.get(i);

            String pre = "";
            int level = nod.getLevel();
            if (level != 0) {
                for (int j = 0; j < level; j++)
                    pre += "    ";
                if (nod.isLeaf())
                    pre += "|_";
            }

            data[i][0] = pre + nod.getName();
            data[i][1] = ((UimsConstants.AuthFlag_CanWrite).equals(nod
                    .getMenuInfo().menuRight));
            data[i][2] = ((UimsConstants.AuthFlag_ReadOnly).equals(nod
                    .getMenuInfo().menuRight));
            // data[i][3] = (nod.getMenuInfo().menuRight == null);
        }
    }

    private void dfsParseTreeToObjList(UTreeNodeMenu node) {
        if (node.getChildCount() == 0) {
            objList.add(node);
            int level = node.getLevel();
            /*String str = "";
            for (int i = 0; i < level; i++)
                str += "  ";
            str += "|_";
            System.out.println(str + node.getName());*/
            return;
        } else {
            objList.add(node);
            int level = node.getLevel();
            /*String str = "";
            for (int i = 0; i < level; i++)
                str += "  ";
            str += "|_";
            System.out.println(str + node.getName());*/

            Enumeration enu = node.children();
            while (enu.hasMoreElements()) {
                Object obj = enu.nextElement();
                dfsParseTreeToObjList((UTreeNodeMenu) obj);
            }
        }
    }

    public Object getObject(int index) {
        return objList.get(index);
    }

    public int getColumnCount() {
        if (columnNames == null) {
            return 0;
        } else {
            return columnNames.length;
        }
    }

    public int getRowCount() {
        if (data == null) {
            return 0;
        } else {
            return data.length;
        }
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        if (row < getRowCount() && col < getColumnCount()) {
            return data[row][col];
        } else {
            return null;
        }
    }

    public Class getColumnClass(int c) {
        if (getValueAt(0, c) != null) {
            return getValueAt(0, c).getClass();
        } else {
            return "".getClass();
        }
    }

    public boolean isCellEditable(int row, int col) {
        return true;
    }

    /*
     * Don't need to implement this method unless your table's data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

}

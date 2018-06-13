package cn.edu.sdu.uims.component.table;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import cn.edu.sdu.uims.component.tree.UTreeNodeMenu;
import cn.edu.sdu.uims.constant.UimsConstants;


@SuppressWarnings("serial")
public class AuthorizationTable extends JTable {


    public AuthorizationTable(AuthorizationTableModel model) {
        super(model);
        setColumnWidth();
        this.addMouseListener(new MyMouseListener());
        this.setCellSelectionEnabled(true);
    }

    public boolean isCellEditable(int row, int col) {
        if (col == 0)
            return false;
        else
            return true;
    }

    public void setColumnWidth() {
        // 得到列的样式
        TableColumnModel colModel = this.getColumnModel();
        // 得到pColumn列然后设置它的最佳宽度
        colModel.getColumn(0).setPreferredWidth(300);
        colModel.getColumn(1).setPreferredWidth(100);
        colModel.getColumn(2).setPreferredWidth(100);
        // colModel.getColumn(3).setPreferredWidth(100);
    }
    class MyMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            Object obj = e.getSource();
            if (!(obj instanceof AuthorizationTable))
                return;
            AuthorizationTable table = (AuthorizationTable) obj;
            int col = table.getSelectedColumn();
            if (col == 0)
                return;

            int row = table.getSelectedRow();
            boolean bool1 = false;
            boolean bool2 = false;

            if (col == 1) {
                bool1 = !(Boolean) table.getValueAt(row, 1);
                bool2 = (Boolean) table.getValueAt(row, 2);
            } else if (col == 2) {
                bool1 = (Boolean) table.getValueAt(row, 1);
                bool2 = !(Boolean) table.getValueAt(row, 2);
            }

            UTreeNodeMenu node = (UTreeNodeMenu) ((AuthorizationTableModel) table
                    .getModel()).getObject(row);
            if (col == 1 && bool1 == true)
                node.getMenuInfo().menuRight = UimsConstants.AuthFlag_CanWrite;
            else if (col == 2 && bool2 == true)
                node.getMenuInfo().menuRight = UimsConstants.AuthFlag_ReadOnly;

            if ((bool1 == false) && (bool2 == false))
                node.getMenuInfo().menuRight = null;

            setChildrenRight(node, node.getMenuInfo().menuRight);
            setParentsRight(node, node.getMenuInfo().menuRight);

            ((AuthorizationTableModel) table.getModel())
                    .updateTableWithObjList();
            // this.updateUI();
            table.repaint();
        }

        private void setChildrenRight(UTreeNodeMenu node, String right) {
            if (node.getChildCount() == 0) {
                node.getMenuInfo().menuRight = right;
                return;
            } else {
                Enumeration enu = node.children();
                while (enu.hasMoreElements()) {
                    UTreeNodeMenu nod = (UTreeNodeMenu) enu.nextElement();
                    nod.getMenuInfo().menuRight = right;
                    setChildrenRight(nod, right);
                }
            }
        }

        private void setParentsRight(UTreeNodeMenu node, String right) {
            if (right == null) {
            	   if (node.isRoot()) {
                       node.getMenuInfo().menuRight = right;
                       return;
                   } else {
                       node.getMenuInfo().menuRight = right;

                       UTreeNodeMenu parentNode = (UTreeNodeMenu) node.getParent();
                       int allChildrenRight = 0;
                       Enumeration enu = parentNode.children();
                       while (enu.hasMoreElements()) {
                           UTreeNodeMenu nod = (UTreeNodeMenu) enu.nextElement();
                           if (nod.getMenuInfo().menuRight != null){
                               return;
                           }else {
                               allChildrenRight++;
                           }
                       }
                       if (allChildrenRight == parentNode.getChildCount())
                           parentNode.getMenuInfo().menuRight = right;
                       setParentsRight(parentNode, right);
                   }
            } else if (UimsConstants.AuthFlag_ReadOnly.equals(right)) {
                if (node.isRoot()) {
                    node.getMenuInfo().menuRight = right;
                    return;
                } else {
                    node.getMenuInfo().menuRight = right;

                    UTreeNodeMenu parentNode = (UTreeNodeMenu) node.getParent();
                    int allChildrenRight = 0;
                    Enumeration enu = parentNode.children();
                    while (enu.hasMoreElements()) {
                        UTreeNodeMenu nod = (UTreeNodeMenu) enu.nextElement();
                        if (nod.getMenuInfo().menuRight == null)
                            return;
                        if (UimsConstants.AuthFlag_ReadOnly.equals(nod
                                .getMenuInfo().menuRight))
                            allChildrenRight++;
                    }
                    if (allChildrenRight == parentNode.getChildCount())
                        parentNode.getMenuInfo().menuRight = right;
                    setParentsRight(parentNode, right);
                }
            } else if (UimsConstants.AuthFlag_CanWrite.equals(right)) {
                if (node.isRoot()) {
                    //node.getMenuInfo().menuRight = right;
                    return;
                } else {
                    UTreeNodeMenu parentNode = (UTreeNodeMenu) node.getParent();
                    node.getMenuInfo().menuRight = right;
                    setParentsRight(parentNode, right);
                }
            }
        }
    }
    
    public List getMenuInfoList() {
    	AuthorizationTableModel model = (AuthorizationTableModel) this.getModel();
    	List menulist = new ArrayList();
    	
    	UTreeNodeMenu node = null;
    	for (int i = 0; i < model.getRowCount(); i++) {
    		node = (UTreeNodeMenu) model.getObject(i);
    		
    		menulist.add(node.getMenuInfo());
    	}
    	
    	return menulist;
    }
}

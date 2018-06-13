package cn.edu.sdu.uims.component.table;

import javax.swing.JTable;

public interface ChangeRenderRule {
	   /**
     * 自己写一个比较，如果返回true，则当前单元置成红色
     * e.g.
     * public boolean ruleToChangeColor(JTable table,int row,int col){
        Object o = table.getValueAt(row, col);
        if (o instanceof Number ) {
            if(((Double)o).doubleValue()<60.0){
                return true;
            }
        }
        return false;
    } 
     * @return
     */
    public boolean ruleToChangeRender(JTable table,int row,int col);

}

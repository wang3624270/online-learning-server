package cn.edu.sdu.uims.component.table.rowrender;

import javax.swing.JTable;

/**
 * 变色规则接口，
 * 可以自己写一个比较ruleToChangeColor，如果返回true，则当前单元置成红色
 * @author zzy
 */
public interface RowSpecificRenderI {
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
    public boolean isColorSpecific(JTable table,int row,int col, Object data);
    public boolean isBackgroundColorSpecific(JTable table,int row,int col, Object data);
    public boolean isEditSpecific(JTable table,int row,int col, Object data);
    public boolean isFontSpecific(JTable table,int row,int col, Object data);
    public String  getCellColorName(JTable table,int row,int col, Object data);
    public String  getCellFontName(JTable table,int row,int col, Object data);
    public String  getCellBackgroundCodeName(JTable table,int row,int col, Object data);
    
}

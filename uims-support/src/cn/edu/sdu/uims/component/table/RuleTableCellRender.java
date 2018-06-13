package cn.edu.sdu.uims.component.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import cn.edu.sdu.uims.component.table.rowrender.RowSpecificRenderI;

/**
 * 
 * 变色Renderer，当符合ChangeColorRule时，变成红色显示 * 
 * @author zzy
 */
class RuleTableCellRender extends DefaultTableCellRenderer {
    
	protected RowSpecificRenderI rule=null;
     Color selectionBackground =
        UIManager.getColor("TextField.selectionBackground");
    private boolean cellHasFocus;
    
    public RuleTableCellRender(){      
    }
    
    public RuleTableCellRender(RowSpecificRenderI rule) {
        this.rule = rule;
    }

    public RowSpecificRenderI getRule() {
        return rule;
    }

    public void setRule(RowSpecificRenderI rule) {
        this.rule = rule;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int col) {
        setText(String.valueOf(value));
        if (rule == null)
            return this;
        if (rule.isColorSpecific(table, row, col, null))
            this.setForeground(Color.RED);
        else
            this.setForeground(Color.BLACK);

        cellHasFocus = hasFocus;

        return this;
    }
    
    protected void paintComponent(Graphics g)
    {
        if (cellHasFocus )
        {
            g.setColor( selectionBackground );
            //g.fillRect(0, 0, getPreferredSize().width+5, getSize().height);
            g.fillRect(0, 0, getSize().width, getSize().height);
        }

        super.paintComponent(g);
    }

}

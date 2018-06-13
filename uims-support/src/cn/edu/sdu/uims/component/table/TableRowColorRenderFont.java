package cn.edu.sdu.uims.component.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;

import cn.edu.sdu.uims.component.table.rowrender.TableRowSpecificRender;
import cn.edu.sdu.uims.def.UColumnTemplate;

public class TableRowColorRenderFont extends TableRowSpecificRender {
	    private Font boldFont = new Font("宋体",1,12);
	    private Font normalFont = new Font("宋体",0,12);
	    private Color backColor;
	    
	    public TableRowColorRenderFont(){      
	    }
	    
	    public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int col) {
	        setText(String.valueOf(value));
	        if (rowSpecificRender == null)
	            return this;
	        if (rowSpecificRender.isFontSpecific(table, row, col,null))
	            this.setFont(boldFont);
	        else
	            this.setFont(normalFont);
			if (tablePanel.isRowColorCtl == false) {
				if (row % 2 == 0) {
					setBackground(table.getBackground());
				} else {
					setBackground(Color.WHITE);
				}
			} else {
				if (table.getValueAt(row, tablePanel.colorCtlColNum) != null) {
					theColor = new Color(Integer.parseInt(table.getValueAt(row,
							tablePanel.colorCtlColNum).toString(), 16));
					setBackground(theColor);
				} else
					setBackground(table.getBackground());
			}

			if (isSelected) {
				setBackground(table.getSelectionBackground());
			}
			if (value instanceof Boolean) { // Boolean
				checkBox.setSelected(((Boolean) value).booleanValue());
				checkBox.setHorizontalAlignment(JLabel.CENTER);
				if (tablePanel.isRowColorCtl == false) {
					if (row % 2 == 0) {
						checkBox.setBackground(table.getBackground());
					} else {
						checkBox.setBackground(Color.WHITE);
					}
				} else {
					if (table.getValueAt(row, tablePanel.colorCtlColNum) != null) {
						theColor = new Color(Integer.parseInt((String) table
								.getValueAt(row,
										tablePanel.colorCtlColNum)
								.toString(), 16));
						checkBox.setBackground(theColor);
					} else
						checkBox.setBackground(table.getBackground());
				}
				if (isSelected) {
					checkBox.setBackground(table.getSelectionBackground());
				}
				return checkBox;
			}
			String str = (value == null) ? "" : value.toString();
			// 设置是否居中
			UColumnTemplate columnTemplate = tablePanel.tableTemplate.no;
			if (columnTemplate != null && col > 0) {
				this
						.setHorizontalAlignment(tablePanel.tableTemplate.columnTemplates[col - 1].horizontalAlignment);
			} else {
				this
						.setHorizontalAlignment(tablePanel.tableTemplate.columnTemplates[col].horizontalAlignment);
			}
//			return super.getTableCellRendererComponent(table, str, isSelected,
//					hasFocus, row, col);
	        return this;
	    }
	    
}

package cn.edu.sdu.uims.component.table.rowrender;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.component.table.UTablePanel;
import cn.edu.sdu.uims.def.UColumnTemplate;
import cn.edu.sdu.uims.service.UFactory;

public class TableRowSpecificRender extends DefaultTableCellRenderer {

	protected UTablePanel tablePanel;
	protected JCheckBox checkBox = new JCheckBox();
	protected Color theColor;
	protected RowSpecificRenderI rowSpecificRender = null;

	public TableRowSpecificRender(UTablePanel tablePanel) {
		this.tablePanel = tablePanel;
	}

	public TableRowSpecificRender() {
	}

	public void setTablePanel(UTablePanel tablePanel) {
		this.tablePanel = tablePanel;

	}

	public RowSpecificRenderI getRowSpecificRender() {
		return rowSpecificRender;
	}

	public void setRowSpecificRender(RowSpecificRenderI rowSpecificRender) {
		this.rowSpecificRender = rowSpecificRender;
	}

	private void SetUserCellAttribute(Component cell, Object o, int row,
			int column) {
		if (column < tablePanel.getUserStartColNo()
				|| column - tablePanel.getUserStartColNo() >= tablePanel
						.getTableTemplate().columnTemplates.length)
			return;
		UColumnTemplate ct = tablePanel.getTableTemplate().columnTemplates[column
				- tablePanel.getUserStartColNo()];
		if (ct.colorName != null) {
			cell.setForeground(UFactory.getModelSession().getColorByName(
					ct.colorName).color);
		} else {
			cell.setForeground(new Color(0, 0, 0));
		}
		if (o == null) {
			setText("");
		} else {
			if (o instanceof ListOptionInfo) {
				setText(((ListOptionInfo) o).getLabel());
			} else if (o instanceof Date) {
				SimpleDateFormat dateFormatter;
				if (ct.format == null)
					dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
				else
					dateFormatter = new SimpleDateFormat(ct.format);
				this.setText(dateFormatter.format((Date) o));
			} else if ((o instanceof Float || o instanceof Double)) {
				String format = null;
				if (ct.maxLength == 0) {
					if (o instanceof Float) {
						Float f = (Float) o;
						setText("" + f.intValue());
					} else {
						Double d = (Double) o;
						setText("" + d.intValue());
					}
				} else {
					if (ct.maxLength > 0) {
						format = "0.";
						for (int index = 0; index < ct.maxLength; index++)
							format += "#";
					} else if (ct.format != null) {
						format = ct.format;
					}
					if (format != null) {
						DecimalFormat fmt = new DecimalFormat(format);
						if (o instanceof Double) {
							setText(fmt.format((Double) o));
						} else if (o instanceof Float) {
							setText(fmt.format((Float) o));
						} else {
							setText(o.toString());
						}
					} else {
						setText(o.toString());
					}
				}
			}
		}
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		if (tablePanel != null) {
			Object data = tablePanel.getRowDataObject(row);
			SetUserCellAttribute(cell, value, row, column);
			if (rowSpecificRender != null) {
				if (rowSpecificRender.isEditSpecific(table, row, column, data)) {
					this.setEnabled(false);
				}
				if (rowSpecificRender.isColorSpecific(table, row, column, data)) {
					this.setForeground(Color.RED);
				}
				String colorName = rowSpecificRender.getCellColorName(table,
						row, column, data);
				if (colorName != null) {
					this.setForeground(UFactory.getModelSession()
							.getColorByName(colorName).color);
				}
			}
			if (rowSpecificRender != null && rowSpecificRender.isBackgroundColorSpecific(table, row, column,data)) {
				setBackground(Color.RED);				
			}
			else if (rowSpecificRender != null && rowSpecificRender.getCellBackgroundCodeName(table, row, column,data) != null) {
				this.setBackground(UFactory.getModelSession()
						.getColorByName(rowSpecificRender.getCellBackgroundCodeName(table, row, column,data)).color);
			}else {
				if (tablePanel.isRowColorCtl() == false) {
					if (row % 2 == 0) {
						setBackground(table.getBackground());
					} else {
						setBackground(Color.WHITE);
					}
				} else {
					if (table.getValueAt(row, tablePanel.getColorCtlColNum()) != null) {
						theColor = new Color(Integer.parseInt(
								table.getValueAt(row,
										tablePanel.getColorCtlColNum()).toString(),
								16));
						setBackground(theColor);
					} else
						setBackground(table.getBackground());
				}
			}
		}

		if (isSelected) {
			setBackground(table.getSelectionBackground());
		}
		if (value instanceof Boolean) { // Boolean
			checkBox.setSelected(((Boolean) value).booleanValue());
			checkBox.setHorizontalAlignment(JLabel.CENTER);
			if (tablePanel != null) {
				if (tablePanel.isRowColorCtl() == false) {
					if (row % 2 == 0) {
						checkBox.setBackground(table.getBackground());
					} else {
						checkBox.setBackground(Color.WHITE);
					}
				} else {
					if (table.getValueAt(row, tablePanel.getColorCtlColNum()) != null) {
						theColor = new Color(Integer.parseInt(
								(String) table.getValueAt(row,
										tablePanel.getColorCtlColNum())
										.toString(), 16));
						checkBox.setBackground(theColor);
					} else
						checkBox.setBackground(table.getBackground());
				}
			}
			if (isSelected) {
				checkBox.setBackground(table.getSelectionBackground());
			}
			return checkBox;
		}
		if (tablePanel != null) {
			String str = (value == null) ? "" : value.toString();
			// 设置是否居中
			UColumnTemplate columnTemplate = tablePanel.getTableTemplate().no;
			if (columnTemplate != null && column > 0) {
				this.setHorizontalAlignment(tablePanel.getTableTemplate().columnTemplates[column - 1].horizontalAlignment);
			} else {
				this.setHorizontalAlignment(tablePanel.getTableTemplate().columnTemplates[column].horizontalAlignment);
			}
		}
		return cell;
	}
}

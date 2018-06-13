package cn.edu.sdu.uims.component.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

import cn.edu.sdu.uims.component.button.UButton;

public class RowNumberRender extends UButton implements TableCellRenderer {

	private static final long serialVersionUID = -1602919081033177490L;

	private Border borderOut;

	private Border borderIn;

	private Border borderPressed;

	public RowNumberRender() {
		super();
		// TODO Auto-generated constructor stub
		borderOut = BorderFactory.createEmptyBorder();// (2,2,2,2);
		borderIn = BorderFactory.createBevelBorder(BevelBorder.RAISED,
				Color.white, Color.white, new Color(148, 145, 140), new Color(
						103, 101, 98));
		borderPressed = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
				Color.white, Color.white, new Color(148, 145, 140), new Color(
						103, 101, 98));
		this.setBorder(borderIn);

	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		this.setBorder(borderOut);
		this.setText((value == null) ? "*" : value.toString());
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		if (isSelected) {
			this.setBorder(borderPressed);
		}

		if (hasFocus) {
			this.setBorder(borderOut);
		}
		return this;
	}
}


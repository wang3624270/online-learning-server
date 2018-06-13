package cn.edu.sdu.uims.component.list;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import cn.edu.sdu.uims.util.UimsUtils;

public class CellRenderer extends JLabel implements ListCellRenderer {

	public CellRenderer() {
		setOpaque(true);
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (value != null) {
			setText(value.toString());
			String test = value.toString();
			int i = test.indexOf(".");
			if (i == -1) {
				// setIcon(new ImageIcon("/images/folder.jpg"));
				setIcon(UimsUtils.getCSClientIcon("folder.jpg"));
			} else {
				// setIcon(new ImageIcon("/images/file.jpg"));
				setIcon(UimsUtils.getCSClientIcon("file.jpg"));
			}
		}
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		return this;
	}

}

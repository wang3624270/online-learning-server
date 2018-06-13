package cn.edu.sdu.uims.component.textfield;

import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import cn.edu.sdu.uims.component.list.UListPopup;
import cn.edu.sdu.uims.filter.CompletionFilterI;
import cn.edu.sdu.uims.filter.NumberCompletionFilter;
import cn.edu.sdu.uims.util.UNumberUtility;

public class UNumberField extends UTextField implements KeyListener,
		FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UListPopup popup;

	private int preferredHeight = 100;

	private String num = "0";

	private CompletionFilterI filter;

	public UNumberField() {
		super();
		// TODO Auto-generated constructor stub
		popup = new UListPopup();
		this.addKeyListener(this);
		this.addFocusListener(this);
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if ((e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9)) {
			focusChanged(e.getKeyChar());
			this.requestFocus();
		} else if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
			focusChanged(e.getKeyChar());
			this.requestFocus();
		} else {
			e.setKeyChar(KeyEvent.CHAR_UNDEFINED);
		}
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			if (popup.isVisible()) {
				if (!popup.isSelected()) {
					popup.setSelectedIndex(0);
					num = popup.getSelectedIndex() + "";
					this.setText((String) popup.getSelectedValue());
				} else {
					num = popup.getSelectedIndex() + "";
					this.setText((String) popup.getSelectedValue());
				}
			}
			popup.setVisible(false);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (popup.isVisible()) {
				if (!popup.isSelected())
					popup.setLastOneSelected();
				else
					popup.setSelectedIndex(popup.getSelectedIndex() - 1);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (popup.isVisible()) {
				if (!popup.isSelected())
					popup.setSelectedIndex(0);
				else
					popup.setSelectedIndex(popup.getSelectedIndex() + 1);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public CompletionFilterI getFilter() {
		return filter;
	}

	public void setFilter(CompletionFilterI filter) {
		this.filter = filter;
	}

	public UListPopup getPopup() {
		return popup;
	}

	public void setPopup(UListPopup popup) {
		this.popup = popup;
	}

	public int getPreferredHeight() {
		return preferredHeight;
	}

	public void setPreferredHeight(int preferredHeight) {
		this.preferredHeight = preferredHeight;
	}

	private void focusChanged(char ch) {
		if (!popup.isVisible()) {
			showPopup();
		}
		if (filter != null) {
			// int pos = this.getCaretPosition();
			String value = this.getText();
			// String beValue = value.substring(0, pos + 1);
			if (ch != KeyEvent.VK_BACK_SPACE)
				value += ch;
			ArrayList array = filter.filter(value.trim());
			if (array != null)
				changeList(array);
			else {
				changeList(new ArrayList());
			}
			// System.out.println(value);
			setSelected(value.trim());
		}
	}

	private void showPopup() {
		popup.setPopupSize(getWidth(), preferredHeight);
		popup.show(this, 0, getHeight() - 1);
	}

	private void changeList(ArrayList array) {
		if (array.size() == 0) {
			if (popup.isVisible()) {
				popup.setVisible(false);
			}
		} else {
			if (!popup.isVisible()) {
				showPopup();
			}
		}
		if (isListChange(array) && array.size() != 0) {
			popup.setList(array);
		}
	}

	private boolean isListChange(ArrayList array) {
		if (array.size() != popup.getItemCount()) {
			return true;
		}
		for (int i = 0; i < array.size(); i++) {
			if (!array.get(i).equals(popup.getItem(i))) {
				return true;
			}
		}
		return false;
	}

	public void setSelected(String input) {
		if (!input.trim().equals("") && UNumberUtility.isIntValue(input.trim())) {
			popup.setSelectedIndex(Integer.parseInt(input.trim()) - 1);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Container c = frame.getContentPane();
		UNumberField txt = new UNumberField();
		NumberCompletionFilter filter = new NumberCompletionFilter();
		txt.setFilter(filter);
		c.add(txt);
		frame.setSize(400, 200);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void requestFocus() {
		super.requestFocus();
	}

	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		ArrayList array = filter.filter("1");
		if (array != null)
			changeList(array);
		else {
			changeList(new ArrayList());
		}
		// System.out.println(value);

		if (!popup.isVisible() && this.getText().trim().equals("")) {
			this.showPopup();
		}
		setSelected("1");
		this.requestFocus();
	}

	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	public String getText() {
		return num;
	}
}

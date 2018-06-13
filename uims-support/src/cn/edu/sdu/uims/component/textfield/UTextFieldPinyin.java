package cn.edu.sdu.uims.component.textfield;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import cn.edu.sdu.uims.filter.CompletionFilterI;
import cn.edu.sdu.uims.util.UPinYin;

public class UTextFieldPinyin extends UTextFiledPopup  {
	/**
	 * 
	 */
	private CompletionFilterI filter;
	public CompletionFilterI getFilter() {
		return filter;
	}

	public void setFilter(CompletionFilterI filter) {
		this.filter = filter;
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if ((e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z')) {
			focusChanged(e.getKeyChar());
			this.requestFocus();
		} else if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
			focusChanged(e.getKeyChar());
			this.requestFocus();
		} else if (UPinYin.isChinese(e.getKeyChar() + "")) {
			String str = this.getText() + e.getKeyChar();
			// PinYin.
			if (!str.equals("")) {
				UPinYin pinyin = new UPinYin();
				pinyin.addHanZi(str);
			}
		}
		else {
			e.setKeyChar(KeyEvent.CHAR_UNDEFINED);
		}
	}

	private void focusChanged(char ch) {
		if (!popup.isVisible()) {
			showPopup();
		}
		if (filter != null) {
			// int pos = this.getCaretPosition();
			String value = this.getText();
			// String beValue = value.substring(0, pos + 1);
			value += ch;
			ArrayList array = filter.filter(value.trim());
			if (array != null)
				changeList(array);
			else {
				changeList(new ArrayList());
			}
		}
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
	
}

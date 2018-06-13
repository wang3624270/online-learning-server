package cn.edu.sdu.uims.component.textfield;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.list.UListPopup;
import cn.edu.sdu.uims.component.table.UTablePanel;

public class UTextFiledPopup extends UTextField implements KeyListener,ListSelectionListener{
	private static final long serialVersionUID = 1L;

	protected UListPopup popup;

	protected int preferredHeight = 100;
	protected Point popupLocation = null;

	protected UComponentI screenOwner;




	public void setScreenOwner(UComponentI screenOwner) {
		this.screenOwner = screenOwner;
	}


	public void initContents() {
		// TODO Auto-generated method stub
		popup = new UListPopup();
		popup.addListSelectionListener(this);
		this.addKeyListener(this);
	}

	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			if (popup.isVisible()) {
				if (!popup.isSelected()) {
					popup.setSelectedIndex(0);
					this.setText((String) popup.getSelectedValue());
				} else {
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


	protected void showPopup() {
		popup.setPopupSize(getWidth(), preferredHeight);
		if(screenOwner != null && screenOwner instanceof UTablePanel) {
			UTablePanel t=(UTablePanel)screenOwner;
			Point p = t.getTablePopupPoint();
			popup.show(t.getAWTComponent(), p.x,p.y);
		}
		else
			popup.show(this, 0, getHeight() - 1);
		

	}



	public void requestFocus() {
		super.requestFocus();
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		JList list = (JList)e.getSource();
		Object o = list.getSelectedValue();
		if(screenOwner != null && screenOwner instanceof UTablePanel) {
			UTablePanel t=(UTablePanel)screenOwner;
			t.setCurrentCellObject(o);
		}else
			setData(o);
		popup.setVisible(false);
		sendActionEventToParent(new ActionEvent(this,0,this.getComponentName()));
	}
}

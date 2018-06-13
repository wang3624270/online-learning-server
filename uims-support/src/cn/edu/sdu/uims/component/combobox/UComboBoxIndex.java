package cn.edu.sdu.uims.component.combobox;

import java.awt.Dimension;

import cn.edu.sdu.uims.trans.URect;

public class UComboBoxIndex extends UComboBox {
	protected int innerWidth = -1, innerHeight = -1, innerX = -1, innerY = -1;

	public Object getData() {
		Integer index = this.getSelectedIndex();
		return index;
	}

	public void setData(Object obj) {
		int index;
		if (obj != null)
			index = (Integer) obj;
		else
			index = -1;
		this.setSelectedIndex(index);
	}
	public URect getBoundRect() {
		// TODO Auto-generated method stub
		return new URect(this.innerX, this.innerY, this.innerWidth,
				this.innerHeight);
	}
	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		Dimension dimension = new Dimension(w, h);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		this.setPreferredSize(dimension);
		this.innerHeight = h;
		this.innerWidth = w;
		this.innerX = x;
		this.innerY = y;
	}
}

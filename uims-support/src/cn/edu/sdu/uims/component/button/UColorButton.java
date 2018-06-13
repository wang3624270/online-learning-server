package cn.edu.sdu.uims.component.button;

import java.awt.Color;
import java.awt.Graphics;

import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.def.UButtonTemplate;

public class UColorButton extends UToggleButton {

	protected UColor c;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color oldColor = g.getColor();
		g.setColor(c.color);
		g.fillRect(7, 7, 16, 16);
		g.setColor(oldColor);
	}

	public void setColor(UColor c) {
		this.c = c;
	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		buttonTemplate = (UButtonTemplate) template;
	}

	public Object getData() {
		// TODO Auto-generated method stub
		if (buttonTemplate != null) {
			return buttonTemplate.colorName;
		}
		return null;
	}
}

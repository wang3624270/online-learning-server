package cn.edu.sdu.uims.component.button.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicToggleButtonUI;

public class UBasicToggleButtonUI extends BasicToggleButtonUI {
	public Color selectForeground, selectBackground, foreground, backround;
	public UBasicToggleButtonUI(){
		selectForeground =new Color(207,230,244);
		selectBackground =Color.BLUE;
		foreground = Color.WHITE;
		backround  = Color.BLACK;
		
	}
	public UBasicToggleButtonUI(Color selectForeground, Color selectBackground, Color foreground, Color backround){
		this.selectForeground =selectForeground;
		this.selectBackground =selectBackground;
		this.foreground = foreground;
		this.backround  = backround;
		
	}
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		AbstractButton b = (AbstractButton) c;
		if (b.isSelected()) {
			b.setBorder(BorderFactory.createLoweredBevelBorder());
			b.setBackground(selectForeground);
			b.setForeground(selectBackground);
		} else {
			b.setBorder(BorderFactory.createEtchedBorder());
			b.setBackground(foreground);
			b.setForeground(backround);
		}
	}
}

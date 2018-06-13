package cn.edu.sdu.uims.view;

import java.awt.Graphics;

import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.URect;

public class UVLine extends UVElement{

	public void draw(Graphics g, UMatrix m) {
		super.draw(g,m);
		URect rd= m.logicToView(new URect(x,y,w,h));
		g.drawLine(rd.x, rd.y, rd.x+rd.w, rd.y+rd.h);
	}
	public void drawBorder(Graphics g, UMatrix m){
	}

}

package cn.edu.sdu.uims.view;

import java.awt.Graphics;

import cn.edu.sdu.uims.trans.UMatrix;

public class UVBox extends UVElement {

	public UVBox() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void draw(Graphics g,UMatrix m) {
		super.draw(g,m);
//		URect rd= UMatrixUtil.logicToView(new URect(x,y,w,h),m.m);
//		g.drawLine(rd.x, rd.y, rd.x+rd.w, rd.y+rd.h);
		
	}

}

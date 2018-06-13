package cn.edu.sdu.uims.itms.cursor;

import java.awt.Graphics;

import cn.edu.sdu.uims.trans.UPoint;

public class ICursorPolyLine extends ICursorBase {
	public void draw(Graphics dc, UPoint []pt, UPoint p){		
		int len = pt.length;
		for (int i = 0; i < len - 1; i++) {
			dc.drawLine(pt[i].x, pt[i].y, pt[i + 1].x, pt[i + 1].y);
		}
		dc.drawLine(pt[len - 1].x, pt[len - 1].y, p.x, p.y);
	}
}

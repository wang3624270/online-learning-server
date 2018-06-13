package cn.edu.sdu.uims.itms.cursor;

import java.awt.Graphics;

import cn.edu.sdu.uims.trans.UPoint;

public class ICursorLine extends ICursorBase {
	public void draw(Graphics dc, UPoint[] pt, UPoint p) {
		
		if (pt != null && pt.length > 0 ) {			
			dc.drawLine(pt[0].x, pt[0].y, p.x, p.y);
		}
	}

}

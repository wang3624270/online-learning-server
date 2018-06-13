package cn.edu.sdu.uims.itms.cursor;

import java.awt.Graphics;

import cn.edu.sdu.uims.trans.UPoint;

public class ICursorRect extends ICursorBase {

	public void draw(Graphics dc, UPoint[] pt, UPoint p) {
		
		if (pt != null && pt.length > 0 ) {
			
			UPoint p0 = pt[0];
			int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
			if(p.x > p0.x) {
				x1 = p0.x;
				x2 = p.x;
			}
			else {
				x2 = p0.x;
				x1 = p.x;
			}
			if(p.y > p0.y) {
				y1 = p0.y;
				y2 = p.y;
			}
			else {
				y2 = p0.y;
				y1 = p.y;
			}
			dc.drawRect(x1, y1, x2-x1, y2-y1);
		}
	}

}

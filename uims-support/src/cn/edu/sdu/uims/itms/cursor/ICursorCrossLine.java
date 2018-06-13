package cn.edu.sdu.uims.itms.cursor;

import java.awt.Dimension;
import java.awt.Graphics;

import cn.edu.sdu.uims.graph.view.UCanvasI;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.trans.UPoint;

public class ICursorCrossLine extends ICursorBase {
	public void draw(UCanvasI canvas, ICursorDataBase data) {
//		if(data.getPoints()== null)
//			return ;
		ViewParameter vp = canvas.getViewParameter();
		Graphics dc = canvas.getGraphics();
		dc.setXORMode(xorColor);
		UPoint p;
		Dimension d = canvas.getGraphViewSize();
		if(data.savePoint.x >= 0 && data.savePoint.y >= 0) {
			p = vp.mv.logicToView(data.savePoint);
			dc.drawLine(p.x,0, p.x, d.height);
			dc.drawLine(0,p.y, d.width, p.y);
		}
		p = vp.mv.logicToView(data.currentPoint);
		dc.drawLine(p.x,0, p.x, d.height);
		dc.drawLine(0,p.y, d.width, p.y);
		dc.setPaintMode();
		data.saveCurrentPoint();
	}
}

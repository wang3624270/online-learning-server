package cn.edu.sdu.uims.graph.model.drag;

import java.awt.Graphics;

import cn.edu.sdu.uims.graph.GraphConstants;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UPoint;

public class DrageDataRectChangePoint implements DrageDataI {

	private int type;
	private UFPoint oldPoint;

	public DrageDataRectChangePoint(UFPoint fp, int type) {
		this.type = type;
		oldPoint = fp;
		
	}

	public void drageDataDraw(Graphics gc, ViewParameter mp, UFPoint p) {
		// TODO Auto-generated method stub
		UPoint p0, p1;
		p0 = mp.mv.logicToView(oldPoint);
		p1 = mp.mv.logicToView(p);
		if (type == GraphConstants.GRAPH_SELECT_STATUS_VECTOR_LEFTTOP) {
			gc.drawLine(p0.x, p0.y, p1.x, p0.y);
			gc.drawLine(p1.x, p0.y, p1.x, p1.y);
			gc.drawLine(p1.x, p1.y, p0.x, p1.y);
			gc.drawLine(p0.x, p1.y, p0.x, p0.y);
		} else if (type == GraphConstants.GRAPH_SELECT_STATUS_VECTOR_RIGHTTOP) {
			gc.drawLine(p1.x, p0.y, p0.x, p0.y);
			gc.drawLine(p0.x, p0.y, p0.x, p1.y);
			gc.drawLine(p0.x, p1.y, p1.x, p1.y);
			gc.drawLine(p1.x, p1.y, p1.x, p0.y);
		} else if (type == GraphConstants.GRAPH_SELECT_STATUS_VECTOR_LEFTBOTTOM) {
			gc.drawLine(p0.x, p1.y, p1.x, p1.y);
			gc.drawLine(p1.x, p1.y, p1.x, p0.y);
			gc.drawLine(p1.x, p0.y, p0.x, p0.y);
			gc.drawLine(p0.x, p0.y, p0.x, p1.y);
		} else if (type == GraphConstants.GRAPH_SELECT_STATUS_VECTOR_RIGHTBOTTOM) {
			gc.drawLine(p1.x, p1.y, p0.x, p1.y);
			gc.drawLine(p0.x, p1.y, p0.x, p0.y);
			gc.drawLine(p0.x, p0.y, p1.x, p0.y);
			gc.drawLine(p1.x, p0.y, p1.x, p1.y);
		}
	}
}

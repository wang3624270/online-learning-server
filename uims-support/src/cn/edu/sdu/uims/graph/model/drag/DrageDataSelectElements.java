package cn.edu.sdu.uims.graph.model.drag;

import java.awt.Graphics;
import java.util.List;

import cn.edu.sdu.uims.graph.model.GElement;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UPoint;

public class DrageDataSelectElements implements DrageDataI {

	UFPoint oldPoint;
	List selectedElement;
	public DrageDataSelectElements(List es, UFPoint p) {
		selectedElement = es;
		oldPoint = p;
	}
	public void drageDataDraw(Graphics gc, ViewParameter mp, UFPoint p) {
		// TODO Auto-generated method stub
		int i;
		int dx,dy;
		UPoint p0, p1, sh;
		p0 = mp.m.logicToView(oldPoint);
//		p1 = mp.m.logicToView(p);
//		p0 = oldPoint;
//		p1 = p;
		dx = (int)(p.x - p0.x);
		dy = (int)(p.y - p0.y);
//		System.out.print("<"+oldPoint.x +"," + oldPoint.y + ">< " + p.x+ "," + p.y + "> dx = " + dx + " dy " + dy);
		GElement ge;
		for(i = 0; i < selectedElement.size();i++) {
			ge = (GElement)selectedElement.get(i);
			if( ge != null ) {
				ge.drawDo(gc, mp, null,null,new UPoint(dx,dy));
				
			}
		}
	}
}

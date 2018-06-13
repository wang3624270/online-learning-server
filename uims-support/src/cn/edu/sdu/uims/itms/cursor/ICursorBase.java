package cn.edu.sdu.uims.itms.cursor;

import java.awt.Color;
import java.awt.Graphics;

import cn.edu.sdu.uims.graph.model.drag.DrageDataI;
import cn.edu.sdu.uims.graph.view.UCanvasI;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.itms.def.ICursorTemplate;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UPoint;

public class ICursorBase {
	protected ICursorTemplate template;
	protected static Color xorColor = new Color(255, 255, 255);

	public UPoint [] changeFPointToPoint(UFPoint [] fps) {
		if(fps == null || fps.length == 0)
			return null;
		UPoint ps[] = new UPoint[fps.length];
		for(int i = 0; i < ps.length;i++) {
			ps[i] = new  UPoint((int)fps[i].x, (int)fps[i].y);
		}
		return ps;
	}
	public UPoint  changeFPointToPoint(UFPoint fp) {
		return new UPoint((int)fp.x, (int)fp.y);
	}
	public void draw(UCanvasI canvas, ICursorDataBase data) {
		if(data.getPoints()== null || data.getPoints().length == 0)
			return ;
		ViewParameter vp = canvas.getViewParameter();
		Graphics dc = canvas.getGraphics();
		dc.setXORMode(xorColor);
//		UPoint [] pt = vp.mv.logicToView(data.getPoints());
		UPoint [] pt = changeFPointToPoint(data.getPoints());
		if(data.savePoint.x >= 0 && data.savePoint.y >= 0) {
//			draw(dc, pt,vp.mv.logicToView(data.savePoint));
			draw(dc, pt,changeFPointToPoint(data.savePoint));
			if(data.getDrageData()!= null)
				data.getDrageData().drageDataDraw(dc, vp, data.savePoint);
		}
//		draw(dc, pt,vp.mv.logicToView(data.currentPoint));
		draw(dc, pt,changeFPointToPoint(data.currentPoint));
		if(data.getDrageData()!= null)
			data.getDrageData().drageDataDraw(dc, vp, data.currentPoint);
		dc.setPaintMode();
		data.saveCurrentPoint();
	}
	public void draw(Graphics dc, UPoint []pt, UPoint p){		
	}
	public void drawExists(UCanvasI dc, ICursorDataBase data) {
	}
	public ICursorTemplate getTemplate() {
		return template;
	}

	public void setTemplate(ICursorTemplate template) {
		this.template = template;
	}
	public void setDrageData(DrageDataI drageData) {
	}
   
}

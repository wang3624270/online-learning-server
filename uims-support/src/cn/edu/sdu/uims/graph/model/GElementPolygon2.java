package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import org.dom4j.Element;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import cn.edu.sdu.uims.base.UStroke;
import cn.edu.sdu.uims.graph.GraphConstants;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UFRect;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.UPoint;

public class GElementPolygon2 extends GElement2D {
	private static final long serialVersionUID=-6578989662759480410l;
	public List getpList() {
		return pList;
	}

	public void setpList(List pList) {
		this.pList = pList;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}


	public List pList;
	// 张韩add
	public boolean closed;
	public boolean isShow = true;
	public GElementPolygon2() {
		pList = null;
	}

	public GElementPolygon2(List pList) {
		this.pList = pList;
	}
	
	// 张韩add
	public GElementPolygon2(List pList,boolean closed) {
		this.pList = pList;
		this.closed = closed;
	}

	Polygon getPolygon(UMatrix m) {
		int i;
		UPoint p;
		UFPoint fp;
		int xpoints[], ypoints[];
		if (pList == null || pList.size() == 0)
			return null;
		xpoints = new int[pList.size()];
		ypoints = new int[pList.size()];
		for (i = 0; i < pList.size(); i++) {
			fp = (UFPoint) pList.get(i);
			p = m.logicToView(fp);
			xpoints[i] = p.x;
			ypoints[i] = p.y;
		}
		Polygon poly = new Polygon(xpoints, ypoints, xpoints.length);
		return poly;
	}

	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object par,UPoint shiftPoint) {
		if(!isShow)
			return;
		Graphics2D dc2d = (Graphics2D) dc;
		Stroke oldStroke = null;
		UStroke stroke;
		if (strokeName != null) {
			oldStroke = dc2d.getStroke();
			stroke = UFactory.getModelSession().getStrokeByName(strokeName);
			if (stroke != null) {
				dc2d.setStroke(stroke.stroke);
			}
		}
		Graphics2D g2d = (Graphics2D) dc;
		for(int i = 0;i < pList.size()-1;++i)
		{
			UFPoint p0 = (UFPoint) pList.get(i);
			UFPoint p1 = (UFPoint) pList.get(i+1);
			Point vp0 = p.m.logicToViewD(p0.x, p0.y, p0.z);
			Point vp1 = p.m.logicToViewD(p1.x, p1.y, p1.z);
			Line2D.Double line = new Line2D.Double(vp0.getX(),vp0.getY(),vp1.getX(),vp1.getY());
			g2d.draw(line);
		}
		if(this.closed)
		{
			UFPoint p0 = (UFPoint) pList.get(pList.size() - 1);
			UFPoint p1 = (UFPoint) pList.get(0);
			Point vp0 = p.m.logicToViewD(p0.x, p0.y, p0.z);
			Point vp1 = p.m.logicToViewD(p1.x, p1.y, p1.z);
			Line2D.Double line = new Line2D.Double(vp0.getX(),vp0.getY(),vp1.getX(),vp1.getY());
			g2d.draw(line);
		}
//		Polygon poly = this.getPolygon(p.m);
//		dc.drawPolygon(poly);
		if (strokeName != null) {
			dc2d.setStroke(oldStroke);
		}
	}

	public int testPointOnElement(UFPoint fp) {
		if (pList != null && pList.size() > 0) {
			if (pList.size() == 2) {
				UFPoint p1 = (UFPoint) pList.get(0);
				UFPoint p2 = (UFPoint) pList.get(1);
				if ((p1.x <= fp.x && p2.x >= fp.x)
						|| (p1.y <= fp.y && fp.y <= p2.y)) {
					return GraphConstants.GRAPH_SELECT_STATUS_INNER;
				}
			} else {
				int[] xpoints = new int[pList.size()];
				int[] ypoints = new int[pList.size()];
				for (int i = 0; i < pList.size(); i++) {
					UFPoint point = (UFPoint) pList.get(i);
					xpoints[i] = (int) point.x;
					ypoints[i] = (int) point.y;
				}
				Polygon polygon = new Polygon(xpoints, ypoints, pList.size());
				if (polygon.contains(fp.x, fp.y))
					return GraphConstants.GRAPH_SELECT_STATUS_INNER;
				else
					return GraphConstants.GRAPH_SELECT_STATUS_NO;
			}
		}
		return GraphConstants.GRAPH_SELECT_STATUS_NO;
	}

	public Object getInnerObjectByPoint(UFPoint fp) {
		if (listNote != null) {
			for (int i = 0; i < listNote.size(); i++) {
				GElement ge = (GElement) listNote.get(i);
			}
		}
		return null;
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
	}
	public void exportElementToDoc(Element ge) {
		// TODO Auto-generated method stub
		UFPoint fp;
		Element se;
		int len,i;
		if(pList!= null || pList.size() != 0) {
			len = pList.size();
			for(i = 0 ; i < len;i++) {
				fp = (UFPoint)pList.get(len-1-i);
				se = ge.addElement("point");
				se.addAttribute("y", ""+fp.y);
				se.addAttribute("x", ""+fp.x);
			}
		}
		super.exportElementToDoc(ge);
	}

	@Override
	public Object getJSonObject(ViewParameter viewParameter) {
		// TODO Auto-generated method stub
		JsonObject obj = new JsonObject();
		JsonArray content = new JsonArray();
		for(int i = 0;i < this.pList.size();++i)
		{
			UFPoint p = (UFPoint) this.pList.get(i);
			JsonObject pObj = new JsonObject();
			pObj.addProperty("x", p.x);
			pObj.addProperty("y", p.y);
			pObj.addProperty("z", p.z);
			content.add(pObj);
		}
		obj.add("GElementPolygon2", content);
		return obj;
	}
	public void restBox(){
		box = new UFRect();
		for(int i = 0;i < this.pList.size();++i)
		{
			UFPoint p = (UFPoint) this.pList.get(i);
			if(p.x < box.x) {
				box.x = p.x;
			}
			if(p.x >= box.x+box.w) {
				box.w = p.x - box.x;
			}
			if(p.y < box.y) {
				box.y = p.y;
			}
			if(p.y >= box.y+box.h) {
				box.h = p.y - box.y;
			}
		}
	}
}

package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.dom4j.Element;

import com.google.gson.JsonObject;

import cn.edu.sdu.uims.base.UStroke;
import cn.edu.sdu.uims.constant.UMethodConstants;
import cn.edu.sdu.uims.graph.GraphConstants;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UPoint;

public class GElementLine2 extends GElement2D {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UFPoint p1 = new UFPoint();
	public UFPoint p2 = new UFPoint();

	public GElementLine2() {
		
	}

	public GElementLine2(float x1, float y1, float x2, float y2) {
		this.p1.x = x1;
		this.p1.y = y1;
		this.p2.x = x2;
		this.p2.y = y2;
	}
	
	/*
	 * 张韩添加
	 * */
	public GElementLine2(UFPoint p1,UFPoint p2){
		this.p1.x = p1.x;
		this.p1.y = p1.y;
		this.p2.x = p2.x;
		this.p2.y = p2.y;
	}

	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object par,UPoint shiftPoint) {
		Graphics2D dc2d = (Graphics2D) dc;
		UPoint pt1, pt2;
		Stroke oldStroke = null;
		UStroke stroke;
		if (strokeName != null) {
			oldStroke = dc2d.getStroke();
			stroke = UFactory.getModelSession().getStrokeByName(strokeName);
			if (stroke != null) {
				dc2d.setStroke(stroke.stroke);
			}
		}
		pt1 = p.m.logicToView(p1);
		pt2 = p.m.logicToView(p2);
		dc.drawLine(pt1.x, pt1.y, pt2.x, pt2.y);
		if (strokeName != null) {
			dc2d.setStroke(oldStroke);
		}
	}

	public int testPointOnElement(UFPoint fp) {
		if ((p1.x < fp.x && fp.x < p2.x) && (p1.y < fp.y && fp.y < p2.y)) {
			return GraphConstants.GRAPH_SELECT_STATUS_INNER;
		} else if ((p1.x <= fp.x && fp.x <= p2.x)
				|| (p1.y <= fp.y && p2.y >= fp.y)) {
			if ((p2.x >= (fp.x - 1) && p2.x <= (fp.x + 1))
					&& (p2.y >= (fp.y - 1) && p2.y <= (fp.y + 1))) {
				UMethodConstants.GRAPH_SELECT_STATUS_BOUND_TYPE = 1;
				return GraphConstants.GRAPH_SELECT_STATUS_BOUND;
			} else if ((p1.x >= (fp.x - 1) && p1.x <= (fp.x + 1))
					&& (p1.y >= (fp.y - 1) && p1.y <= (fp.y + 1))) {
				UMethodConstants.GRAPH_SELECT_STATUS_BOUND_TYPE = 2;
				return GraphConstants.GRAPH_SELECT_STATUS_BOUND;
			}
			return GraphConstants.GRAPH_SELECT_STATUS_INNER;
		}
		return GraphConstants.GRAPH_SELECT_STATUS_NO;
	}

	@SuppressWarnings("unchecked")
	public ArrayList getDragPoints(int selectStatus) {
		ArrayList pointList = new ArrayList();
		switch (selectStatus) {
		case 1:
			pointList.add(p1);
			break;
		case 2:
			pointList.add(p2);
			break;
		}
		return pointList;
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		readUFPoint(in, p1);
		readUFPoint(in, p2);
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeUFPoint(out, p1);
		writeUFPoint(out, p2);
	}
	public void exportElementToDoc(Element ge) {
		// TODO Auto-generated method stub
		ge.addAttribute("y2", ""+p2.y);
		ge.addAttribute("x2", ""+p2.x);
		ge.addAttribute("y1", ""+p1.y);
		ge.addAttribute("x1", ""+p1.x);
		super.exportElementToDoc(ge);
	}

	@Override
	public Object getJSonObject(ViewParameter viewParameter) {
		// TODO Auto-generated method stub
		JsonObject obj = new JsonObject();
		JsonObject content = new JsonObject();
		JsonObject pObj0 = new JsonObject();
		JsonObject pObj1 = new JsonObject();
		pObj0.addProperty("x", this.p1.x);
		pObj0.addProperty("y", this.p1.y);
		pObj0.addProperty("z", this.p1.z);

		pObj1.addProperty("x", this.p2.x);
		pObj1.addProperty("y", this.p2.y);
		pObj1.addProperty("z", this.p2.z);
		
		content.add("start", pObj0);
		content.add("end", pObj1);
		obj.add("GElementLine2", content);
		return obj;
	}	
	
}

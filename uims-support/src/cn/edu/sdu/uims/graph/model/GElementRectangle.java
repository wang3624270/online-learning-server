package cn.edu.sdu.uims.graph.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

import com.google.gson.JsonObject;

import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.uims.base.UStroke;
import cn.edu.sdu.uims.graph.GraphConstants;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UPoint;
import cn.edu.sdu.uims.trans.URect;

public class GElementRectangle extends GElement2D {

	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object par,UPoint shiftPoint) {
		Color oldColor = dc.getColor();
		UColor uColor = null;
		boolean bColorChangeed = false;
		if (colorName != null) {
			uColor = UFactory.getModelSession().getColorByName(colorName);
			dc.setColor(uColor.color);
			bColorChangeed = true;
		}
		if(box == null)
			return;
		Graphics2D dc2d = (Graphics2D) dc;
		URect ur;
		Stroke oldStroke = null;
		UStroke stroke;
		if (strokeName != null) {
			oldStroke = dc2d.getStroke();
			stroke = UFactory.getModelSession().getStrokeByName(strokeName);
			if (stroke != null) {
				dc2d.setStroke(stroke.stroke);
			}
			if(uColor != null) 
				dc2d.setColor(uColor.color);
		}
		ur = p.m.logicToView(box);
		dc.drawRect(ur.x+shiftPoint.x, ur.y+shiftPoint.y, ur.w, ur.h);
//		dc.fillRect((int)box.x, (int)box.y, (int)box.w, (int)box.h);
		if (strokeName != null) {
			dc2d.setStroke(oldStroke);
		}
		if (bColorChangeed)
			dc.setColor(oldColor);
		
	}
	public int testPointOnElement(UFPoint fp) {
		if(Math.abs(fp.x- box.x)+ Math.abs(fp.y-box.y) <  GraphConstants.MIN_BOUND) {
			fp.x = box.x + box.w;
			fp.y = box.y + box.h;
			return GraphConstants.GRAPH_SELECT_STATUS_VECTOR_LEFTTOP;
		}
		else if(Math.abs(fp.x- box.x - box.w)+ Math.abs(fp.y-box.y) <  GraphConstants.MIN_BOUND) {
			fp.x = box.x ;
			fp.y = box.y + box.h;
			return GraphConstants.GRAPH_SELECT_STATUS_VECTOR_RIGHTTOP;
		}else if(Math.abs(fp.x- box.x-box.w)+ Math.abs(fp.y-box.y-box.h) <  GraphConstants.MIN_BOUND) {
			fp.x = box.x ;
			fp.y = box.y ;
			return GraphConstants.GRAPH_SELECT_STATUS_VECTOR_RIGHTBOTTOM;
		}if(Math.abs(fp.x- box.x)+ Math.abs(fp.y-box.y-box.h) <  GraphConstants.MIN_BOUND) {
			fp.x = box.x +  box.w;
			fp.y = box.y;
			return GraphConstants.GRAPH_SELECT_STATUS_VECTOR_LEFTBOTTOM;
		}if(fp.x > box.x && fp.y >box.y && fp.x < (box.x+box.w) && fp.y < (box.y+box.h) ) {
			return GraphConstants.GRAPH_SELECT_STATUS_INNER;
		} 
		return GraphConstants.GRAPH_SELECT_STATUS_NO;
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
	}
	public void exportElementToDoc(Element ge) {
		// TODO Auto-generated method stub
		ge.addAttribute("h", ""+box.h);
		ge.addAttribute("w", ""+box.w);
		ge.addAttribute("y", ""+box.y);
		ge.addAttribute("x", ""+box.x);
		super.exportElementToDoc(ge);
	}
	@Override
	public Object getJSonObject(ViewParameter viewParameter) {
		// TODO Auto-generated method stub
		JsonObject obj = new JsonObject();
		
		JsonObject content = new JsonObject();
		JsonObject cp = new JsonObject();
		cp.addProperty("x", this.box.x);
		cp.addProperty("y", this.box.y);
		cp.addProperty("w", this.box.w);
		cp.addProperty("h", this.box.h);
		content.add("box",cp);
		content.addProperty("roomId",this.getId());
		obj.add("GElementRectangle", content);
		return obj;
	}	
	

}

package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

import cn.edu.sdu.uims.base.UStroke;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UPoint;

/*
 * 张韩添加
 * */

public class GElementCircle extends GElement2D {
	
	private static final long serialVersionUID = 1L;
	public UFPoint pc = null;
	public double radius = 0.0;

	public GElementCircle() {
		// TODO Auto-generated constructor stub
		pc = new UFPoint();
		radius = 0.0;
	}
	
	public GElementCircle(UFPoint pc,double radius){
		this.pc = pc;
		this.radius = radius;
	}
	
	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object par,UPoint shiftPoint) {
		Graphics2D dc2d = (Graphics2D) dc;
		Point cp;
		Stroke oldStroke = null;
		UStroke stroke;
		if (strokeName != null) {
			oldStroke = dc2d.getStroke();
			stroke = UFactory.getModelSession().getStrokeByName(strokeName);
			if (stroke != null) {
				dc2d.setStroke(stroke.stroke);
			}
		}
		cp = p.m.logicToViewD(pc.x, pc.y, pc.z);
		Point vp = p.m.logicToViewD(pc.x + radius,pc.y,pc.z);
		double r = vp.getX() - cp.getX();
		double x0 = cp.getX() -r;
		double y0 = cp.getY() -r;
		double w = 2*r;
		double h = 2*r;
		Ellipse2D.Double circle = new Ellipse2D.Double(x0,y0,w,h);
		dc2d.draw(circle);
		if (strokeName != null) {
			dc2d.setStroke(oldStroke);
		}
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		readUFPoint(in, pc);
		readDouble(in,radius);
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeUFPoint(out, pc);
		writeDouble(out,radius);
	}
	public void exportElementToDoc(Element ge) {
		// TODO Auto-generated method stub
		ge.addAttribute("yc", ""+pc.y);
		ge.addAttribute("xc", ""+pc.x);
		ge.addAttribute("radius",""+radius);
		super.exportElementToDoc(ge);
	}	

}

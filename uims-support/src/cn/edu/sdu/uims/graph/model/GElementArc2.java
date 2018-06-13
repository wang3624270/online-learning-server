package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

import com.google.gson.JsonObject;

import cn.edu.sdu.uims.base.UStroke;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UPoint;

public class GElementArc2 extends GElement2D {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UFPoint pc = null;
	public UFPoint ps = null;
	public UFPoint pe = null;
	
	// 张韩add
	public boolean counterclockwise;
	public double start_angle;
	public double end_angle;

	public GElementArc2(){
		this.pc = new UFPoint();
		this.ps = new UFPoint();
		this.pe = new UFPoint();
	}
	
	public GElementArc2(UFPoint pc,UFPoint ps,UFPoint pe){
		this.pc = pc;
		this.ps = ps;
		this.pe = pe;
	}
	
	public GElementArc2(UFPoint pc,UFPoint ps,UFPoint pe,double start_angle,double end_angle,boolean counterclockwise){
		this.pc = pc;
		this.ps = ps;
		this.pe = pe;
		this.start_angle = start_angle;
		this.end_angle = end_angle;
		this.counterclockwise = counterclockwise;
	}
	
	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object par,UPoint shiftPoint) {
		Graphics2D dc2d = (Graphics2D) dc;
		UPoint cp,sp,ep;
		Stroke oldStroke = null;
		UStroke stroke;
		if (strokeName != null) {
			oldStroke = dc2d.getStroke();
			stroke = UFactory.getModelSession().getStrokeByName(strokeName);
			if (stroke != null) {
				dc2d.setStroke(stroke.stroke);
			}
		}
		cp = p.m.logicToView(pc);
		sp = p.m.logicToView(ps);
		ep = p.m.logicToView(pe);
		double radius = this.getRadius(pc, ps);
		double x0 = cp.x - radius;
		double y0 = cp.y - radius;
		double w = 2 * radius;
		double h = 2 * radius;
		Arc2D.Double arc = null;
		if(this.counterclockwise) //逆时针
		{
			double extent = 0.0;
			if(this.end_angle <= this.start_angle)
				extent = this.end_angle - this.start_angle + 360;
			else
				extent = this.end_angle - this.start_angle;
			arc =  new Arc2D.Double(x0,y0,w,h,this.end_angle,extent,Arc2D.OPEN);
		}
		else  // 顺时针
		{
			double extent = 0.0;
			if(this.end_angle <= this.start_angle)
				extent = this.end_angle - this.start_angle + 360;
			else
				extent = this.end_angle - this.start_angle;
			arc = new Arc2D.Double(x0,y0,w,h,this.start_angle,extent,Arc2D.OPEN);
		}
		dc2d.draw(arc);
		if (strokeName != null) {
			dc2d.setStroke(oldStroke);
		}
	}
	
	//张韩add
	public double getRadius(UFPoint pc,UFPoint p0) {
		double x = pc.x - p0.x;
		double y = pc.y - p0.y;
		double z = pc.z - p0.z;
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		readUFPoint(in, pc);
		readUFPoint(in, ps);
		readUFPoint(in, pe);
		
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeUFPoint(out, pc);
		writeUFPoint(out, ps);
		writeUFPoint(out, pe);
	}
	public void exportElementToDoc(Element ge) {
		// TODO Auto-generated method stub
		ge.addAttribute("ye", ""+pe.y);
		ge.addAttribute("xe", ""+pe.x);
		ge.addAttribute("ys", ""+ps.y);
		ge.addAttribute("xs", ""+ps.x);
		ge.addAttribute("yc", ""+pc.y);
		ge.addAttribute("xc", ""+pc.x);
		super.exportElementToDoc(ge);
	}

	@Override
	public Object getJSonObject(ViewParameter viewParameter) {
		// TODO Auto-generated method stub
		
		JsonObject obj = new JsonObject();
		JsonObject content = new JsonObject();
		JsonObject cp = new JsonObject();
		cp.addProperty("x", this.pc.x);
		cp.addProperty("y", this.pc.y);
		cp.addProperty("z", this.pc.z);
		content.add("center",cp);
		content.addProperty("radius",this.getRadius(pc, ps));
		content.addProperty("start",this.start_angle);
		content.addProperty("end",this.end_angle);
		obj.add("GElementArc2", content);
		return obj;
	}	

}

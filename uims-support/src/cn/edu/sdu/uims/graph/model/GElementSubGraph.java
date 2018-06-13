package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.dom4j.Element;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.UPoint;

public class GElementSubGraph extends GElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UFPoint po = new UFPoint();
	public float sx, sy;
	public float ra;
	public String subGraphName;
	public boolean dataDisp;
	UMatrix m;

	public GElementSubGraph() {
		po.x = 0;
		po.y = 0;
		sx = 1;
		sy = 1;
		ra = 0;
		subGraphName = null;
		m = new UMatrix();
	}

	public GElementSubGraph(Graph2D g, float x0, float y0) {
		po.x = x0;
		po.y = y0;
		sx = 1;
		sy = 1;
		ra = 0;
		subGraphName = null;
		m = new UMatrix(po);
	}

	public GElementSubGraph(String subGraphName, Graph2D g, float x0, float y0,
			float sx, float sy, float ra) {
		po.x = x0;
		po.y = y0;
		this.sx = sx;
		this.sy = sy;
		this.ra = ra;
		this.subGraphName = subGraphName;
		m = new UMatrix(po);
	}

	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object data, UPoint shiftPoint) {
		if (subGraphName == null)
			return;
		Graph2D g2d = (Graph2D) parent;
		Graph2DI subGraph = g2d.getGraph2DByName(subGraphName);
		if (subGraph == null)
			return;
		ViewParameter sp = new ViewParameter(p.mv, p.mt);
		UMatrix.multi(m, p.mt, sp.mt);
		UMatrix.multi(sp.mv, sp.mt, sp.m);
		Object par = null;
		if (data != null) {
			par = getAttributeObject(data);
		}
		if (dataDisp) {
			if (par != null)
				subGraph.draw(dc, sp, c, par, shiftPoint);
		} else {
			if (par == null && data instanceof UFormI) {
				par = data;
			}
			subGraph.draw(dc, sp, c, par, shiftPoint);
		}
	}

	public void init() {
		m = new UMatrix(po);
	}

	public String getSubGraphName() {
		return subGraphName;
	}

	public void setSubGraphName(String subGraphName) {
		this.subGraphName = subGraphName;
	}

	public void setAttributeValueToXml(HashMap map) {

	}

	public void read(DataInputStream in) throws IOException {
		super.read(in);
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
	}
	public void exportElementToDoc(Element ge) {
		// TODO Auto-generated method stub
		ge.addAttribute("dataDisp", ""+dataDisp);
		ge.addAttribute("subName", this.getSubGraphName());
		ge.addAttribute("ra", ""+ra);
		ge.addAttribute("sy", ""+sy);
		ge.addAttribute("sx", ""+sx);
		ge.addAttribute("yo", ""+po.y);
		ge.addAttribute("xo", ""+po.x);
		ge.addAttribute("type", "subGraph");
		super.exportElementToDoc(ge);
	}	
}

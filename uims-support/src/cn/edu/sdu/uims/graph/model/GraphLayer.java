package cn.edu.sdu.uims.graph.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.trans.UFRect;
import cn.edu.sdu.uims.trans.UMatrix;

public class GraphLayer extends GElement implements GraphLayerI{
	private double x, y, w,h,dpi;
	private boolean visible = true;
	private boolean canSelected = false;
	private String graph2DName;
	public GraphLayer(){
		x=0;
		y=0;
		w=595.27559f;
		h=841.88976f; 
		dpi=72;
		visible = true;
	}
	public UFRect getGraphBox(){
		UFRect r = new UFRect(x,y,w,h);
		return r;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getW() {
		return w;
	}
	public void setW(double w) {
		this.w = w;
	}
	public double getH() {
		return h;
	}
	public void setH(double h) {
		this.h = h;
	}
	public double getDpi() {
		return dpi;
	}
	public void setDpi(double dpi) {
		this.dpi = dpi;
	}
	
	
/*	public Graph2D getGraph2D() {
		return graph2D;
	}
	public void setGraph2D(Graph2D graph2D) {
		this.graph2D = graph2D;
	}
	public List getSelectElementByPoint(UFPoint fp) {
			return graph2D.getSelectElementsByPoint(fp);
	}
	public SelectedData getSelectedDataByPoint(UFPoint fp){
		return graph2D.getSelectedDataByPoint(fp);
	}
*/
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		x = in.readDouble();
		y = in.readDouble();
		w= in.readDouble();
		h= in.readDouble();
		dpi = in.readDouble();
		visible = in.readBoolean();
		graph2DName = readString(in);
		
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(w);
		out.writeDouble(h);
		out.writeDouble(dpi);
		out.writeBoolean(visible);
		writeString(out,graph2DName);

	}
	public String getGraph2DName() {
		return graph2DName;
	}
	public void setGraph2DName(String graph2DName) {
		this.graph2DName = graph2DName;
	}
	public ViewParameter changeViewParameter(ViewParameter p) {
		UMatrix m = new UMatrix(x,y);
		ViewParameter sp = new ViewParameter(p.mv, p.mt);
		UMatrix.multi(m, p.mt, sp.mt);
		UMatrix.multi(sp.mv, sp.mt, sp.m);
		return sp;
	}
	public void copy(GraphLayerI layerI){
		if(layerI instanceof GraphLayer) {
			GraphLayer layer = (GraphLayer)layerI;
			x = layer.x;
			y = layer.y;
			w = layer.w;
			h = layer.h;
			dpi = layer.dpi;
			visible = true;
			graph2DName = layer.graph2DName;
		}	
	}
	public void exportElementToDoc(Element ge){
		ge.addAttribute("graph2dName", graph2DName);
		ge.addAttribute("x",""+x);
		ge.addAttribute("y",""+y);
		ge.addAttribute("w",""+w);
		ge.addAttribute("h",""+h);
		ge.addAttribute("dpi",""+dpi);
		ge.addAttribute("display",""+visible);
		super.exportElementToDoc(ge);
	}
	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return visible;
	}
	@Override
	public void setVisible(boolean visible) {
		// TODO Auto-generated method stub
		this.visible = visible;
	}
	public boolean isCanSelected() {
		return canSelected;
	}
	public void setCanSelected(boolean canSelected) {
		this.canSelected = canSelected;
	}
}

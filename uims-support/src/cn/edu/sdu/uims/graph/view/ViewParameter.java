package cn.edu.sdu.uims.graph.view;

import java.awt.Point;

import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.UPoint;
import cn.edu.sdu.uims.trans.URect;

public class ViewParameter {
	public WVTrans wv;
	public UMatrix mv = new UMatrix() ;
	public UMatrix mt = new UMatrix();
	public UMatrix m = new UMatrix();
	public ViewParameter(){
	}
	public ViewParameter(UMatrix mv, UMatrix mt ){
		UMatrix.copy(mv, this.mv);
		UMatrix.copy(mt, this.mt);
		UMatrix.multi(mv, mt,m);
	}
	public ViewParameter(WVTrans wv, UMatrix mv, UMatrix mt ){
		this(mv,mt);
		this.wv = wv;
	}
	private int reverseYValue(int y){
		if(wv != null && !wv.rightHanded) 
			return y;
		else 
			return  (int)(wv.vy1 - (y - wv.vy0));
	}
	private double reverseYValueD(double y){
		if(wv != null && !wv.rightHanded) 
			return y;
		else 
			return  (wv.vy1 - (y - wv.vy0));
	}
	
	public double logicToView(double h){
		UPoint v0 = m.logicToView(0,0,0);
		UPoint v1 = m.logicToView(h,h,0);
		double x = v1.x - v0.x;
		double y = v1.y - v1.y;
		return Math.sqrt(x*x+y*y);
	}
	
	public UPoint logicToView(double x, double y, double z) {
		UPoint vp = m.logicToView(x,y,z);
		vp.y = reverseYValue(vp.y);
		return vp;
	}
	public URect logicToView(double x1, double y1, double z1, double x2, double y2, double z2) {
		UPoint vp0 = m.logicToView(x1,y1,z1);
		UPoint vp1 = m.logicToView(x2,y2,z2);
		vp0.y = reverseYValue(vp0.y);
		vp1.y = reverseYValue(vp1.y);		
		URect r;
		if(vp0.y > vp1.y) {
			return new URect(vp0.x,vp1.y,vp1.x-vp0.x+1, vp0.y-vp1.y+1);
		}else
			return new URect(vp0.x,vp0.y,vp1.x-vp0.x+1, vp1.y-vp0.y+1);
	}

	
	public  UFPoint viewToLogic(Point p) {
//		return m.viewToLogic(p)
		double xt, yt;
		xt =  (m.rm[0][0] * p.x + m.rm[0][1] * p.y + m.rm[0][3]);
		yt =  (m.rm[1][0] * p.x + m.rm[1][1] * p.y + m.rm[1][3]);
		return new UFPoint(xt, yt);
	}	
}

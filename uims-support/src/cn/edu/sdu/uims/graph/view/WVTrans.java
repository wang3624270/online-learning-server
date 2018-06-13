package cn.edu.sdu.uims.graph.view;

import java.io.Serializable;

import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.UPoint;

public class WVTrans implements Serializable {
	public boolean rightHanded = false;
	public double wx0, wx1, wy0,wy1;
	public double vx0, vy0, vx1,vy1;
	public UMatrix m;
	public WVTrans(){
		m = new UMatrix();
	}
	public void setWindows(double wx0, double wy0, double wx1,double wy1){
		this.wx0 = wx0;
		this.wy0 = wy0;
		this.wx1 = wx1;
		this.wy1 = wy1;
	}
	public void setViewport(double vx0, double vy0, double vx1,double vy1){
		this.vx0 = vx0;
		this.vy0 = vy0;
		this.vx1 = vx1;
		this.vy1 = vy1;
	}

	
	public void makeWVMatrix(){
		double a = (vx1-vx0)/(wx1-wx0);
		double b = (vy1-vy0)/(wy1-wy0);
		m.m[0][0] = a;
		m.m[0][1] = 0;
		m.m[0][2] = 0;
		m.m[0][3] = vx0-a*wx0;
		m.m[1][0] = 0;
		m.m[1][1] = b;
		m.m[1][2] = 0;
		m.m[1][3] = vy0-b*wy0;	
		m.m[2][0] = 0;
		m.m[2][1] = 0;
		m.m[2][2] = 1;
		m.m[2][3] = 0;	
		m.m[3][0] = 0;
		m.m[3][1] = 0;
		m.m[3][2] = 0;
		m.m[3][3] = 1;	
	}
	public UPoint viewToPoint(UFPoint fp) {
		UPoint p = new  UPoint();
		p.x = (int)(m.m[0][0] *fp.x + m.m[0][1] * fp.y + m.m[0][3]);
		p.y = (int)(m.m[1][0] *fp.x + m.m[1][1] * fp.y + m.m[1][3]);
		if(this.rightHanded) {
			p.y = (int)(vy1-(p.y-vy0));
		}
		return p;
	}
}

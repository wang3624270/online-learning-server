package cn.edu.sdu.uims.trans;

import java.io.Serializable;

public class UFRect implements Serializable{
	public double x, y, w,h;
	public UFRect(){
		this(0,0,0,0);
	}
	public UFRect(double x,double y,double w, double h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public void setUFRect(double x,double y,double w, double h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public void setUFRect(UFRect r){
		this.x = r.x;
		this.y = r.y;
		this.w = r.w;
		this.h = r.h;
	}
	public void Union(UFRect r){
		double x1, y1,x2,y2;
		x1 = (x < r.x) ? x:r.x;
		y1 = (y < r.y) ? y:r.y;
		x2 = (x+w > r.x + r.w) ? x+w :r.x+r.w;
		y2 = (y+h > r.y + r.h) ? y+h :r.y+r.h;
		x = x1;
		y = y1;
		w = x2-x1;
		h = y2-y1;
	}
}

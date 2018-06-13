package cn.edu.sdu.uims.trans;

import java.io.Serializable;

public class URect implements Serializable{
	public int x, y, w,h;
	public URect(int x,int y,int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public void setURect(int x,int y,int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public void setURect(URect r){
		this.x = r.x;
		this.y = r.y;
		this.w = r.w;
		this.h = r.h;
	}
	public void Union(URect r){
		int x1, y1,x2,y2;
		x1 = (x < r.x) ? x:r.x;
		y1 = (y < r.y) ? y:r.y;
		x2 = (x+w > r.x + r.w) ? x+w :r.x+r.w;
		y2 = (y+h > r.y + r.h) ? y+h :r.y+r.h;
		r.x = x1;
		r.y = y1;
		r.w = x2-x1;
		r.h = y2-y1;
	}
}

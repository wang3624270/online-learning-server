package cn.edu.sdu.uims.trans;

import java.io.Serializable;

public class UFPoint implements Serializable {
	public double x, y, z;
	public UFPoint(){
		
	}
	public UFPoint(double x,double y){
		this.x = x;
		this.y = y;
		this.z = 0;
	}
	public void setUFPoint(double x, double y){
		this.x = x;
		this.y = y;
		this.z = 0;
	}
	public void setUFPoint(UFPoint p){
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}
	
}

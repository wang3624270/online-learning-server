package cn.edu.sdu.uims.trans;

import java.io.Serializable;

public class UPoint implements Serializable{
	public int x, y;
	public UPoint(){
		
	}
	public UPoint(int x,int y){
		this.x = x;
		this.y = y;
	}
	public void setUPoint(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void setUPoint(UPoint p){
		this.x = p.x;
		this.y = p.y;
	}
}

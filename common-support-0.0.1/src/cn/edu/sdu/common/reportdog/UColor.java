package cn.edu.sdu.common.reportdog;

import java.awt.Color;
import java.io.Serializable;

public class UColor implements Serializable {
	public int r, g, b;
	public transient Color color;

	public UColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public UColor(int r, int g, int b, String name) {
		this(r, g, b);
	}
	public void getObject(){
		if(color == null)
			color = new  Color(r,g,b);
	}
}

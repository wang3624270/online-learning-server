package cn.edu.sdu.uims.def;

import java.io.Serializable;

public class UBaseTemplate implements Serializable{

	protected String backGroud;
	public String getvAlign() {
		return vAlign;
	}

	public void setvAlign(String vAlign) {
		this.vAlign = vAlign;
	}

	protected String style ;
	protected String height;
	protected String width;
	protected String x;
	protected String y;
	protected String align = "left";
	protected String vAlign = "top";
	public String getVAlign() {
		return vAlign;
	}

	public void setVAlign(String align) {
		vAlign = align;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getBackGroud() {
		return backGroud;
	}

	public void setBackGroud(String backGroud) {
		this.backGroud = backGroud;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
	
	
}

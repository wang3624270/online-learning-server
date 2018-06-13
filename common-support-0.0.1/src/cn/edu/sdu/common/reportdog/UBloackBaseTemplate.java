package cn.edu.sdu.common.reportdog;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class UBloackBaseTemplate extends UTemplate {
	public String style ;
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getsX() {
		return sX;
	}
	public void setsX(String sX) {
		this.sX = sX;
	}
	public String getsY() {
		return sY;
	}
	public void setsY(String sY) {
		this.sY = sY;
	}
	public String getsHeight() {
		return sHeight;
	}
	public void setsHeight(String sHeight) {
		this.sHeight = sHeight;
	}
	public String getsWidth() {
		return sWidth;
	}
	public void setsWidth(String sWidth) {
		this.sWidth = sWidth;
	}
	public String sX, sY, sHeight, sWidth;
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		style = readString(in);
		sX = readString(in);
		sY = readString(in);
		sHeight = readString(in);
		sWidth = readString(in);
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeString(out, style) ;
		writeString(out, sX);
		writeString(out, sY);
		writeString(out, sHeight);
		writeString(out, sWidth);
	}
}

package cn.edu.sdu.common.reportdog;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;


public class UBlockTemplate extends UBloackBaseTemplate {
	public String fontName = null;
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public int getBefore() {
		return before;
	}
	public void setBefore(int before) {
		this.before = before;
	}
	public int getAfter() {
		return after;
	}
	public void setAfter(int after) {
		this.after = after;
	}
	public int getHorizontalAlignment() {
		return horizontalAlignment;
	}
	public void setHorizontalAlignment(int horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}
	public int getVerticalAlignment() {
		return verticalAlignment;
	}
	public void setVerticalAlignment(int verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}
	public float getLeading() {
		return leading;
	}
	public void setLeading(float leading) {
		this.leading = leading;
	}
	public String colorName = null;
	public int before = 0;
	public int after = 0;
	public int horizontalAlignment = 0;
	public int verticalAlignment = 0;
	public float leading=-1;
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		fontName = readString(in);
		colorName = readString(in);
		before = in.readInt();
		after = in.readInt();
		horizontalAlignment = in.readInt();
		verticalAlignment = in.readInt();
		leading = in.readFloat();
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeString(out, fontName);
		writeString(out, colorName);
		out.writeInt(before);
		out.writeInt(after);
		out.writeInt(horizontalAlignment);
		out.writeInt(verticalAlignment);
		out.writeFloat(leading);
	}
}

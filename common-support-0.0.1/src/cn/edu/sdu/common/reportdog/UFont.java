package cn.edu.sdu.common.reportdog;

import java.awt.Font;
import java.io.Serializable;

public class UFont implements Serializable{
	public String fontName;
	public  int stly;
	public int size;
	public String fileName;
	public transient Font font = null;	
	public UFont(String fontName, int style, int size,String fName){
		this.fontName = fontName;
		this.stly = style;
		this.size = size;
		if(fName != null) {
			this.fileName = fName;
		}
		else {
			this.fileName = "cn/edu/sdu/reportfont/simsun.ttf";
		}
	}
	public void getObject(){
		if(font == null)
			font = new Font(fontName,stly,size);
	}
}

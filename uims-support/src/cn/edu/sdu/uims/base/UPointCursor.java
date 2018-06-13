package cn.edu.sdu.uims.base;

import java.awt.Cursor;
import java.awt.Point;
import java.io.Serializable;

public class UPointCursor implements Serializable{
	public String name;
	public transient Cursor cursor = null;
	public UPointCursor(){
		name = "default";
		cursor = Cursor.getDefaultCursor();
	}
	public UPointCursor(String nane, int intvalue, String imageName, Point p){
		this.name = name;
		if(imageName == null) {
			cursor = new Cursor(intvalue);
		}
		else {
			
		}
	}
}

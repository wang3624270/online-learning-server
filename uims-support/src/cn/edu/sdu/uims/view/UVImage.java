package cn.edu.sdu.uims.view;

import java.awt.Graphics;
import java.awt.Image;

import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.URect;

public class UVImage extends UVElement {
	private Image img = null;
	
	public UVImage(){
	}
	public void draw(Graphics g, UMatrix m) {
		if(img == null)
			return;
		super.draw(g,m);
		URect rs = new URect(x,y,w,h); 
		URect rd;
		rd = m.logicToView(rs);
		g.drawImage(img, rd.x,rd.y ,rd.w,rd.h, null);
		
	}
	public Object getData(){
		return img;
	}
	public void setData(Object obj){
		if(obj instanceof Image){
		img = (Image)obj;
		}
	}
}

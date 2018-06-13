package cn.edu.sdu.uims.frame.def;

import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.sdu.uims.def.UElementTemplate;

public class UToolbarTemplate extends UElementTemplate {
	public String selectMode;
	public String fontName ;
	public String colorName ;
	public int alignment;
	public int labelAlignment;
	public int width = 30, height =30,labelWidth = 0, labelHeight = 0;
	public int hgap = 3,  vgap = 3;
	public UToolTemplate items[];

	public int row= 1, col=0;//toolbar的行和列
	
	public boolean floatable = false;//toolbar是否可浮动
	public boolean defaultEnable = false;
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		fontName = readString(in);
		int len, i;
		len = in.readInt();
		if(len == 0)
			items = null;
		else {
			items  = new UToolTemplate[len];
			for(i =0; i < len; i++) {
				items[i] = (UToolTemplate)readTemplate(in);
			}
		}
		row = in.readInt();
		col = in.readInt();
		floatable = in.readBoolean();
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeString(out, fontName);
		int i;
		if(items == null)
			out.writeInt(0);
		else {
			out.writeInt(items.length);
			for(i = 0; i < items.length;i++)
				writeTemplate(out, items[i]);
		}
		out.writeInt(row);
		out.writeInt(col);
		out.writeBoolean(floatable);
	}
	public Dimension getSize(){
		int w = col*width + (col-1)*this.hgap+ 10;
		int h = row*height + (row-1)*this.vgap +2;
		return new Dimension(w,h);
	}
}

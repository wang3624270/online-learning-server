package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

import cn.edu.sdu.uims.service.UFactory;

public class UButtonGroupTemplate extends UElementTemplate {
	public int colnum, rownum;
	public int comHeight;
	public int comWidth;

	public int getColnum() {
		return colnum;
	}
	public void setColnum(int colnum) {
		this.colnum = colnum;
	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public UButtonTemplate[] getButtons() {
		return buttons;
	}
	public void setButtons(UButtonTemplate[] buttons) {
		this.buttons = buttons;
	}
	public UButtonTemplate buttons[];
	public void getSelfAttribute(Element e1){
		String str = e1.attributeValue("colnum");
		if(str != null)
			colnum = Integer.parseInt(str);
		str = e1.attributeValue("rownum");
		if(str != null)
			rownum = Integer.parseInt(str);
		buttons = UFactory.getModelSession().getButtons(e1);
		dictionary = e1.attributeValue("dictionary");
		str = e1.attributeValue("comHeight");
		if(str == null)
			comHeight = h;
		else
			comHeight = Integer.parseInt(str);
		str = e1.attributeValue("comWidth");
		if(str == null)
			comWidth = -1;
		else
			comWidth = Integer.parseInt(str);
		
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		colnum = in.readInt();
		rownum = in.readInt();
		int len,i;
		len = in.readInt();
		if(len == 0)
			buttons = null;
		else {
			buttons = new UButtonTemplate[len];
			for(i = 0; i < len;i++)
				buttons[i] = (UButtonTemplate)readTemplate(in);
		}
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(colnum);
		out.writeInt(rownum);
		int i;
		if(buttons== null)
			out.writeInt(0);
		else {
			out.writeInt(buttons.length);
			for(i = 0; i < buttons.length;i++) 
				writeTemplate(out, buttons[i]);
		}
	}
}

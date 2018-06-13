package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

public class ULabelMultiLineTemplate extends ULabelTitleTemplate {
	public int charMax =80;
	public boolean fixHeight = true;
	public int getCharMax() {
		return charMax;
	}
	public void setCharMax(int charMax) {
		this.charMax = charMax;
	}
	public int getLineMax() {
		return lineMax;
	}
	public void setLineMax(int lineMax) {
		this.lineMax = lineMax;
	}
	public int lineMax =1;
	public void getSelfAttribute(Element e1){
		String str;
		super.getSelfAttribute(e1);
		str = e1.attributeValue("lineMax");
		if(str != null)
			lineMax = Integer.parseInt(str);
		str = e1.attributeValue("charMax");
		if(str!= null)
		  charMax = Integer.parseInt(str);
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		charMax = in.readInt();
		lineMax = in.readInt();
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(charMax);
		out.writeInt(lineMax);
	}
}

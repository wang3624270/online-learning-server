package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.dom4j.Element;

import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.service.UFactory;

public class UComboBoxDateTemplate extends UElementTemplate {

	public int style =-1;
	public boolean defaultCurrentDate = true;
	
	public int getStyle() {
		return style;
	}
	public void setStyle(int style) {
		this.style = style;
	}
	public void getSelfAttribute(Element e1) {
		String str;
		name = e1.attributeValue("name");
		str = e1.attributeValue("defaultCurrentDate");
		if(str !=null && str.equals("false"))
			defaultCurrentDate = false;
		str = e1.attributeValue("style");
		if (str != null) {
			HashMap constantsMap = UFactory.getModelSession().getConstantsMap();
			HashMap dateStyle=(HashMap)constantsMap.get(UConstants.TYPEDEF_DATE_STYLE);
			String style_str=(String)dateStyle.get(str);
			if(style_str!=null)
			style=Integer.parseInt(style_str);
		}
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		style = in.readInt();
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(style);
	}

}

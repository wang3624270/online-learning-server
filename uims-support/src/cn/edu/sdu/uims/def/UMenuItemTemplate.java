package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

import cn.edu.sdu.uims.constant.UConstants;


public class UMenuItemTemplate extends UButtonTemplate{
	public Integer menuNo;
	public String csmType;
	public int type = UConstants.COMPONENT_MENU_ITEM;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
	public boolean isDefaultSelected() {
		return defaultSelected;
	}
	public void setDefaultSelected(boolean defaultSelected) {
		this.defaultSelected = defaultSelected;
	}
	public String handler;
	public boolean defaultSelected;//缺省是否选中
        
	public void getSelfAttribute(Element e1){

		super.getSelfAttribute(e1);

		name = e1.attributeValue("name");
			content = name;
			cmd = name;
			String str=null;
			str = e1.attributeValue("content");
			if (str != null)
				content = str;
			str = e1.attributeValue("cmd");
			if (str != null)
				cmd = str;

			str = e1.attributeValue("handler");
			if (str != null)
				handler = str;

			str = e1.attributeValue("defaultSelected");
			if (str != null) {
				defaultSelected = str != null ? true
						: false;
			}
	}	
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		type = in.readInt();
		handler = readString(in);
		defaultSelected = in.readBoolean();
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(type);
		writeString(out,handler);
		out.writeBoolean(defaultSelected);

	}
}

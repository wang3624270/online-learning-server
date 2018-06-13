package cn.edu.sdu.common.reportdog;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import cn.edu.sdu.common.form.UFormI;

public class UCellAttribute extends UTemplate{

	public String content;
	public String enContent;
	public boolean editable = true;
	public String  fieldClassName = null;		
	public String filter=null,filter1=null;
	public String borderName = null;
	public String fontName = null;
	public String backColorName = null;
	public String frontColorName = null;
	public int row, col, rowSpan=1, colSpan=1;
	public int verticalAlignment = 0; 
	public int horizontalAlignment = 0;
	public int indent = 0;
	public int x, y, w, h;
	
	public int fixHeight=0;


	public UCellAttribute(){
		this("");
	}
	public UCellAttribute(String content){
		this.content = content;		
	}
	public static String getReplaceString(String str, HashMap map, UFormI form,
			Object obj) {
		String methodName = null;
		Method method;
		String ret = null;
		if (map != null) {
			ret = (String) map.get(str);
		}
		methodName = "get" + str.substring(0, 1).toUpperCase()
				+ str.substring(1, str.length());
		if (ret == null && form != null) {
			try {
				method = form.getClass().getMethod(methodName);
				ret = method.invoke(form).toString();
			} catch (Exception e) {
				ret = null;
			}
		}
		if (ret == null && obj != null) {
			try {
				method = obj.getClass().getMethod(methodName);
				ret = method.invoke(obj).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (ret == "null")
			ret = "";
		return ret;
	}

	public void setParameterData(HashMap map, UFormI form, Object obj) {
//		content = UTextUtil.replaceString(content, map, form, obj);
//		enContent = UTextUtil.replaceString(enContent, map, form, obj);
	}
	public UCellAttribute( UCellAttribute o){
		this.content = o.content;
		this.enContent = o.enContent;
		this.editable = o.editable;
		this.fieldClassName = o.fieldClassName;
		this.filter = o.filter;
		this.filter1 = o.filter1;
		this.borderName = o.borderName;
		this.fontName = o.fontName;
		this.backColorName = o.backColorName;
		this.frontColorName = o.frontColorName;
		this.row = o.row;
		this.col = o.col;
		this.rowSpan = o.rowSpan;
		this.colSpan = o.colSpan;
		this.verticalAlignment = o.verticalAlignment;
		this.horizontalAlignment = o.horizontalAlignment;
		this.indent = o.indent;
		this.x = o.x;
		this.y = o.y;
		this.w = o.w;
		this.h = o.h;
		this.fixHeight = o.fixHeight;
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		content = readString(in);
		enContent = readString(in);
		editable = in.readBoolean();
		fieldClassName  = readString(in);		
		filter = readString(in);
		filter1  =readString(in);
		borderName  = readString(in);
		fontName  = readString(in);
		backColorName  = readString(in);
		frontColorName  = readString(in);
		row = in.readInt(); 
		col = in.readInt();
		rowSpan = in.readInt();
		colSpan = in.readInt();
		verticalAlignment  = in.readInt(); 
		horizontalAlignment = in.readInt();
		indent = in.readInt();
		x = in.readInt();
		y = in.readInt();
		w = in.readInt();
		h = in.readInt();
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeString(out, content);
		writeString(out, enContent);
		out.writeBoolean(editable);
		writeString(out,fieldClassName);		
		writeString(out,filter);
		writeString(out,filter1);
		writeString(out,borderName);
		writeString(out,fontName);
		writeString(out,backColorName);
		writeString(out,frontColorName);
		out.writeInt(row); 
		out.writeInt(col);
		out.writeInt(rowSpan);
		out.writeInt(colSpan);
		out.writeInt(verticalAlignment); 
		out.writeInt(horizontalAlignment);
		out.writeInt(indent);
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(w);
		out.writeInt(h);
	}	
}
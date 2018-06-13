package cn.edu.sdu.common.reportdog;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.dom4j.Element;

import cn.edu.sdu.common.form.ListOptionInfo;

public class UTemplate implements Serializable {
	public String name, className;

	public Object newComponent() {
		Object obj = null;
		try {
			obj = (Object) Class.forName(className).newInstance();
		} catch (Exception e) {
			obj = null;
			System.out.println("classname = " + className);
			e.printStackTrace();
		}
		return obj;
	}

	public UTemplate() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	// //////////////////////////////////

	public void copy(UTemplate temp) {
		name = temp.name;
		className = temp.className;
	}

	public void read(DataInputStream in) throws IOException {
		name = readString(in);
		className = readString(in);
	}

	public void write(DataOutputStream out) throws IOException {
		writeString(out, name);
		writeString(out, className);
	}

	public UTemplate readTemplate(DataInputStream in) throws IOException {
		UTemplate template = null;
		String objectName = readString(in);
		if (!objectName.equals("null")) {
			try {
				template = (UTemplate) Class.forName(objectName).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			template.read(in);
		}
		return template;
	}

	public String readString(DataInputStream in) throws IOException {
		String s;
		int len = in.readInt();
		if (len == 0)
			return null;
		byte b[] = new byte[len];
		in.read(b);
		s = new String(b);
		return s;
	}

	public void writeString(DataOutputStream out, String s) throws IOException {
		if (s == null)
			out.writeInt(0);
		else {
			byte[] b = s.getBytes();
			out.writeInt(b.length);
			out.write(b);
		}
	}

	public void writeTemplate(DataOutputStream out, UTemplate template)
			throws IOException {
		if (template == null)
			writeString(out, "null");
		else {
			String objectName = template.getClass().getName();
			writeString(out, objectName);
			template.write(out);
		}
	}

	public ListOptionInfo readListOptionInfo(DataInputStream in)
			throws IOException {
		ListOptionInfo info = new ListOptionInfo();
		info.setLabel(readString(in));
		info.setValue(readString(in));
		info.setParentValue(readString(in));
		return info;
	}

	public void writeListOptionInfo(DataOutputStream out, ListOptionInfo info)
			throws IOException {
		writeString(out, info.getLabel());
		writeString(out, info.getValue());
		writeString(out, info.getParentValue());
	}
	public int geIntValueFromXml(Element e, String name,int defaultValue){
		String str = e.attributeValue(name);
		if(str == null || str.length() == 0){
			return defaultValue;
		}else {
			return Integer.parseInt(str);
		}
	}
}

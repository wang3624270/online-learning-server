package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

public class UImageLabelTemplate extends UElementTemplate {
	public String fileName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void getSelfAttribute(Element e1){
		name = e1.attributeValue("name");
		fileName = e1.attributeValue("fileName");
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		fileName = readString(in);
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeString(out, fileName);
	}
}

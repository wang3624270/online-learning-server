package cn.edu.sdu.uims.def;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

public class ULabelTitleTemplate extends UElementTemplate {
	public String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void getSelfAttribute(Element e1){
		name = e1.attributeValue("name");
		title = e1.attributeValue("title");
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		title = readString(in);
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeString(out,title);
	}
}

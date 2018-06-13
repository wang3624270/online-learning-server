package cn.edu.sdu.uims.itms.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

import cn.edu.sdu.common.reportdog.UTemplate;

public class IEventorTemplate extends UTemplate {
	public void setAttribute(Element ele) {
		className = ele.attributeValue("className");
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
	}
}

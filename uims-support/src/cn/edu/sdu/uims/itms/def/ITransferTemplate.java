package cn.edu.sdu.uims.itms.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

import cn.edu.sdu.common.reportdog.UTemplate;

public class ITransferTemplate extends UTemplate {
	public String eventType;
	public String nextStatusName;
	public String actionName;
	public String eventorName;

	public void setAttribute(Element ele) {
		name = ele.attributeValue("name");
		eventType = ele.attributeValue("eventType");
		nextStatusName = ele.attributeValue("next");
		actionName = ele.attributeValue("action");
		eventorName = ele.attributeValue("eventor");
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		eventType = readString(in);
		nextStatusName = readString(in);
		actionName = readString(in);
		eventorName = readString(in);
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeString(out, eventType);
		writeString(out, nextStatusName);
		writeString(out, actionName);
		writeString(out, eventorName);
	}

}

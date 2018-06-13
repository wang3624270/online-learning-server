package cn.edu.sdu.uims.itms.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Element;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.itms.ItmsConstants;

public class ITaskTemplate extends UTemplate {
	public int opType;
	public String dataFormName;
	public ArrayList taskList;
	public HashMap statusMapping;

	public void setAttribute(Element ele) {
		String str;
		String key, fileName;
		ISubTaskTemplate ut;
		if (className == null)
			className = ItmsConstants.DEFAULY_CLASSNAME_TASK;
		str = ele.attributeValue("op");
		if (str.equals(ItmsConstants.OPERATE_TYPE_STRING_SEQUENCE)) {
			opType = ItmsConstants.OPERATE_TYPE_INT_SEQUENCE;
		} else {
			opType = ItmsConstants.OPERATE_TYPE_INT_SELECT;
		}
		dataFormName = ele.attributeValue("dataForm");
		if (dataFormName == null)
			dataFormName = ItmsConstants.DEFAULY_CLASSNAME_FORM;

		taskList = new ArrayList();

		Iterator it2, it3;
		Element e, ee;
		it2 = ele.elementIterator("subTask");
		while (it2.hasNext()) {
			e = (Element) it2.next();
			ut = new ISubTaskTemplate();
			ut.setAttribute(e);
			taskList.add(ut);
		}
		statusMapping = new HashMap();
		it2 = ele.elementIterator("statusMapping");
		while (it2.hasNext()) {
			e = (Element) it2.next();
			it3 = e.elementIterator("item");
			while (it3.hasNext()) {
				ee = (Element) it3.next();
				statusMapping.put(ee.attributeValue("from"), ee
						.attributeValue("to"));
			}
		}
	}

	public void read(DataInputStream in) throws IOException {
		super.read(in);
		opType = in.readInt();
		dataFormName = readString(in);
		int len, i;
		UTemplate t;
		len = in.readInt();
		if (len == 0)
			taskList = null;
		else {
			taskList = new ArrayList();
			for (i = 0; i < len; i++) {
				t = readTemplate(in);
				taskList.add(t);
			}
		}
		len = in.readInt();
		String from, to;
		if (len == 0)
			statusMapping = null;
		else {
			statusMapping = new HashMap();
			for (i = 0; i < len; i++) {
				from = readString(in);
				to = readString(in);
				statusMapping.put(from, to);
			}
		}
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(opType);
		writeString(out, dataFormName);
		int i;
		if (taskList == null)
			out.writeInt(0);
		else {
			out.writeInt(taskList.size());
			for (i = 0; i < taskList.size(); i++) {
				writeTemplate(out, (ISubTaskTemplate) taskList.get(i));
			}
		}
		if (statusMapping == null) {
			out.writeInt(0);
		} else {
			String from, to;
			Iterator it = statusMapping.keySet().iterator();
			out.writeInt(statusMapping.size());
			while (it.hasNext()) {
				from = (String) it.next();
				to = (String) statusMapping.get(from);
				writeString(out, from);
				writeString(out, to);
			}
		}
	}

}

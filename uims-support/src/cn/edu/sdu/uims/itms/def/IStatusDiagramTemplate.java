package cn.edu.sdu.uims.itms.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Element;

import cn.edu.sdu.common.reportdog.UTemplate;

public class IStatusDiagramTemplate extends UTemplate {
	public String cursorName;
	public ArrayList statusList;

	public void setAttribute(Element ele) {
		Element statusEle;
		Iterator it1, it2;
		IStatusTemplate st;
		statusList = new ArrayList();
		it2 = ele.elementIterator("status");
		while (it2.hasNext()) {
			statusEle = (Element) it2.next();
			st = new IStatusTemplate();
			st.setAttribute(statusEle);
			statusList.add(st);
		}

	}

	public void read(DataInputStream in) throws IOException {
		super.read(in);
		cursorName = readString(in);
		int len, i;
		UTemplate t;
		len = in.readInt();
		if (len == 0) {
			statusList = null;
		} else {
			statusList = new ArrayList();
			for (i = 0; i < len; i++) {
				t = readTemplate(in);
				statusList.add(t);
			}
		}
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeString(out, cursorName);
		if (statusList == null) {
			out.writeInt(0);
		} else {
			out.writeInt(statusList.size());
			for (int i = 0; i < statusList.size(); i++)
				writeTemplate(out, (IStatusTemplate) statusList.get(i));
		}
	}

}

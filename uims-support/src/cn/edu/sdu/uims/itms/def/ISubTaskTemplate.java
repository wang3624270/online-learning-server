package cn.edu.sdu.uims.itms.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Element;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.itms.ItmsConstants;

public class ISubTaskTemplate extends UTemplate {
	public String cursorName, cursorDataName, dataMember, templateName,
			multiExeStartStatus, nextSubStartStatus;
	public UTemplate outDefineTemplate = null;
	public int type;
	public int executeTimes;
	public ArrayList transList;
	public int lastPointIndex;// 当任务切换或者点顺序执行的时候，下个子任务需要的最后一个点在这次任务点集中的位置,默认-1

	// 是最后一个点

	public void setAttribute(Element ele) {
		String str;
		name = ele.attributeValue("name");
		str = ele.attributeValue("type");
		if (str.equals(ItmsConstants.TASK_TYPE_STRING_TASK)) {
			type = ItmsConstants.TASK_TYPE_INT_TASK;
		} else {
			type = ItmsConstants.TASK_TYPE_INT_DIAGRAM;
		}
		cursorName = ele.attributeValue("cursor");
		cursorDataName = ele.attributeValue("cursorData");
		dataMember = ele.attributeValue("dataMember");
		templateName = ele.attributeValue("templateName");
		multiExeStartStatus = ele.attributeValue("multiExeStartStatus");
		nextSubStartStatus = ele.attributeValue("nextSubStartStatus");

		str = ele.attributeValue("executeTimes");
		if (str == null) {
			executeTimes = 1;
		} else {
			executeTimes = Integer.parseInt(str);
		}

		str = ele.attributeValue("lastPointIndex");
		if (str == null) {
			lastPointIndex = -1;
		} else {
			lastPointIndex = Integer.parseInt(str);
		}
		ITransferTemplate tt;
		Element tEle;
		Iterator it2;
		transList = new ArrayList();
		it2 = ele.elementIterator("trans");
		while (it2.hasNext()) {
			tEle = (Element) it2.next();
			tt = new ITransferTemplate();
			tt.setAttribute(tEle);
			transList.add(tt);
		}
	}

	public void read(DataInputStream in) throws IOException {
		super.read(in);
		cursorName = readString(in);
		cursorDataName = readString(in);
		dataMember = readString(in);
		templateName = readString(in);
		multiExeStartStatus = readString(in);
		nextSubStartStatus = readString(in);
		outDefineTemplate = readTemplate(in);
		type = in.readInt();
		executeTimes = in.readInt();
		int len, i;
		UTemplate t;
		len = in.readInt();
		if (len == 0) {
			transList = null;
		} else {
			transList = new ArrayList();
			for (i = 0; i < len; i++) {
				t = readTemplate(in);
				transList.add(t);
			}
		}
		lastPointIndex = in.readInt();
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeString(out, cursorName);
		writeString(out, cursorDataName);
		writeString(out, dataMember);
		writeString(out, templateName);
		writeString(out, multiExeStartStatus);
		writeString(out, nextSubStartStatus);
		writeTemplate(out, outDefineTemplate);
		out.writeInt(type);
		out.writeInt(executeTimes);
		if (transList == null)
			out.writeInt(0);
		else {
			out.writeInt(transList.size());
			for (int i = 0; i < transList.size(); i++)
				writeTemplate(out, (ITransferTemplate) transList.get(i));
		}
		out.writeInt(lastPointIndex);
	}
}

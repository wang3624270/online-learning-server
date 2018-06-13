package cn.edu.sdu.uims.itms.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Element;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.itms.ItmsConstants;

public class IStatusTemplate extends UTemplate {
	public int type;
	public ArrayList transList;
	public String transMapAction;
	public boolean esc, help;
	public String userSwitch;

	public void setAttribute(Element ele) {
		String str;
		name = ele.attributeValue("name");
		str = ele.attributeValue("type");
		if (str.equals(ItmsConstants.STATUS_TYPE_STRING_START)) {
			type = ItmsConstants.STATUS_TYPE_INT_START;
		} else if (str.equals(ItmsConstants.STATUS_TYPE_STRING_END)) {
			type = ItmsConstants.STATUS_TYPE_INT_END;
		} else {
			type = ItmsConstants.STATUS_TYPE_INT_NORMAL;
		}
		str = ele.attributeValue("esc");
		esc = (str != null && str.equals("true")) ? true : false;

		str = ele.attributeValue("help");
		help = (str != null && str.equals("true")) ? true : false;

		userSwitch = ele.attributeValue("userSwitch");

		transMapAction = ele.attributeValue("transMapAction");
		transList = new ArrayList();
		Element tEle;
		Iterator it1, it2;
		ITransferTemplate tt;
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
		type = in.readInt();
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
		transMapAction = readString(in);
		esc = in.readBoolean();
		help = in.readBoolean();
		userSwitch = readString(in);
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(type);
		if (transList == null) {
			out.writeInt(0);
		} else {

			out.writeInt(transList.size());
			for (int i = 0; i < transList.size(); i++)
				writeTemplate(out, (ITransferTemplate) transList.get(i));
		}
		writeString(out, transMapAction);
		out.writeBoolean(esc);
		out.writeBoolean(help);
		writeString(out, userSwitch);
	}

}

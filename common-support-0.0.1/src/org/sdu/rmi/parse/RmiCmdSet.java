package org.sdu.rmi.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.sdu.rmi.RmiCmdHelper;

public class RmiCmdSet extends RmiModel {
	List<RmiCmdSet> childCmdSetList;

	public List<RmiCmdSet> getChildCmdSetList() {
		return childCmdSetList;
	}

	public void setChildCmdSetList(List<RmiCmdSet> childCmdSetList) {
		this.childCmdSetList = childCmdSetList;
	}

	HashMap<String, Cmd> cmdMap;

	public HashMap<String, Cmd> getCmdMap() {
		return cmdMap;
	}

	public void setCmdMap(HashMap<String, Cmd> cmdMap) {
		this.cmdMap = cmdMap;
	}

	public RmiCmdSet() {
		childCmdSetList = new ArrayList<RmiCmdSet>();
		cmdMap = new HashMap<String, Cmd>();
	}

	public void parse() {

		List<Element> list = currentElement.elements();
		int i;
		Element e;

		for (i = 0; i < list.size(); i++) {
			e = list.get(i);
			if (e.getName().equals("cmd-file")) {
				String path = e.attributeValue("path");
				URL dd = RmiCmdHelper.class.getResource(path);
				if (dd == null)
					continue;
				String ds = dd.getFile();
				File file = new File(ds);

				try {
					InputStream fileIn = new FileInputStream(file);

					SAXReader sb = new SAXReader();
					Document doc = (Document) sb.read(fileIn);

					RmiCmdSet set = new RmiCmdSet();
					set.currentElement = doc.getRootElement();
					set.parse();
					childCmdSetList.add(set);

				} catch (IOException | DocumentException ee) {
					ee.printStackTrace();
				}
			} else if (e.getName().equals("cmd")) {
				Cmd c = new Cmd();
				c.currentElement = e;
				c.parse();
				cmdMap.put(c.getKey(), c);
			}
		}

	}
}
package org.sdu.rmi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.sdu.rmi.parse.Cmd;
import org.sdu.rmi.parse.RmiCmdSet;

public class RmiCmdHelper {

	private String rmiCmdRootFilePath = null;
	private static RmiCmdSet rootRmiCmdSet = null;
	private static HashMap<String, Cmd> totalCmdMap = new HashMap<String, Cmd>();

	public static Cmd getTotalCmdMap(String key) {
		return totalCmdMap.get(key);
	}

	public RmiCmdHelper() {
		if (rmiCmdRootFilePath == null)
			rmiCmdRootFilePath = "rmi/rmi-cmd-root.xml";

		// URL dd =
		// String ds = dd.getFile();
		// File file = new File(ds);

		try {
			// InputStream fileIn = new FileInputStream(file);
			InputStream fileIn = RmiCmdHelper.class.getClassLoader().getResourceAsStream(rmiCmdRootFilePath);
			SAXReader sb = new SAXReader();
			Document doc = (Document) sb.read(fileIn);

			rootRmiCmdSet = new RmiCmdSet();
			rootRmiCmdSet.currentElement = doc.getRootElement();
			rootRmiCmdSet.parse();

		} catch (Exception e) {
			e.printStackTrace();
		}
		walkRmiCmdSet(rootRmiCmdSet, totalCmdMap);
	}

	private void walkRmiCmdSet(RmiCmdSet set, HashMap totalCmdMap) {
		totalCmdMap.putAll(set.getCmdMap());
		int i;
		for (i = 0; i < set.getChildCmdSetList().size(); i++) {

			walkRmiCmdSet(set.getChildCmdSetList().get(i), totalCmdMap);
		}

	}

	public String getRmiCmdRootFilePath() {
		return rmiCmdRootFilePath;
	}

	public void setRmiCmdRootFilePath(String rmiCmdRootFilePath) {
		this.rmiCmdRootFilePath = rmiCmdRootFilePath;
	}

}
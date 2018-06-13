package cn.edu.sdu.uims.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.edu.sdu.uims.def.UElementTemplate;

public class UXMLFileUtil {

	private SAXReader sb;

	private InputStream inputStream;

	private Document doc;

	private XMLWriter xmlWriter;

	private OutputStream outputStream;
	
	private Element rootElement;

	public UXMLFileUtil(String path) {
		super();
		// TODO Auto-generated constructor stub

		try {
			sb = new SAXReader();
			inputStream = new FileInputStream(path + "printpaneltemplate.xml");
			doc = sb.read(inputStream);

			outputStream = new FileOutputStream(path + "printpaneltemplate.xml");
			xmlWriter = new XMLWriter(outputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 修改printPanel的坐标和字体信息
	 * 
	 */
	public void modifiedPrintPanelItemInfo(UElementTemplate uelementTemplate,
			String panelName) {
		Iterator rootit = null, compsit = null, compit = null;
		Attribute attr = null;
		Element element = null;
		rootElement = doc.getRootElement();
		rootit = rootElement.elementIterator();
		while (rootit.hasNext()) {
			element = (Element) rootit.next();
			attr = element.attribute("name");
			if (attr != null && attr.getValue().equals(panelName)) {
				compsit = element.elementIterator();
				while (compsit.hasNext()) {
					element = (Element) compsit.next();
					if (element != null) {
						compit = element.elementIterator();
						while (compit.hasNext()) {
							element = (Element) compit.next();
							attr = element.attribute("name");
							if (attr != null) {
								if (attr.getValue().equals(
										uelementTemplate.name)) {
									attr = element.attribute("x");
									if (attr != null)
										attr.setValue(uelementTemplate.x + "");
									attr = element.attribute("y");
									if (attr != null)
										attr.setValue(uelementTemplate.y + "");
									attr = element.attribute("w");
									if (attr != null)
										attr.setValue(uelementTemplate.w + "");
									attr = element.attribute("h");
									if (attr != null)
										attr.setValue(uelementTemplate.h + "");
									attr = element.attribute("font");
									if (attr != null)
										attr
												.setValue(uelementTemplate.fontName);
								}
							}
						}
					}
				}
			}
		}
	}
	public void writeToXMLFile() {
		try {
			xmlWriter.write(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/*
	public static void main(String[] args) {
		UElementTemplate uelement = new UElementTemplate();
		uelement.name = "name";
		uelement.x = 100;
		uelement.y = 100;
		uelement.w = 100;
		uelement.h = 100;
		uelement.font = new UFont("font16", "bbb", 0, 0);
		String sysPath = System.getProperty("user.dir");
		UXMLFileUtil util = new UXMLFileUtil(sysPath
				+ "\\src\\uims\\configure\\");

		System.out.println(sysPath);
		util.modifiedPrintPanelItemInfo(uelement);
	} */
}

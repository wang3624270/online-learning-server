package cn.edu.sdu.uims.graph.model.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.edu.sdu.uims.graph.model.GraphModel2D;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.graph.model.data.xml.XmlGraphModelDataProcessor;

public class XmlGraphMoelUtils {
	public static GraphModelI getGraph2DModelByFile(File file) {
		Element root = null;
		Document doc = null;
		SAXReader sb = null;
		InputStream in = null;
		sb = new SAXReader();
		try {
			in = new FileInputStream(file);
			doc = sb.read(in);
			root = doc.getRootElement();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		GraphModelI g2d = new GraphModel2D();
		XmlGraphModelDataProcessor pi = new XmlGraphModelDataProcessor(g2d);
		pi.setObject(root);
		return g2d;
	}	
	public static void exportGraph2DModelToFile(GraphModelI g2d, File file) {
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("graphmodeltemplate");
		Element ge = rootElement.addElement("graph_model");
		g2d.exportElementToDoc(ge);
		try {
		// 对document格式化输出到指定名称文件, 格式化后，使xml符合节点缩进样式
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
			
			writer.write(document);
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static String changeAlignmentValueToString(int value){
		switch(value) {
		case 0:
			return "center";
		case 1:
			return "top";
		case 2:
			return "left";
		case 3:
			return "bottom";
		case 4:
			return "right";
		}
		return "";
	}
}

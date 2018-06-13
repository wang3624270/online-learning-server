package cn.edu.sdu.uims.constant;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/*********************************************
 * @author bhy
 * @function 用于定义通用的method的类 *
 * @version 1.1
 ******************************************/
public class UMethodConstants {
	/**
	 * 格式化输出的XML格式
	 * 
	 * @param content
	 *            xml的内容
	 * @return 被成功格式化的xml内容
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static String formatXmlContent(String content) {
		String formatContent = null;
		SAXReader reader = new SAXReader();
		StringReader in = new StringReader(content);
		Document doc;
		try {
			doc = reader.read(in);
			// 创建格式化输出格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			// 设置xml的输出码值
			format.setEncoding("utf-8");
			// 创建输出目标
			StringWriter out = new StringWriter();
			// 创建输出流
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(doc);
			writer.close();
			formatContent = out.toString();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建字符窜输入流
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formatContent;
	}

	public static int GRAPH_SELECT_STATUS_BOUND_TYPE = 1; // element选中的顶点情况
	
	public static int DRAG_ELEMENT_TYPE = 0; // 拖动element的类型
//	private static List points = new ArrayList(); // 存放原element的点的信息
//	public static List getPoints() {
//		return points;
//	}
//
//	public static void setPoints(List points) {
//		points = points;
//	}
}

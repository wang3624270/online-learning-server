package cn.edu.sdu.uims.frame;

import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JOptionPane;

public class MyProperties {
	private static String fileName = "client.properties";

	private static Properties properties = null;

	private static MyProperties propertiesInstance = new MyProperties();

	public final static String SERVER = "server";
	public final static String WEBSERVER = "webserver";

	public final static String IS_TAB = "isTab";

	public final static String IS_TREE_SHOW = "isTreeShow";
 

	/** 服务器启动类型——本地，不单独启动服务器，直接生成服务对象 */
	public final static String SERVERTYPE_LOCAL = "LOCAL";

	private MyProperties() {
		try {
			properties = new Properties();
			properties.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(fileName));
			// properties.load(this.getClass().getResourceAsStream(fileName));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "配置文件读取失败!");
			propertiesInstance = null;
		}
	}

	public static MyProperties createProperties() {
		return propertiesInstance;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

	public Enumeration propertyNames() {
		return properties.propertyNames();
	}

	public void savePropertiesToFile() {
		try {
			properties.store(new FileOutputStream(fileName), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

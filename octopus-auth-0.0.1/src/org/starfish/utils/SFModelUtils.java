package org.starfish.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.Element;

import org.dom4j.io.SAXReader;
import org.starfish.Starfish;
import org.starfish.pageside_access.PageAccess;
import org.starfish.pageside_access.URLAccess;
import org.starfish.pageside_access.menu.MenuNode;

public class SFModelUtils {
	public static void updateModelInDB(PageAccess pageAccess) {
		try {

			Connection connection = Starfish.starfish.getDataSource().getConnection();
			Statement statement = connection.createStatement();
			String sql = "update tp_npt_page_element_access set description='" + pageAccess.asXML() + "' where path='"
					+ pageAccess.getCurrentPagePath() + "'";
			statement.execute(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void updateModelInDB(URLAccess actionAccess) {
		// try {
		//
		// Connection connection = Nepenthes.nepenthes.getDataSource()
		// .getConnection();
		// Statement statement = connection.createStatement();
		// String sql = "update npt_struts_link_access set description='"
		// + actionAccess.asXML()
		// + "',updateTime=now() where package='"
		// + actionAccess.getPackageName() + "' and action='"
		// + actionAccess.getActionName() + "'";
		// statement.execute(sql);
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}

	public static void updateModelInDB(MenuNode menu) {
		try {

			Connection connection = Starfish.starfish.getDataSource().getConnection();
			Statement statement = connection.createStatement();
			String sql = "update starfish_menu_access set authDetail='" + menu.getMenuAccess().asXML()
					+ "',updateTime=now() where logicId='" + menu.getLogicId() + "'";
			statement.execute(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Element getRootElement(String des) {
		try {
			InputStream in = new ByteArrayInputStream(des.getBytes("UTF-8"));
			SAXReader sb = new SAXReader();
			Document doc = (Document) sb.read(in);
			return doc.getRootElement();
		} catch (Exception e) {
			return null;
		}
	}
}
package org.starfish;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.shandong_univ.grapedb.SessionRelatedDBManager;
import org.starfish.pageside_access.PageAccess;

public class StarfishPageConfigManager {
	public static HashMap<String, PageAccess> pageAccessMap;
	static {
		new StarfishPageConfigManager();
	}

	public StarfishPageConfigManager() {
		pageAccessMap = new HashMap<String, PageAccess>();
	}

	public static PageAccess getWebPageAccessConf() {

		return null;
	}

	public static PageAccess checkPageSF(String pagePath) {

		// if (pageAccessMap.get(pagePath) != null) {
		// return pageAccessMap.get(pagePath);
		// }

		try {
			Document doc = null;
			InputStream in = null;
			if (Starfish.starfish.getPageAccessConfigurePlace().equals(
					"database")) {
				String des = checkPageAccessDataSource(pagePath);
				if (des == null)
					return null;
				in = new ByteArrayInputStream(des.getBytes("UTF-8"));
				SAXReader sb = new SAXReader();
				doc = (Document) sb.read(in);
			} else if (Starfish.starfish.getPageAccessConfigurePlace().equals(
					"file")) {
				ExternalContext context = FacesContext.getCurrentInstance()
						.getExternalContext();
				String rPath = pagePath.substring(0, pagePath.lastIndexOf('/'));
				String pageName = pagePath
						.substring(pagePath.lastIndexOf('/') + 1);
				String pageName_f = pageName
						.substring(0, pageName.indexOf('.'));
				String nptName = pageName_f + ".npt.xml";
				URL url = ((HttpServletRequest) context.getRequest())
						.getServletContext().getResource(rPath + "/" + nptName);
				if (url == null)
					return null;
				File file = new File(url.toURI());
				in = new FileInputStream(file);
				SAXReader sb = new SAXReader();
				doc = (Document) sb.read(in);

			} else {
			}
			PageAccess pageAccess = new PageAccess(null);
			pageAccess.currentElement = doc.getRootElement();
			pageAccess.parse();
			pageAccessMap.put(pagePath, pageAccess);
			in.close();
			pageAccess.setCurrentPagePath(pagePath);
			return pageAccessMap.get(pagePath);
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	private static String checkPageAccessDataSource(String pagePath) {
		try {

			Connection connection = Starfish.starfish.getDataSource()
					.getConnection();
			Statement statement = connection.createStatement();
			String sql = "SELECT description FROM tp_npt_page_element_access n where path='"
					+ pagePath + "'";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String str = rs.getString(1);
				return str;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// public static String checkActionAccessDataSource(String packageName,
	// String actionName) {
	// try {
	//
	// Connection connection = Nepenthes.nepenthes.getDataSource()
	// .getConnection();
	// Statement statement = connection.createStatement();
	// String sql =
	// "SELECT description FROM npt_struts_link_access n where package='"
	// + packageName + "' and action='" + actionName + "'";
	// ResultSet rs = statement.executeQuery(sql);
	// while (rs.next()) {
	// String str = rs.getString(1);
	// return str;
	// }
	// return null;
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// }
	//
	// }

}
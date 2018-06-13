package org.shandong_univ.tenant;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.shandong_univ.tenant.constants.TenantConstants;

public class JSFTenantPropertiesAgent {

	private HashMap<String, HashMap<String, Properties>> propertiesMap;

	public JSFTenantPropertiesAgent() {
		propertiesMap = new HashMap<String, HashMap<String, Properties>>();
	}

	public String getProperites(String proertiesSet, String propertyName) {

		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		HttpSession session = ((HttpServletRequest) context.getRequest())
				.getSession();
		String str = (String) session
				.getAttribute(TenantConstants.TenantIdentity);
		if (str == null)
			return null;
		if (propertiesMap.get(str) == null)
			propertiesMap.put(str, new HashMap<String, Properties>());
		HashMap<String, Properties> m = propertiesMap.get(str);

		if (m.get(proertiesSet) == null) {
			loadTenantProperties(str, proertiesSet);
		}

		return (String) m.get(proertiesSet).get(propertyName);

	}

	private void loadTenantProperties(String tenantId, String propertiesSetName) {
		try {
			Connection connection = TenantConfigure.getDataSource()
					.getConnection();
			Statement statement = connection.createStatement();
			String sql = "select * from "
					+ TenantConfigure.getTenantConfigure().getTenantInfoDB()
					+ ".tenant_properties where tenantIdentity='" + tenantId
					+ "' and propertiesSetName='" + propertiesSetName + "'";
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			String str = rs.getString("propertiesContent");
			connection.close();
			Properties p = new Properties();
			InputStream in = new ByteArrayInputStream(str.getBytes("UTF-8"));
			Reader reader = new InputStreamReader(in, "UTF-8");
			p.load(reader);
			propertiesMap.get(tenantId).put(propertiesSetName, p);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
package org.shandong_univ.grapedb;

import java.io.StringReader;
import java.util.HashMap;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.shandong_univ.tenant.TenantConfigure;
import org.shandong_univ.tenant.constants.TenantConstants;

import net.sf.jsqlparser.parser.CCJSqlParserManager;

public class SessionRelatedDBManager {
	public static CCJSqlParserManager pm = new CCJSqlParserManager();
	public static final ThreadLocal<Boolean> sessionRelatedInThread = new ThreadLocal<Boolean>();
	private static CCJSqlParserManager parserManager = new CCJSqlParserManager();

	// public static final ThreadLocal<String> currentParsedSqlInThread = new
	// ThreadLocal<String>();

	// public static final ThreadLocal<String> currentDBInThread = new
	// ThreadLocal<String>();

	public static String getSessionRelatedSql(String sql) {

		if (TenantConfigure.isMultiTenant()) {
			net.sf.jsqlparser.statement.Statement statement = null;
			try {
				statement = parserManager.parse(new StringReader(sql));
				return statement.toString();
			} catch (Exception e) {
				e.printStackTrace();
				System.out
						.println("Error this sql statement can not be parsed: "
								+ sql);
				return null;
			}
		}

		FacesContext context = FacesContext.getCurrentInstance();
		// if (context != null) {
		// ExternalContext extContext = context.getExternalContext();
		// HttpSession se = ((HttpServletRequest) extContext.getRequest())
		// .getSession();
		// String str = (String) se
		// .getAttribute(TenantConstants.TenantDatabase);
		// currentDBInThread.set(str);
		// }

		return sql;
	}
}
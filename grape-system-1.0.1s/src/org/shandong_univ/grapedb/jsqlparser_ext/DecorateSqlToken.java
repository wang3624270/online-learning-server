package org.shandong_univ.grapedb.jsqlparser_ext;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.shandong_univ.tenant.constants.TenantConstants;

import net.sf.jsqlparser.schema.Table;

public class DecorateSqlToken {

	public static void decorateTable(Table table) {
		// Only support JSF temporary.
		if (table.getSchemaName() != null)
			return;
		FacesContext context = FacesContext.getCurrentInstance();
		if (context == null)
			return;
		ExternalContext extContext = context.getExternalContext();
		HttpSession se = ((HttpServletRequest) extContext.getRequest())
				.getSession();
		String str = (String) se.getAttribute(TenantConstants.TenantDatabase);
		table.setSchemaName(str);

	}

}
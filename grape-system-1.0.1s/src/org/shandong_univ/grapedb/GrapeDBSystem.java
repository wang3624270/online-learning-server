package org.shandong_univ.grapedb;

import java.io.StringReader;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.shandong_univ.grapedb.derive_parse.DeriveGroups;
import org.shandong_univ.tenant.TenantConfigure;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;

public class GrapeDBSystem {
	public static Logger logger = Logger.getLogger(GrapeDBSystem.class);
	private static String grapeDBConifgFile = "grapedb/table-derive-relation.xml";

	public static DeriveGroups deriveGroups;
	public static GrapeDBSystem grapeDBSystem;

	public boolean enableGrapeDB = false;

	public boolean isEnableGrapeDB() {
		return enableGrapeDB;
	}

	public void setEnableGrapeDB(boolean enableGrapeDB) {
		this.enableGrapeDB = enableGrapeDB;
	}

	public boolean deriveTable = false;

	public boolean isDeriveTable() {
		return deriveTable;
	}

	public void setDeriveTable(boolean deriveTable) {
		this.deriveTable = deriveTable;
	}

	public static boolean TanentInJSF = false;

	public static boolean isTanentInJSF() {
		return TanentInJSF;
	}

	public static void setTanentInJSF(boolean tanentInJSF) {
		TanentInJSF = tanentInJSF;
	}

	private static CCJSqlParserManager parserManager = new CCJSqlParserManager();

	public GrapeDBSystem() {
		grapeDBSystem = this;
		loadConfig();

	}

	private void loadConfig() {
		logger.info("------Shandong University GrapeDB Experiment for SaaS-like applications.-------\n");
		logger.info("GrapeDB system is maintained by xinxiao.zhao1985@gmail.com from Jinan Topwellsoft Corp.,"
				+ " and it can support database access route feature in multi-tenant environment.\n");
		try {

			SAXReader sb = new SAXReader();
			Document doc = null;
			try {
				if (Thread.currentThread().getContextClassLoader().getResourceAsStream(grapeDBConifgFile) == null)
					return;
				doc = sb.read(Thread.currentThread().getContextClassLoader().getResourceAsStream(grapeDBConifgFile));
			} catch (DocumentException e) {
				return;
			}
			Element root = doc.getRootElement();
			// Element point = root.element("point");
			List list = root.elements();
			Element element = null;

			GrapeDBSystem.deriveGroups = new DeriveGroups();

			GrapeDBSystem.deriveGroups.parseModel(root);

		} catch (Exception e) {
		}
	}

	public static String transSQL(String sql) {
		if (grapeDBSystem == null) {
			// SqlAccessLogHelper.sqlAccessLog(sql);
			return sql;
		}

		if (grapeDBSystem.enableGrapeDB) {
			if (grapeDBSystem.deriveTable && GrapeDBVar.threadCurrentGrapeDBKey.get() != null) {
				try {
					Statement statement = parserManager.parse(new StringReader(sql));
					String re = statement.toString();
					// GrapeDBVar.threadCurrentGrapeDBKey.set(null);
					return re;
				} catch (JSQLParserException e) {
					e.printStackTrace();
					System.out.println("Grape DB experiment error: " + sql + ".");
					return sql;
				}

			}
			if (TenantConfigure.isMultiTenant()) {

				net.sf.jsqlparser.statement.Statement statement = null;
				try {
					statement = parserManager.parse(new StringReader(sql));
					return statement.toString();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error this sql statement can not be parsed: " + sql);
					return null;
				}
			}
		}
		// SqlAccessLogHelper.sqlAccessLog(sql);
		return sql;
	}
}
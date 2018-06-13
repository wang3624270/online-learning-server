package org.shandong_univ.grapedb;

import java.io.StringReader;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;

public class a {

	public static void main(String[] args) {
		try {
			CCJSqlParserManager pm = new CCJSqlParserManager();
			String sql = "SELECT * FROM MY_TABLE1, MY_TABLE2, (SELECT * FROM MY_TABLE3) LEFT OUTER JOIN MY_TABLE4 "
					+ " WHERE ID = (SELECT MAX(ID) FROM MY_TABLE5) AND ID2 IN (SELECT * FROM MY_TABLE6)";
			Statement statement = pm.parse(new StringReader(sql));

			System.out.println(statement.toString());
		} catch (JSQLParserException e) {
			e.printStackTrace();
		}

	}

}
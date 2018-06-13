package org.shandong_univ.grapedb.derive_parse;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.Select;

public class a {
	public static void main(String[] args) {
		try {
			CCJSqlParserManager pm = new CCJSqlParserManager();
			String sql = "SELECT * FROM abroad_app_info a group by a.d asc";
			net.sf.jsqlparser.statement.Statement statement = pm
					.parse(new StringReader(sql));
			/*
			 * now you should use a class that implements StatementVisitor to
			 * decide what to do based on the kind of the statement, that is
			 * SELECT or INSERT etc. but here we are only interested in SELECTS
			 */
			System.out.println(statement.toString());
			if (statement instanceof Select) {
				Select selectStatement = (Select) statement;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
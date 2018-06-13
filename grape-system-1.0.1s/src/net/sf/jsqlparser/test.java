package net.sf.jsqlparser;

import java.io.StringReader;

import net.sf.jsqlparser.parser.CCJSqlParserManager;

public class test {
	public static void main(String[] args) {

		CCJSqlParserManager pm = new CCJSqlParserManager();
		String sql = "select * from t b";
		try {
			net.sf.jsqlparser.statement.Statement statement = pm
					.parse(new StringReader(sql));
			System.out.println(statement.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
package org.sdu.db_service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

public class DatabaseServiceUtils {
	private static DatabaseServiceUtils instance= null;
	public static DatabaseServiceUtils getInstantce(){
		if(instance == null)
			instance = new DatabaseServiceUtils();
		return instance;
	}
	
	private Properties getProperties(String databaseName) {
		Properties dsconfig = new Properties();
		try {
			dsconfig.load(this.getClass().getResourceAsStream(
					"/"+databaseName+".properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dsconfig;
	}
	private  Connection getConnection(Properties dsconfig) {
		Connection conn = null;
		do {
			try {
				Class.forName(dsconfig.getProperty("driverClass"));
				conn = DriverManager.getConnection(
						dsconfig.getProperty("jdbcUrl"),
						dsconfig.getProperty("user"),
						dsconfig.getProperty("password"));
			} catch (ClassNotFoundException e) {
			} catch (SQLException sqle) {
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
				}
			}
		} while (conn == null);
		return conn;
	}

	public List queryForMapList(String databaseName, String sqlStatement) {
		// TODO Auto-generated method stub
		Properties dsconfig = getProperties(databaseName);
		Connection con=getConnection(dsconfig);
		Statement stmt;
		ResultSet rs;
		HashMap map = null;
		List list=new ArrayList();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				map=new HashMap();
				for(int i=1;i<=columnCount;i++){
					map.put(rsmd.getColumnLabel(i), rs.getObject(i));
				}
				list.add(map);
			}
			stmt.close();
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List queryForArrayList(String databaseName, String sqlStatement) {
		// TODO Auto-generated method stub
		Properties dsconfig = getProperties(databaseName);
		Connection con=getConnection(dsconfig);
		Statement stmt;
		ResultSet rs;
		List list=new ArrayList();
		Object oa[];
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);
			ResultSetMetaData rsmd = rs.getMetaData() ; 
			int columnCount = rsmd.getColumnCount();
			oa = new Object[columnCount];
			while (rs.next()) {
				for(int i=1;i<=columnCount;i++)
					oa[i-1] =  rs.getObject(i);
				list.add(oa);
			}
			stmt.close();
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public List queryForFormList(String databaseName, String sqlStatement, String formClassName) {
		// TODO Auto-generated method stub
		Properties dsconfig = getProperties(databaseName);
		Connection con=getConnection(dsconfig);
		Statement stmt;
		ResultSet rs;
		Object f, o;
		int i;
		String name,methodName;
		Method method;
		List list=new ArrayList();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStatement);
			ResultSetMetaData rsmd = rs.getMetaData() ; 
			int columnCount = rsmd.getColumnCount();
			Class c = Class.forName(formClassName);
			HashMap <String, Method> map = new HashMap<String, Method>();
			for(i = 0; i <columnCount;i++) {
				name =rsmd.getColumnName(i+1);
				methodName = "set" + name.substring(0,1).toUpperCase() + name.substring(1,name.length());
				method = c.getMethod(methodName);
				map.put(name, method);
			}
			while (rs.next()) {
				f = c.newInstance();				
				for(i=0;i<columnCount;i++) {
					name =rsmd.getColumnName(i+1);
					o = rs.getObject(i+1);
					method = map.get(name);
					method.invoke(f, o);
				}
				list.add(f);
			}
			stmt.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public 	void queryForFormList(RmiRequest req, RmiResponse res) {
		String databaseName = (String)req.get("databaseName");
		String sqlStatement = (String)req.get("sqlStatement");
		String formClassName = (String)req.get("formClassName");
		res.add(RmiKeyConstants.KEY_FORMLIST, this.queryForFormList(databaseName, sqlStatement, formClassName));
	}
	public int executeUpdate(String databaseName, String sql) throws SQLException{
		Properties dsconfig = getProperties(databaseName);
		Connection con=getConnection(dsconfig);
		Statement stmt = null;
		int returnValue=0;
		try {
			stmt = con.createStatement();
			returnValue=stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(!stmt.isClosed())
				stmt.close();
			if(!con.isClosed())
				con.close();
		}
		return returnValue;
	}


}

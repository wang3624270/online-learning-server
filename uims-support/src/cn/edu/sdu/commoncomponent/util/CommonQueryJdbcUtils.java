package cn.edu.sdu.commoncomponent.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.ListOptionInfo;

public class CommonQueryJdbcUtils {
	public static final String DB_ATTRIBUTE[][] = { { "propertyms",
			"211.86.56.168", "sdupublicdb", "viewUser", "123456" } };
	private static CommonQueryJdbcUtils instance = null;
	private  HashMap<String, Connection> conMap;

	public CommonQueryJdbcUtils() {
		conMap = new HashMap<String, Connection>();
		Connection con;
		for (int i = 0; i < DB_ATTRIBUTE.length; i++) {
			con = openDb(DB_ATTRIBUTE[i][1], DB_ATTRIBUTE[i][2],
					DB_ATTRIBUTE[i][3], DB_ATTRIBUTE[i][4]);
			conMap.put(DB_ATTRIBUTE[i][0], con);
		}
	}

	public static synchronized CommonQueryJdbcUtils getInstance() {
		if (instance == null) {
			instance = new CommonQueryJdbcUtils();
		}
		return instance;
	}

	public Connection openDb(String hostName, String dbName, String user,
			String password) {
		Connection conn = null;
		do {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://" + hostName
						+ ":3306/" + dbName, user, password);
			} catch (ClassNotFoundException e) {
			} catch (SQLException sqle) {
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					System.out.println("数据库错误");
				}
			}
		} while (conn == null);
		return conn;
	}

	public void closeDb(Connection conn) {
		if (conn == null)
			return;
		try {
			conn.close();
		} catch (Exception ex) {
		}
	}

	public List queryForList(String name, String sql) {
		return null;
	}

	public synchronized List getCommonRoomBuildingOptionInfoList(
			String campusNum) {
		Connection conn = conMap.get("propertyms");
		if (conn == null)
			return null;
		Statement stmt;
		ResultSet rs;
		String sql,num,name;
		List list = new ArrayList();
		ListOptionInfo info;
		try {
			stmt = conn.createStatement();
			sql = "select distinct buildingNum, buildingName from V_CLASSROOM  where 1=1 ";
			if (campusNum != null && campusNum.length() != 0
					&& !campusNum.equals("-1")) {
				sql += " and campusNum = '" + campusNum
						+ "'  order by  buildingNum  ";
			}
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				num = (String) rs.getObject(1);
				name = (String)rs.getObject(2);
				list.add(new ListOptionInfo(num, name));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public synchronized List getCommonRoomInfoListOfBuilding(String buildingNum) {
		Connection conn = conMap.get("propertyms");
		if (conn == null)
			return null;
		Statement stmt;
		ResultSet rs;
		String sql;
		List list = new ArrayList();
		Object a[];
		try {
			stmt = conn.createStatement();
			sql = "select room_id, room_name, locationCode, buildingNum, buildingName,  campusNum from V_CLASSROOM  where  buildingNum = '" + buildingNum + "'  order by  locationCode  ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				a = new Object[6];
				a[0]= rs.getObject(1);
				a[1]= rs.getObject(2);
				a[2]= rs.getObject(3);
				a[3]= rs.getObject(4);
				a[4]= rs.getObject(5);
				a[5]= rs.getObject(6);
				list.add(a);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}


	public synchronized Object [] getCommonRoomInfoByLocationCode(String locationCode) {
		Connection conn = conMap.get("propertyms");
		if (conn == null)
			return null;
		Statement stmt;
		ResultSet rs;
		String sql;
		List list = new ArrayList();
		Object a[] = null;
		try {
			stmt = conn.createStatement();
			sql = "select room_id, room_name, locationCode, buildingNum, buildingName,  campusNum from V_CLASSROOM  where  locationCode = '" + locationCode + "' ";
			rs = stmt.executeQuery(sql);
			if( rs.next()) {
				a = new Object[6];
				a[0]= rs.getObject(1);
				a[1]= rs.getObject(2);
				a[2]= rs.getObject(3);
				a[3]= rs.getObject(4);
				a[4]= rs.getObject(5);
				a[5]= rs.getObject(6);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return a;
	}

	
}

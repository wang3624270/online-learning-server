package org.shandong_univ.tenant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.shandong_univ.grapedb.SessionRelatedDBManager;

import com.zaxxer.hikari.HikariDataSource;

public class TenantConfigure {
	public String getTenantInfoDB() {
		return tenantInfoDB;
	}

	public void setTenantInfoDB(String tenantInfoDB) {
		this.tenantInfoDB = tenantInfoDB;
	}

	public HikariDataSource getSource() {
		return source;
	}

	public void setSource(HikariDataSource source) {
		this.source = source;
		String tenantInfoDb_ = source.getJdbcUrl();
		tenantInfoDB = tenantInfoDb_.substring(tenantInfoDb_.lastIndexOf("/") + 1);
	}

	private boolean enableMultiTenant = false;
	private String jdbcUrl;
	private String username;
	private String password;
	private String driverClass;
	private HikariDataSource source;
	private String tenantInfoDB = null;

	public List<String> tenantIdentityList = null;

	public List<String> tenantDatabaseList = null;

	public TenantConfigure() {
		tenantConfigure = this;
		source = new HikariDataSource();

		tenantIdentityList = new ArrayList<String>();
		tenantDatabaseList = new ArrayList<String>();
	}

	public boolean isEnableMultiTenant() {

		return enableMultiTenant;
	}

	public void setEnableMultiTenant(boolean enableMultiTenant) {
		this.enableMultiTenant = enableMultiTenant;
	}

	public void refreshTenant() {
		try {
			SessionRelatedDBManager.sessionRelatedInThread.set(false);
			Connection connection = source.getConnection();
			Statement statement = connection.createStatement();
			String sql = "select tenantIdentity,dbName from " + tenantInfoDB + ".tenant_info";
			ResultSet rs = statement.executeQuery(sql);
			tenantIdentityList.clear();
			tenantDatabaseList.clear();
			while (rs.next()) {
				tenantIdentityList.add(rs.getString(1));
				tenantDatabaseList.add(rs.getString(2));
			}
			SessionRelatedDBManager.sessionRelatedInThread.set(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// /////////////////////////////////////////////////////////////////////
	private static TenantConfigure tenantConfigure;

	public static TenantConfigure getTenantConfigure() {
		return tenantConfigure;
	}

	public static DataSource getDataSource() {
		return tenantConfigure.getSource();
	}

	public static boolean isMultiTenant() {
		if (tenantConfigure == null)
			return false;
		return tenantConfigure.isEnableMultiTenant();
	}

	public static List<String> getTenantList() {
		if (tenantConfigure.tenantIdentityList.size() == 0) {
			tenantConfigure.refreshTenant();
		}
		return tenantConfigure.tenantIdentityList;
	}

	public static List<String> getTenentDatabaseList() {
		if (tenantConfigure.tenantDatabaseList.size() == 0) {
			tenantConfigure.refreshTenant();
		}
		return tenantConfigure.tenantDatabaseList;
	}

	// /////////////////////////////////////////////////////////////////////////////////
	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
		source.setJdbcUrl(this.jdbcUrl);

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		source.setUsername(this.username);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		source.setPassword(this.password);
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
		source.setDriverClassName("org.mariadb.jdbc.Driver");
	}

}
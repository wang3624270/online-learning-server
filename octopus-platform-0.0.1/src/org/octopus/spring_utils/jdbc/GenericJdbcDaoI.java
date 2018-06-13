package org.octopus.spring_utils.jdbc;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.jdbc.core.ConnectionCallback;

public interface GenericJdbcDaoI {
	public abstract void beginTranstaion();

	public abstract void commit();

	public abstract void rollback();

	public abstract void execute(String sql);

	public abstract List<Map<String, Object>> queryForList(String sql);
	
	public abstract void execute(ConnectionCallback<String> action);

}

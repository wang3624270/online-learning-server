package org.octopus.spring_utils.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.TransactionStatus;

public class GenericJdbcDaoImpl extends JdbcDaoSupport implements GenericJdbcDaoI {

	public GenericJdbcDaoImpl() {

	}

	@Autowired
	OctopusJdbcTransactionHelper octopusJdbcTransactionHelper;

	@Override
	public void beginTranstaion() {
		TransactionStatus tmp = octopusJdbcTransactionHelper.platformTransactionManager
				.getTransaction(octopusJdbcTransactionHelper.transactionDefinition);
		octopusJdbcTransactionHelper.transcationStatus.set(tmp);

	}

	@Override
	public void commit() {
		TransactionStatus tmp = octopusJdbcTransactionHelper.transcationStatus.get();
		if (tmp == null) {
			return;
		}
		octopusJdbcTransactionHelper.platformTransactionManager.commit(tmp);
		octopusJdbcTransactionHelper.transcationStatus.remove();

	}

	@Override
	public void rollback() {
		TransactionStatus tmp = octopusJdbcTransactionHelper.transcationStatus.get();
		if (tmp == null) {
			return;
		}
		octopusJdbcTransactionHelper.platformTransactionManager.rollback(tmp);
		octopusJdbcTransactionHelper.transcationStatus.remove();

	}

	@Override
	protected void checkDaoConfig() {
		this.setJdbcTemplate(octopusJdbcTransactionHelper.jdbcTemplate);
		super.checkDaoConfig();

	}

	@Override
	public void execute(String sql) {
		// TODO Auto-generated method stub
		octopusJdbcTransactionHelper.jdbcTemplate.execute(sql);
	}

	@Override
	public void execute(ConnectionCallback<String> action) {
		// TODO Auto-generated method stub
		octopusJdbcTransactionHelper.jdbcTemplate.execute(action);
	}
	
	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		// TODO Auto-generated method stub
		return octopusJdbcTransactionHelper.jdbcTemplate.queryForList(sql);
	}

}
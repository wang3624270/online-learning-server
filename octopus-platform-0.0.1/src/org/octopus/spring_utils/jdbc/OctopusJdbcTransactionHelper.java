package org.octopus.spring_utils.jdbc;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
@Scope("application")
public class OctopusJdbcTransactionHelper {
	public DataSourceTransactionManager platformTransactionManager;
	public DefaultTransactionDefinition transactionDefinition;
	public static ThreadLocal<TransactionStatus> transcationStatus = new ThreadLocal<TransactionStatus>();
	@Autowired
	public JdbcTemplate jdbcTemplate;

	public OctopusJdbcTransactionHelper() {

	}

	@PostConstruct
	public void init() {
		platformTransactionManager = new DataSourceTransactionManager(jdbcTemplate.getDataSource());
		transactionDefinition = new DefaultTransactionDefinition();
		transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
		transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	}

}

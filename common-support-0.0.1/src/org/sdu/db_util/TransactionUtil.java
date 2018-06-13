package org.sdu.db_util;

import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;

import org.sdu.spring_util.ApplicationContextHandle;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class TransactionUtil {
	static TransactionTemplate txTemplate = null;
	static TransactionTemplate tsTemplateCommitAtOnce = null;

	public static void execTransaction(final TxCallback cb) throws Exception {
		if (txTemplate == null)
			txTemplate = (TransactionTemplate) ApplicationContextHandle.getBean("tsTemplate");
		try {

			txTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					cb.exec();

				}
			});

		} catch (Exception e) {
			throw e;
		}
	}

	public static void execTransactionCommitAtOnce(final TxCallback cb) throws Exception {
		if (tsTemplateCommitAtOnce == null)
			tsTemplateCommitAtOnce = (TransactionTemplate) ApplicationContextHandle.getBean("tsTemplateCommitAtOnce");

		try {
			tsTemplateCommitAtOnce.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					cb.exec();

				}
			});

		} catch (Exception e) {
			throw e;
		}
	}
}
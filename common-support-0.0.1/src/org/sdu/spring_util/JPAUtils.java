package org.sdu.spring_util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class JPAUtils {
	static EntityManagerFactory emf = ApplicationContextHandle.getBean(
			OpenEntityManagerInViewFilter.DEFAULT_ENTITY_MANAGER_FACTORY_BEAN_NAME, EntityManagerFactory.class);
	static ThreadLocal<Boolean> participate;

	public static void beginJPASession() {
		if (participate == null)
			participate = new ThreadLocal<Boolean>();
		participate.set(false);
		if (TransactionSynchronizationManager.hasResource(emf)) {
			participate.set(true);
		} else {
			try {
				EntityManager em = emf.createEntityManager();
				TransactionSynchronizationManager.bindResource(emf, new EntityManagerHolder(em));
			} catch (PersistenceException ex) {
				throw new DataAccessResourceFailureException("Could not create JPA EntityManager", ex);
			}
		}
	}

	public static void endJPASession() {
		if (participate == null || participate.get() == null)
			return;
		if (!participate.get()) {
			EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.unbindResource(emf);
			EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager());
		}

	}
}
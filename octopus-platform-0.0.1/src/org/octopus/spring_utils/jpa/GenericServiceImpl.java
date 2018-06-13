package org.octopus.spring_utils.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.octopus.common_business.notices.model.NoticesMemberInfo;
import org.springframework.transaction.annotation.Transactional;

public class GenericServiceImpl<T> implements GenericServiceI<T> {
	private EntityManagerFactory entityManagerFactory;
	private Class<T> clazz;

	public GenericServiceImpl() {
	}

	public GenericServiceImpl(Class<T> clazz) {
		this.clazz = clazz;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	@PersistenceContext
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		// EntityManager manager = entityManager.get();
		if (entityManager == null) {
			entityManager = entityManagerFactory.createEntityManager();
			// entityManager.set(manager);
		}
		return entityManager;
	}

	@Transactional
	public boolean delete(Object obj) {

		if (!getEntityManager().contains(obj))
			obj = getEntityManager().merge(obj);
		getEntityManager().remove(obj);
		return true;
	}

	@Transactional
	public boolean deleteById(Serializable id) {
		Object obj = find(id);
		return delete(obj);
	}

	public int execute(String sql) {
		return execute(sql, null);
	}

	public void flushORMSession() {

		getEntityManager().flush();
	}

	public int execute(String sql, Collection<Object> paras) {
		Query query = getEntityManager().createQuery(sql);
		int parameterIndex = 0;
		if (paras != null && paras.size() > 0) {
			for (Object obj : paras) {
				query.setParameter(parameterIndex++, obj);
			}
		}
		return query.executeUpdate();
	}

	public T find(Serializable id) {
		return getEntityManager().find(clazz, id);
	}

	public T findByFieldName(String fieldName, Serializable value) {
		Query query = getEntityManager().createQuery("from " + clazz + " where fieldName=?");
		query.setParameter(0, value);
		return (T) query.getSingleResult();
	}

	public List query(String scope) {
		return query(scope, null);
	}

	public List query(String scope, Collection paras) {
		return query(scope, null, -1, -1);
	}

	public List query(String scope, Collection paras, int begin, int max) {

		Query query;
		if (scope != null)
			query = getEntityManager().createQuery("from " + clazz + " where " + scope);
		else
			query = getEntityManager().createQuery("from " + clazz.getName());
		int parameterIndex = 0;
		if (paras != null && paras.size() > 0) {
			for (Object obj : paras) {
				query.setParameter(parameterIndex++, obj);
			}
		}
		if (begin >= 0 && max > 0) {
			query.setFirstResult(begin);
			query.setMaxResults(max);
		}
		return query.getResultList();
	}

	public List queryForList(String hql) {
		Query query = getEntityManager().createQuery(hql);
		return query.getResultList();
	}

	public List queryForList(String hql, int begin, int max) {
		Query query = getEntityManager().createQuery(hql);
		query.setFirstResult(begin);
		query.setMaxResults(max);
		return query.getResultList();
	}

	protected List queryForList(final String countHql, final String listHql, final Collection<Object> paras,
			final Page page) {
		Long count = (Long) queryForObject(countHql, paras);
		if (count == null)
			page.setTotalCount(0);
		else
			page.setTotalCount(count.intValue());
		if (page.isEmpty())
			return Collections.EMPTY_LIST;
		return queryForList(listHql, paras, page);
	}

	protected List queryForList(final String listHql, final Collection<Object> paras) {
		Query query = getEntityManager().createQuery(listHql);
		int parameterIndex = 0;
		if (paras != null && paras.size() > 0) {
			for (Object obj : paras) {
				query.setParameter(parameterIndex++, obj);
			}
		}

		return query.getResultList();
	}

	protected List queryForList(final String listHql, final Collection<Object> paras, final Page page) {
		Query query = getEntityManager().createQuery(listHql);
		int parameterIndex = 0;
		if (paras != null && paras.size() > 0) {
			for (Object obj : paras) {
				query.setParameter(parameterIndex++, obj);
			}
		}
		query.setFirstResult(page.getFirstResult());
		query.setMaxResults(page.getPageSize());

		return query.getResultList();
	}

	protected Object queryForObject(final String select, final Collection<Object> paras) {
		// Query query = getEntityManager().createQuery(select);
		// int parameterIndex = 0;
		// if (paras != null && paras.size() > 0) {
		// for (Object obj : paras) {
		// query.setParameter(parameterIndex++, obj);
		// }
		// }

		return uniqueResult(select, paras);
	}

	public Object uniqueResult(String sql) {
		return uniqueResult(sql, null);
	}

	public Object uniqueResult(String sql, Collection<Object> paras) {
		Query query = getEntityManager().createQuery(sql);
		int parameterIndex = 0;
		if (paras != null && paras.size() > 0) {
			for (Object obj : paras) {
				query.setParameter(parameterIndex++, obj);
			}
		}
		List list = query.getResultList();
		if (list.size() == 0)
			return null;
		return query.getResultList().get(0);
	}

	@Transactional
	public boolean create(Object obj) {
		// getEntityManager().getTransaction().begin();
		save(obj);
		// getEntityManager().getTransaction().commit();
		return true;
	}

	@Transactional
	public boolean save(Object obj) {
		// getEntityManager().getTransaction().begin();
		getEntityManager().persist(obj);
		// getEntityManager().getTransaction().commit();
		return true;
	}

	@Transactional
	public boolean update(Object obj) {
		// getEntityManager().getTransaction().begin();
		getEntityManager().merge(obj);
		// getEntityManager().getTransaction().commit();
		return true;
	}

	public PageBean<T> genaratePageBean(Page pageInfo, String countHql, String listHql, List<T> list) {
		PageBean<T> pageBean = new PageBean<T>(pageInfo.getPageSize(), countHql, listHql.toString());
		pageBean.setDispList(list);
		pageBean.setTotalRows(pageInfo.getTotalCount());
		pageBean.setCurrentPage(pageInfo.getPageIndex());
		return pageBean;

	}

	public PageBean genaratePageBean(Page pageInfo, String countHql, String listHql) {
		PageBean pageBean = new PageBean<T>(pageInfo.getPageSize(), countHql, listHql.toString());
		List list = this.queryForList(countHql, listHql, null, pageInfo);
		pageBean.setDispList(list);
		pageBean.setTotalRows(pageInfo.getTotalCount());
		pageBean.setCurrentPage(pageInfo.getPageIndex());
		return pageBean;

	}

	@Override
	public T query(Serializable id) {
		// TODO Auto-generated method stub
		return this.find(id);

	}

	@Override
	public Map getMapFromPo(T obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMapToPo(Map m, T obj) {
		// TODO Auto-generated method stub
	}


}

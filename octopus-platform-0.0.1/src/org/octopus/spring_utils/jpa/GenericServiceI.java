package org.octopus.spring_utils.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.octopus.common_business.notices.model.NoticesMemberInfo;

public interface GenericServiceI<T> {

	/**
	 * 把对象保存到持久层
	 * 
	 * @param obj
	 * @return 若保存成功，则返回true，否则返回false
	 * @throws IdExistException
	 */
	default boolean save(T obj) {
		return false;
	}

	/**
	 * 更新持久层中的对象
	 * 
	 * @param obj
	 * @return 若修改成功，则返回true，否则返回false
	 */
	default boolean update(T obj) {
		return false;
	}

	/**
	 * 删除持久层中的对象
	 * 
	 * @param obj
	 * @return 若删除操作成功，则返回true，否则返回false
	 */
	default boolean delete(T obj) {
		return false;
	}

	boolean deleteById(Serializable id);

	/**
	 * 根据类及主键加载对象
	 * 
	 * @param clz
	 * @param id
	 * @return 若查找到指定主键值的持久对象，则返回该对象，否则返回null
	 */
	T find(Serializable id);

	T query(Serializable id);

	/**
	 * 根据类、字段名及字段值加载对象，只加载一条符合条件的对象。
	 * 
	 * @param clz
	 * @param fieldName
	 * @param value
	 * @return 若查询到指定属性及值的持久对象，则返回该对象，否则返回null
	 */
	T findByFieldName(String fieldName, Serializable value);

	/**
	 * 根据条件查询对象
	 * 
	 * @param clz
	 *            类名
	 * @param scope
	 *            查询条件
	 * @return 返回查询的记录结果记录
	 */
	List<T> query(String scope);

	/**
	 * 根据条件、条件参数查询对象
	 * 
	 * @param clz
	 *            类名
	 * @param scope
	 *            查询条件
	 * @param paras
	 *            查询参数
	 * @return 返回查询的记录结果集
	 */
	List<T> query(String scope, Collection<Object> paras);

	/**
	 * 查询符合条件的对象，从begin开始取max条记录
	 * 
	 * @param clz
	 *            Java类
	 * @param scope
	 *            查询条件
	 * @param paras
	 *            查询参数
	 * @param begin
	 *            返回有效结果开始记录数
	 * @param max
	 *            返回的最多记录数
	 * @return 返回查询的记录结果集
	 */
	List<T> query(String scope, Collection<Object> paras, int begin, int max);

	/**
	 * 查询符合条件的对象
	 */
	List<T> queryForList(String hql);

	/**
	 * 执行sql语句，并返回一个对象,如select count(*) from tableName等
	 * 
	 * @param sql
	 *            sql语句
	 * @return 返回查询结果，若没有结果则返回null
	 */
	Object uniqueResult(String sql);

	/**
	 * 根据sql语句及查询参数执行查询，并返回一个唯一对象，如select count(*) from tableName where filed=?
	 * 
	 * @param sql
	 * @param paras
	 * @return 返回单一的查询结果，若没有结果则返回null
	 */
	Object uniqueResult(String sql, Collection<Object> paras);

	/**
	 * 执行任意sql语句，返回受影响的记录数
	 * 
	 * @param sql
	 *            sql语句
	 * @return 返回受影响的记录数
	 */
	int execute(String sql);

	/**
	 * 根据sql语句及参数执行数据库操作，返回受影响的记录数
	 * 
	 * @param sql
	 *            sql语句
	 * @param paras
	 *            参数
	 * @return 返回受影响的记录数
	 */
	int execute(String sql, Collection<Object> paras);

	public void flushORMSession();
	public Map getMapFromPo(T obj);
	public void setMapToPo(Map m, T obj);

	
}

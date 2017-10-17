package com.wlg.Dao;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by LvLiangFeng on 2016/6/13.
 */
public interface BaseDao {

	public <T> Serializable save(T entity);

	public <T> void saveOrUpdate(T entity);

	/**
	 * 更新指定的实体
	 *
	 * @param <T>
	 * @param pojo
	 */
	public <T> void updateEntitie(T pojo);

	/**
	 * 通过sql获取数据
	 * @param sql
	 * @param objs
     * @return
     */
	public SqlRowSet findForJdbcSet(String sql, Object... objs);

	/**
	 * 删除实体
	 *
	 * @param <T>
	 *
	 * @param <T>
	 *
	 * @param <T>
	 * @param entitie
	 */
	public <T> void delete(T entitie);

	/**
	 * 根据实体名称和主键获取实体
	 *
	 * @param <T>
	 * @param entityName
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> entityName, Serializable id);

	/**
	 * 根据实体名字获取唯一记录
	 *
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> T findUniqueByProperty(Class<T> entityClass,
                                      String propertyName, Object value);

	/**
	 * 根据实体名字获取唯一记录
	 *
	 * @param value
	 * @return
	 */
	public <T> T findUniqueByID(Class<T> entityClass, Object value);

	/**
	 * 按属性查找对象列表.
	 */
	public <T> List<T> findByProperty(Class<T> entityClass,
                                      String propertyName, Object value);

	/**
	 * 加载全部实体
	 *
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public <T> List<T> loadAll(final Class<T> entityClass);

	/**
	 * 根据实体名称和主键获取实体
	 *
	 * @param <T>
	 *
	 * @param <T>
	 * @param entityName
	 * @param id
	 * @return
	 */
	public <T> T getEntity(Class entityName, Serializable id);

	public <T> void deleteEntityById(Class entityName, Serializable id);

	/**
	 * 删除实体集合
	 *
	 * @param <T>
	 * @param entities
	 */
	public <T> void deleteAllEntitie(Collection<T> entities);



	public <T> void updateEntityById(Class entityName, Serializable id);

	/**
	 * 通过hql 查询语句查找对象
	 *
	 * @param <T>
	 * @param hql
	 * @return
	 */
	public <T> List<T> findByQueryString(String hql);

	/**
	 * 通过hql查询唯一对象
	 *
	 * @param <T>
	 * @param hql
	 * @return
	 */
	public <T> T singleResult(String hql);

	/**
	 * 根据sql更新
	 *
	 * @param sql
	 * @return
	 */
	public int updateBySqlString(String sql);

	/**
	 * 根据sql查找List
	 *
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findListbySql(String query);

	/**
	 * 通过属性称获取实体带排序
	 *
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public <T> List<T> findByPropertyisOrder(Class<T> entityClass,
                                             String propertyName, Object value, boolean isAsc);

	/**
	 * 通过hql 查询语句查找HashMap对象
	 *
	 * @param query
	 * @return
	 */
	public Map<Object, Object> getHashMapbyQuery(String query);

	/**
	 * 执行SQL
	 */
	public Integer executeSql(String sql, Object[] param);

	/**
	 * 通过JDBC查找对象集合 使用指定的检索标准检索数据返回数据
	 */
	public List<Map<String, Object>> findForJdbc(String sql, Object... objs);

	/**
	 * 通过JDBC查找对象集合 使用指定的检索标准检索数据返回数据
	 */
	public Map<String, Object> findOneForJdbc(String sql, Object... objs);

	/**
	 * 通过hql 查询语句查找对象
	 *
	 * @param <T>
	 * @param hql,param
	 * @return
	 */
	public <T> List<T> findHql(String hql, Object... param);

	/**
	 * 根据sql返回结果集
	 * @param rs
	 * @return
     */
	public String SqlRowSetToJosn(SqlRowSet rs);


	public List<Map<String,Object>> queryForList(String sql);

	public Object queryEntityById(String sql,Object t);


}

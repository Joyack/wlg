/**
 * 文件名：BaseDao.java
 * 创建日期： 2012-6-12
 * Copyright (c) 2011-2011 广电运通
 * All rights reserved.
 
 * 修改记录：
 * 1.修改时间, 修改人：
 *   修改内容：
 */
package com.wlg.Dao.Impl.hibernate;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Entity;

import com.wlg.Model.PageBean;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.hibernate.SessionFactory;
import org.omg.CORBA.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


/**
 * 公用的DAO类，供子类继承或直接使用.
 * 
 * @param <T>
 *            实体类
 * @author yrliang
 */
@SuppressWarnings("unchecked")
public class BaseDao<T> extends HibernateDao<T, Serializable> {
	@Autowired
	@Qualifier("jdbcTemplate")
	JdbcTemplate jdbcTemplate;


	/**
	 * 主键属性名
	 */
	public static final String PK = "id";

	/**
	 * 跨数据库改造 特殊字符
	 */
	public static final String WHERE = " where ";

	/** 日志对象 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 * 创建一个新的实例 BaseDao.
	 * 
	 */
	public BaseDao() {

	}

	/**
	 * 
	 * 创建一个新的实例 BaseDao.
	 * 
	 * @param sessionFactory
	 *            hibernate会话工厂
	 * @param entityClass
	 *            实体的类
	 */
	public BaseDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}

	/**
	 * 描述：saveNew(保存一个新的实体对象) <br>
	 * 
	 * @param entity
	 *            实体
	 * @return Serializable 主键
	 * @Exception 异常对象 <br>
	 */
	public Serializable saveNew(T entity) {
		super.getSession().save(entity);
		Serializable id = (Serializable) ReflectionUtils.getFieldValue(entity, PK);

		return id;
	}

	/**
	 * 描述：获取关联属性 <br>
	 *
	 * @return Serializable
	 * @Exception 异常对象 <br>
	 */
	public String[] getAsscociationProperties() {
		List<String> asscociationProperties = new ArrayList<String>();
		PropertyDescriptor[] entityPds = BeanUtils.getPropertyDescriptors(this.entityClass.getClass());
		for (int i = 0; i < entityPds.length; i++) {
			PropertyDescriptor entityPd = entityPds[i];
			Class clazz = entityPd.getPropertyType();
			if (Collection.class.isAssignableFrom(clazz) || clazz.isAnnotationPresent(Entity.class)) {
				asscociationProperties.add(entityPd.getName());
			}
		}
		return asscociationProperties.toArray(new String[0]);
	}

	/**
	 * 拷贝属性值到hibernate持久化实体的方式更新，不拷贝关联实体属性
	 * 
	 * @param entity
	 *            实体
	 */
	public void copyUpdate(T entity) {
		Assert.notNull(entity, "entity can not be null");

		Serializable id = (Serializable) ReflectionUtils.getFieldValue(entity, PK);
		T target = get(id);
		Assert.notNull(target, entity.getClass().getName() + "[id=" + id
				+ "] not exist！");

		// 复制更新持久化实体
		BeanUtils.copyProperties(entity, target, getAsscociationProperties());
	}

	/**
	 * 拷贝属性值到hibernate持久化实体的方式更新
	 * 
	 * @param entity
	 *            携带新的属性值的对象
	 * @param ignoreFields
	 *            无需拷贝的属性
	 */
	public void copyUpdate(T entity, String[] ignoreFields) {
		Assert.notNull(entity, "entity can not be null");

		Serializable id = (Serializable) ReflectionUtils.getFieldValue(entity, PK);
		T target = get(id);
		Assert.notNull(target, entity.getClass().getName() + "[id=" + id
				+ "] not exist！");

		// 复制更新持久化实体
		BeanUtils.copyProperties(entity, target, ignoreFields);
	}

	/**
	 * 根据过滤参数进行分页查询
	 * 
	 * @param page
	 *            页面对象
	 * @param propertyFilterMap
	 *            形式如(key="EQS_xxxfieldName")
	 * @return 页面对象
	 */
	@Transactional(readOnly = true)
	@Deprecated
	public PageBean<T> findPage(final PageBean<T> page, final Map<String, String> propertyFilterMap) {
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();

		// 分析参数Map,构造PropertyFilter列表
		for (Map.Entry<String, String> entry : propertyFilterMap.entrySet()) {
			String filterName = entry.getKey();
			Object value = entry.getValue();
			// 如果value值为空,则忽略此filter.
			if (value == null) {
				continue;
			}
			boolean omit = StringUtils.isBlank(value.toString());
			if (!omit) {
				PropertyFilter filter = new PropertyFilter(filterName, value);
				filterList.add(filter);
			}
		}
		return super.findPageBean(page, filterList);
	}

	/**
	 * 根据过滤参数进行分页查询
	 * 
	 * @param page
	 *            页面对象
	 * @param propertyFilterMap
	 *            形式如(key="EQS_xxxfieldName")
	 * @return 页面对象
	 * @see PropertyFilter
	 */
	@Transactional(readOnly = true)
	public PageBean<T> findPageNew(final PageBean<T> page, final Map<String, Object> propertyFilterMap) {
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();

		// 分析参数Map,构造PropertyFilter列表
		for (Map.Entry<String, Object> entry : propertyFilterMap.entrySet()) {
			String filterName = entry.getKey();
			Object value = entry.getValue();
			// 如果value值为空,则忽略此filter.
			if (value == null) {
				continue;
			}
			boolean omit = StringUtils.isBlank(value.toString());
			if (!omit) {
				PropertyFilter filter = new PropertyFilter(filterName, value);
				filterList.add(filter);
			}
		}
		return super.findPageBean(page, filterList);
	}

	@Autowired
	private RegexPropertyMessageResources msgResource;

	public String getI18nMessage(String key, Object... objects) {
		String s = msgResource.getMessage(key, objects, Locale.getDefault());
		return s;
	}





	/**
	 * 分页查询
	 * @param sql 查询的sql语句，且该语句不能包括limit分页部分字符串
	 *             sql事例：SELECT COLUMN1 COLUMN2 FROM TABLE　WHERE COLUMN3=? AND COLUMN4=5 ORDER BY COLUMN6
	 * @param params 参数列表 不需要参数的可以为空
	 * @param page 最小为1
	 * @param pageSize 分页大小
	 * @return
	 * @throws SystemException
	 * @throws RuntimeException
	 */
	protected  PageBean<Map<String,Object>> queryEntitys(String sql, Object[] params, int page, int pageSize)throws SystemException,RuntimeException{
		if("".equals(sql)){
			return null;
		}
		PageBean<Map<String,Object>> pageCounter = new PageBean<Map<String,Object>>(page,pageSize);
		int fromStrIndex = sql.toUpperCase().indexOf("FROM");
		//构造统计总记录sql
		String countSql = "SELECT COUNT(1) " + sql.substring(fromStrIndex);
		if(params == null || params.length == 0){
			pageCounter.setTotalCount(jdbcTemplate.queryForObject(countSql,Long.class));
		}else {
			pageCounter.setTotalCount(jdbcTemplate.queryForObject(countSql,params,Long.class));
		}

		//查询数据
		page = page < 1 ? 1 : page;
		page = page > pageCounter.getTotalPageBeans() ? (int) pageCounter.getTotalPageBeans() : page;
		if(page<=0)page=1;
		StringBuffer sqlBf = new StringBuffer(sql);
		sqlBf.append(" LIMIT ")
				.append((page - 1) * pageSize)
				.append(",")
				.append(pageSize);
		if(params == null || params.length == 0){
			pageCounter.setResult(jdbcTemplate.queryForList(sqlBf.toString()));
		}else {
			pageCounter.setResult(jdbcTemplate.queryForList(sqlBf.toString(),params));
		}
		return pageCounter;
	}





}

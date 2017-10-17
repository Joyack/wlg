/**
 * 文件名：HibernateDao.java
 * 创建日期： 2012-6-12
 * Copyright (c) 2011-2011 广电运通
 * All rights reserved.
 
 * 修改记录：
 * 1.修改时间, 修改人：
 *   修改内容：
 */
package com.wlg.Dao.Impl.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wlg.Model.PageBean;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;
import com.wlg.Dao.Impl.hibernate.PropertyFilter.MatchType;


/**
 * 封装SpringSide扩展功能的Hibernat DAO泛型基类.
 * 
 * 扩展功能包括分页查询,按属性过滤条件列表查询. 可在Service层直接使用,也可以扩展泛型DAO子类使用,见两个构造函数的注释.
 * 
 * @param <T>
 *            DAO操作的对象类型
 * @param <PK>
 *            主键类型
 * 
 * @author calvin
 */
public class HibernateDao<T, PK extends Serializable> extends
		SimpleHibernateDao<T, PK> {
	/**
	 * 用于Dao层子类使用的构造函数. 通过子类的泛型定义取得对象类型Class. eg. public class UserDao extends
	 * HibernateDao<User, Long>{ }
	 */
	public HibernateDao() {
		super();
	}

	/**
	 * 用于省略Dao层, Service层直接使用通用HibernateDao的构造函数. 在构造函数中定义对象类型Class. eg.
	 * HibernateDao<User, Long> userDao = new HibernateDao<User,
	 * Long>(sessionFactory, User.class);
	 * 
	 * @param sessionFactory
	 *            sessionFactory
	 * @param entityClass
	 *            entityClass
	 */
	public HibernateDao(final SessionFactory sessionFactory,
			final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}

	// -- 分页查询函数 --//
	/**
	 * 分页获取全部对象.
	 * 
	 * @param pageBean
	 *            PageBean
	 * @return 返回全部
	 */
	public PageBean<T> getAll(final PageBean<T> pageBean) {
		return findPageBean(pageBean);
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param pageBean
	 *            分页参数.不支持其中的orderBy参数.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
	public PageBean<T> findPageBean(final PageBean<T> pageBean, final String hql,
			final Object... values) {
		Assert.notNull(pageBean, "PageBean not allow null");

		Query q = createQuery(hql, values);
		long totalCount = 0;
		if (pageBean.isAutoCount()) {
			totalCount = countHqlResult(hql, values);
			pageBean.setTotalCount(totalCount);
		}

		setPageBeanParameter(q, pageBean);
		List result = q.list();
		// 过滤由于所选的页码不符合查询结果，则更改页码为起始页码进行查询校准
		if (result.size() == 0 && totalCount != 0) {
			q.setFirstResult(0);
			q.setMaxResults(pageBean.getPageSize());
			result = q.list();
			pageBean.setPageNo(1);
		}
		pageBean.setResult(result);
		return pageBean;
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param pageBean
	 *            分页参数.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            命名参数,按名称绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
	public PageBean<T> findPageBean(final PageBean<T> pageBean, final String hql,
			final Map<String, Object> values) {
		Assert.notNull(pageBean, "PageBean not allow null");

		Query q = createQuery(hql, values);
		long totalCount = 0;
		if (pageBean.isAutoCount()) {
			totalCount = countHqlResult(hql, values);
			pageBean.setTotalCount(totalCount);

		}

		setPageBeanParameter(q, pageBean);

		List<T> result = q.list();
		// 过滤由于所选的页码不符合查询结果，则更改页码为起始页码进行查询校准
		if (result.size() == 0 && totalCount != 0) {
			q.setFirstResult(0);
			q.setMaxResults(pageBean.getPageSize());
			result = q.list();
			pageBean.setPageNo(1);
		}
		pageBean.setResult(result);
		return pageBean;
	}

	/**
	 * 按Criteria分页查询.
	 * 
	 * @param pageBean
	 *            分页参数.
	 * @param criterions
	 *            数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
	public PageBean<T> findPageBean(final PageBean<T> pageBean, final Criterion... criterions) {
		Assert.notNull(pageBean, "PageBean not allow null");

		Criteria c = createCriteria(criterions);
		int totalCount = 0;
		if (pageBean.isAutoCount()) {
			totalCount = countCriteriaResult(c);
			pageBean.setTotalCount(totalCount);
		}

		setPageBeanParameter(c, pageBean);
		List result = c.list();
		// 过滤由于所选的页码不符合查询结果，则更改页码为起始页码进行查询校准
		if (result.size() == 0 && totalCount != 0) {
			c.setFirstResult(0);
			c.setMaxResults(pageBean.getPageSize());
			result = c.list();
			pageBean.setPageNo(1);
		}
		pageBean.setResult(result);
		return pageBean;
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageBeanParameter(final Query q, final PageBean<T> pageBean) {
		// hibernate的firstResult的序号从0开始
		q.setFirstResult(pageBean.getFirst() - 1);
		q.setMaxResults(pageBean.getPageSize());
		return q;
	}

	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 * 
	 * @param c
	 *            条件对象
	 */
	protected Criteria setPageBeanParameter(final Criteria c, final PageBean<T> pageBean) {
		// hibernate的firstResult的序号从0开始
		c.setFirstResult(pageBean.getFirst() - 1);
		c.setMaxResults(pageBean.getPageSize());

		if (pageBean.isOrderBySetted()) {
			String[] orderByArray = StringUtils.split(pageBean.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(pageBean.getOrder(), ',');

			Assert.isTrue(orderByArray.length == orderArray.length,
							"paping multiple order param,order field and order orientation not equal");

			for (int i = 0; i < orderByArray.length; i++) {
				if (pageBean.ASC.equals(orderArray[i])) {
					c.addOrder(Order.asc(orderByArray[i]));
				} else {
					c.addOrder(Order.desc(orderByArray[i]));
				}
			}
		}
		return c;
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		String fromHql = hql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql,
			final Map<String, Object> values) {
		String fromHql = hql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	@SuppressWarnings("unchecked")
	protected int countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl,
					"orderEntries");
			ReflectionUtils
					.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			// 此处出现异常不需要处理
			logger.error("Runtime Exception impossibility throw:{}", e
					.getMessage());
		}

		// 执行Count查询
		int totalCount = (Integer) c.setProjection(Projections.rowCount())
				.uniqueResult();

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			// 此处出现异常不需要处理
			logger.error("Runtime Exception impossibility throw:{}", e
					.getMessage());
		}

		return totalCount;
	}

	// -- 属性过滤条件(PropertyFilter)查询函数 --//

	/**
	 * 按属性查找对象列表,支持多种匹配方式.
	 * 
	 * @param matchType
	 *            匹配方式,目前支持的取值见PropertyFilter的MatcheType enum.
	 * @param value
	 *            值
	 * @param propertyName
	 *            属性名
	 * @return 返回值
	 */
	public List<T> findBy(final String propertyName, final Object value,
			final MatchType matchType) {
		Criterion criterion = buildPropertyFilterCriterion(propertyName, value,
				matchType);
		return find(criterion);
	}

	/**
	 * 按属性过滤条件列表查找对象列表.
	 * 
	 * @param filters
	 *            过滤条件
	 * @return 返回List
	 */
	public List<T> find(List<PropertyFilter> filters) {
		Criterion[] criterions = buildPropertyFilterCriterions(filters);
		return find(criterions);
	}

	/**
	 * 按属性过滤条件列表分页查找对象.
	 * 
	 * @param pageBean
	 *            页对象
	 * @param filters
	 *            过滤条件
	 * @return 返回PageBean
	 */
	public PageBean<T> findPageBean(final PageBean<T> pageBean,
			final List<PropertyFilter> filters) {
		Criterion[] criterions = buildPropertyFilterCriterions(filters);
		return findPageBean(pageBean, criterions);
	}

	/**
	 * 按属性条件列表创建Criterion数组,辅助函数.
	 */
	protected Criterion[] buildPropertyFilterCriterions(
			final List<PropertyFilter> filters) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for (PropertyFilter filter : filters) {
			if (!filter.isMultiProperty()) { // 只有一个属性需要比较的情况.
				Criterion criterion = buildPropertyFilterCriterion(filter
						.getPropertyName(), filter.getPropertyValue(), filter
						.getMatchType());
				criterionList.add(criterion);
			} else {// 包含多个属性需要比较的情况,进行or处理.
				Disjunction disjunction = Restrictions.disjunction();
				for (String param : filter.getPropertyNames()) {
					Criterion criterion = buildPropertyFilterCriterion(param,
							filter.getPropertyValue(), filter.getMatchType());
					disjunction.add(criterion);
				}
				criterionList.add(disjunction);
			}
		}
		return criterionList.toArray(new Criterion[criterionList.size()]);
	}

	/**
	 * 按属性条件参数创建Criterion,辅助函数.
	 */
	protected Criterion buildPropertyFilterCriterion(final String propertyName,
			final Object propertyValue, final MatchType matchType) {
		Assert.hasText(propertyName, "propertyName not allow null");
		Criterion criterion = null;

		// 根据MatchType构造criterion
		if (MatchType.EQ.equals(matchType)) {
			criterion = Restrictions.eq(propertyName, propertyValue);
		} else if (MatchType.LIKE.equals(matchType)) {
			criterion = Restrictions.like(propertyName, (String) propertyValue,
					MatchMode.ANYWHERE);
		} else if (MatchType.LE.equals(matchType)) {
			criterion = Restrictions.le(propertyName, propertyValue);
		} else if (MatchType.LT.equals(matchType)) {
			criterion = Restrictions.lt(propertyName, propertyValue);
		} else if (MatchType.GE.equals(matchType)) {
			criterion = Restrictions.ge(propertyName, propertyValue);
		} else if (MatchType.GT.equals(matchType)) {
			criterion = Restrictions.gt(propertyName, propertyValue);
		}

		return criterion;
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 * 
	 * @param propertyName
	 *            属性名
	 * @param newValue
	 *            新值
	 * @param oldValue
	 *            旧值
	 * @return 返回值
	 */
	public boolean isPropertyUnique(final String propertyName,
			final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue))
			return true;
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}
}

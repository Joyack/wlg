package com.wlg.Dao.Impl;
import java.io.Serializable;
import java.util.*;

import com.wlg.Dao.BaseDao;
import com.wlg.Dao.Impl.hibernate.HibernateDao;
import com.wlg.Model.GoodsInfo;
import com.wlg.Util.HiSession;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import javax.annotation.Resource;

/**
 * Created by LvLiangFeng on 2016/6/13.
 */
@Repository
public class BaseDaoImp implements BaseDao {

    @Resource
    private HiSession hiSession;

    /**
     * 初始化Log4j的一个实例
     */
    private static final Logger logger = Logger
            .getLogger(BaseDaoImp.class);

    /**
     * 根据主键更新实体
     */
    public <T> void updateEntityById(Class entityName, Serializable id) {
        updateEntitie(get(entityName, id));
    }

    /**
     * 通过hql 查询语句查找对象
     *
     * @param query
     * @return
     */
    public <T> List<T> findByQueryString(String query) {
//        Query queryObject = this.hiSession.getSession().createQuery(query);
//        List<T> list = queryObject.list();
//        if (list.size() > 0) {
//            this.hiSession.getSession().flush();
//        }
//        return list;
        return this.hiSession.getSession().createQuery(query).list();
    }

    /**
     * 按属性查找对象列表.
     */
    public <T> List<T> findByProperty(Class<T> entityClass,
                                      String propertyName, Object value) {
        Assert.hasText(propertyName);
        return (List<T>) createCriteria(entityClass,
                Restrictions.eq(propertyName, value)).list();
    }

    /**
     * 通过hql 查询语句查找对象
     *
     * @param <T>
     * @param hql
     * @return
     */
    public <T> List<T> findHql(String hql, Object... param) {
        Query q = this.hiSession.getSession().createQuery(hql);
        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                q.setParameter(i, param[i]);
            }
        }
        return q.list();
    }


    /**
     * 通过JDBC查找对象集合 使用指定的检索标准检索数据返回数据
     */
    public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
        try {
            return this.hiSession.getJdbcTemplate().queryForMap(sql, objs);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * 通过JDBC查找对象集合 使用指定的检索标准检索数据返回数据
     */
    public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
        return this.hiSession.getJdbcTemplate().queryForList(sql, objs);
    }

    public List<Map<String,Object>> queryForList(String sql){
        return this.hiSession.getJdbcTemplate().queryForList(sql);
    }

    @Override
    public Object queryEntityById(String sql, Object t) {
        return this.hiSession.getJdbcTemplate().queryForObject(sql, t.getClass());
    }


    public Integer executeSql(String sql, Object[] param) {
        return this.hiSession.getJdbcTemplate().update(sql, param);
    }

    /**
     * 通过hql 查询语句查找HashMap对象
     *
     * @param hql
     * @return
     */
    public Map<Object, Object> getHashMapbyQuery(String hql) {

        Query query = this.hiSession.getSession().createQuery(hql);
        List list = query.list();
        Map<Object, Object> map = new HashMap<Object, Object>();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Object[] tm = (Object[]) iterator.next();
            map.put(tm[0].toString(), tm[1].toString());
        }
        return map;

    }



    /**
     * 通过sql查询语句查找对象
     *
     * @param <T>
     * @param sql
     * @return
     */
    public <T>List<T> findListbySql(String sql) {
        Query querys = this.hiSession.getSession().createSQLQuery(sql);
        return querys.list();
    }


    /**
     * 通过sql更新记录
     *
     * @param query
     * @return
     */
    public int updateBySqlString(String query) {

        Query querys = this.hiSession.getSession().createSQLQuery(query);
        return querys.executeUpdate();
    }

    /**
     * 通过hql查询唯一对象
     *
     * @param <T>
     * @param hql
     * @return
     */
    public <T> T singleResult(String hql) {
        T t = null;
        Query queryObject = this.hiSession.getSession().createQuery(hql);
        List<T> list = queryObject.list();
        if (list.size() == 1) {
            this.hiSession.getSession().flush();
            t = list.get(0);
        } else if (list.size() > 0) {
            logger.info("查询结果数:" + list.size() + "大于1");
        }
        return t;
    }


    /**
     * 根据主键删除指定的实体
     *
     * @param <T>
     * @param id
     */
    public <T> void deleteEntityById(Class entityName, Serializable id) {
        delete(get(entityName, id));
        this.hiSession.getSession().flush();
    }

    /**
     * 删除全部的实体
     *
     * @param <T>
     *
     * @param entitys
     */
    public <T> void deleteAllEntitie(Collection<T> entitys) {
        for (Object entity : entitys) {
            this.hiSession.getSession().delete(entity);
            this.hiSession.getSession().flush();
        }
    }



    public <T> List<T> loadAll(final Class<T> entityClass) {
        Criteria criteria = createCriteria(entityClass);
        return criteria.list();
    }




    /**
     * 根据属性名和属性值查询. 有排序
     *
     * @param <T>
     * @param entityClass
     * @param propertyName
     * @param value
     * @param isAsc
     * @return
     */
    public <T> List<T> findByPropertyisOrder(Class<T> entityClass,
                                             String propertyName, Object value, boolean isAsc) {
        Assert.hasText(propertyName);
        return createCriteria(entityClass, isAsc,
                Restrictions.eq(propertyName, value)).list();
    }

    /**
     * 根据传入的实体持久化对象
     */
    public <T> Serializable save(T entity) {
        try {
            Serializable id = this.hiSession.getSession().save(entity);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("保存实体成功," + entity.getClass().getName());
            }
            return id;
        } catch (RuntimeException e) {
            logger.error("保存实体异常", e);
            throw e;
        }

    }


    /**
     * 根据传入的实体添加或更新对象
     *
     * @param <T>
     *
     * @param entity
     */

    public <T> void saveOrUpdate(T entity) {
        try {
            this.hiSession.getSession().saveOrUpdate(entity);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("添加或更新成功," + entity.getClass().getName());
            }
        } catch (RuntimeException e) {
            logger.error("添加或更新异常", e);
            throw e;
        }
    }


    /**
     * 根据主键获取实体并加锁。
     *
     * @param <T>
     * @param entityName
     * @param id
     * @return
     */
    public <T> T getEntity(Class entityName, Serializable id) {

        T t = (T) this.hiSession.getSession().get(entityName, id);
        if (t != null) {
            this.hiSession.getSession().flush();
        }
        return t;
    }


    /**
     * 更新指定的实体
     *
     * @param <T>
     * @param pojo
     */
    public <T> void updateEntitie(T pojo) {
        this.hiSession.getSession().update(pojo);
        this.hiSession.getSession().flush();
    }

    public SqlRowSet findForJdbcSet(String sql, Object... param){
        return this.hiSession.getJdbcTemplate().queryForRowSet(sql, param);
    }

    /**
     * 根据传入的实体删除对象
     */
    public <T> void delete(T entity) {
        try {
            this.hiSession.getSession().delete(entity);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("删除成功," + entity.getClass().getName());
            }
        } catch (RuntimeException e) {
            logger.error("删除异常", e);
            throw e;
        }
    }

    /**
     * 根据Id获取对象。
     */
    public <T> T get(Class<T> entityClass, final Serializable id) {

        return (T) this.hiSession.getSession().get(entityClass, id);

    }

    /**
     * 根据实体名字获取唯一记录
     *
     * @param propertyName
     * @param value
     * @return
     */
    public <T> T findUniqueByProperty(Class<T> entityClass,
                                      String propertyName, Object value) {
        Assert.hasText(propertyName);
        return (T) createCriteria(entityClass,
                Restrictions.eq(propertyName, value)).uniqueResult();
    }

    /**
     * 根据实体名字获取唯一记录
     *
     * @param value
     * @return
     */
    public <T> T findUniqueByID(Class<T> entityClass,Object value) {
        return (T) createCriteria(entityClass,
                Restrictions.eq("id", value)).uniqueResult();
    }


    /**
     * 创建Criteria对象，有排序功能。
     *
     * @param <T>
     * @param entityClass
     * @param isAsc
     * @param criterions
     * @return
     */
    private <T> Criteria createCriteria(Class<T> entityClass, boolean isAsc,
                                        Criterion... criterions) {
        Criteria criteria = createCriteria(entityClass, criterions);
        if (isAsc) {
            criteria.addOrder(Order.asc("asc"));
        } else {
            criteria.addOrder(Order.desc("desc"));
        }
        return criteria;
    }


    /**
     * 创建Criteria对象带属性比较
     *
     * @param <T>
     * @param entityClass
     * @param criterions
     * @return
     */
    private <T> Criteria createCriteria(Class<T> entityClass,
                                        Criterion... criterions) {
        Criteria criteria = this.hiSession.getSession().createCriteria(entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }


    /**
     * 创建单一Criteria对象
     *
     * @param <T>
     * @param entityClass
     * @return
     */
    private <T> Criteria createCriteria(Class<T> entityClass) {
        Criteria criteria = this.hiSession.getSession().createCriteria(entityClass);
        return criteria;
    }

    public String SqlRowSetToJosn(SqlRowSet rs){
        StringBuilder sb = new StringBuilder();
        String[] fields = rs.getMetaData().getColumnNames();
        sb.append("[");
        while(rs.next()){
            sb.append("{");
            for(int i=0;i<fields.length;i++){
                String column="\""+fields[i]+"\":\""+ObjToString(rs.getString(i+1))+"\"";
                sb.append(column);
                if(i!=fields.length-1)sb.append(",");
            }
            sb.append("},");
        };
        String json=sb.toString();
        if (json.length()>1)json=json.substring(0,json.length()-1);
        return json+"]";
    }

    private String ObjToString(Object obj){
        if(obj==null||obj.equals("null"))return "";
        else return obj.toString().replaceAll("\\n","").replaceAll("\\\\",",");
    }


}

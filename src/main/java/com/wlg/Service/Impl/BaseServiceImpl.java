package com.wlg.Service.Impl;

import com.wlg.Dao.BaseDao;
import com.wlg.Service.BaseService;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by wlg on 2016/6/14.
 */
@Service
public class BaseServiceImpl implements BaseService {
    @Resource
    private BaseDao baseDao;

    @Transactional(propagation=Propagation.REQUIRED)
    public <T> Serializable save(T entity) {
        return this.baseDao.save(entity);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public SqlRowSet findForJdbcSet(String sql, Object... objs) {
        return baseDao.findForJdbcSet(sql,objs);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public <T> void saveOrUpdate(T entity) {
        this.baseDao.saveOrUpdate(entity);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public <T> void updateEntitie(T pojo) {
        this.baseDao.updateEntitie(pojo);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public <T> void delete(T entitie) {
        this.baseDao.delete(entitie);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public <T> T get(Class<T> entityName, Serializable id) {
        return this.baseDao.get(entityName,id);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value) {
        return this.baseDao.findUniqueByProperty(entityClass,propertyName,value);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) {
        return this.baseDao.findByProperty(entityClass,propertyName,value);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public <T> List<T> loadAll(Class<T> entityClass) {
        return this.baseDao.loadAll(entityClass);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public <T> T getEntity(Class entityName, Serializable id) {
        return this.baseDao.getEntity(entityName,id);
    }


    @Transactional(propagation=Propagation.REQUIRED)
    public <T> void deleteEntityById(Class entityName, Serializable id) {
        this.baseDao.deleteEntityById(entityName,id);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public <T> void deleteAllEntitie(Collection<T> entities) {
        this.baseDao.deleteAllEntitie(entities);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public <T> void updateEntityById(Class entityName, Serializable id) {
        this.baseDao.updateEntityById(entityName,id);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public <T> List<T> findByQueryString(String hql) {
        return this.baseDao.findByQueryString(hql);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public <T> T singleResult(String hql) {
        return this.baseDao.singleResult(hql);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public int updateBySqlString(String sql) {
        return this.baseDao.updateBySqlString(sql);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public <T> List<T> findListbySql(String query) {
        return this.baseDao.findListbySql(query);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public <T> List<T> findByPropertyisOrder(Class<T> entityClass, String propertyName, Object value, boolean isAsc) {
        return this.baseDao.findByPropertyisOrder(entityClass,propertyName,value,isAsc);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public Map<Object, Object> getHashMapbyQuery(String query) {
        return this.baseDao.getHashMapbyQuery(query);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public Integer executeSql(String sql, Object[] param) {
        return this.baseDao.executeSql(sql,param);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
        return this.baseDao.findForJdbc(sql,objs);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
        return this.baseDao.findOneForJdbc(sql,objs);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public <T> List<T> findHql(String hql, Object... param) {
        return this.baseDao.findHql(hql,param);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public String SqlRowSetToJosn(SqlRowSet rs) {
        return this.baseDao.SqlRowSetToJosn(rs);
    }
}

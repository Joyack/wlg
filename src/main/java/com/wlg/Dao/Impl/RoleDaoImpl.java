package com.wlg.Dao.Impl;

import com.wlg.Dao.BaseDao;
import com.wlg.Dao.RoleDao;
import com.wlg.Model.Role;
import com.wlg.Util.HiSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Repository
public class RoleDaoImpl implements RoleDao{
    @Resource
    private HiSession hiSession;
    @Resource
    private BaseDao baseDao;

    /**
    * 初始化Log4j的一个实例
    */
    private static final Logger logger = Logger
        .getLogger(RoleDaoImpl.class);

    @Override
    public <T> Serializable saveRole(Role role) {
        try {
            Serializable id = this.hiSession.getSession().save(role);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("保存实体成功," + role.getClass().getName());
            }
             return id;
        } catch (RuntimeException e) {
            logger.error("保存实体异常", e);
            throw e;
        }
   }

    @Override
    public int updateRole(Role role) {
        this.hiSession.getSession().update(role);
        this.hiSession.getSession().flush();
        return 1;
    }

    @Override
    public int deleteRole(Role role) {
        try {
            this.hiSession.getSession().delete(role);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("删除成功," + role.getClass().getName());
            }
        } catch (RuntimeException e) {
            logger.error("删除异常", e);
            throw e;
        }
        return 1;
    }


    @Override
    public Role getRoleByName(String name) {
        return baseDao.findUniqueByProperty(Role.class,"name",name);
    }
}


package com.wlg.Dao.Impl;

import com.wlg.Dao.BaseDao;
import com.wlg.Dao.UserRoleDao;
import com.wlg.Model.UserRole;
import com.wlg.Util.HiSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Repository
public class UserRoleDaoImpl implements UserRoleDao{
    @Resource
    private HiSession hiSession;
    @Resource
    private BaseDao baseDao;

    /**
    * 初始化Log4j的一个实例
    */
    private static final Logger logger = Logger
        .getLogger(UserRoleDaoImpl.class);

    @Override
    public <T> Serializable saveUserRole(UserRole userRole) {
        try {
            Serializable id = this.hiSession.getSession().save(userRole);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("保存实体成功," + userRole.getClass().getName());
            }
             return id;
        } catch (RuntimeException e) {
            logger.error("保存实体异常", e);
            throw e;
        }
   }

    @Override
    public <T> void updateUserRole(UserRole userRole) {
        this.hiSession.getSession().update(userRole);
        this.hiSession.getSession().flush();
    }

    @Override
    public <T> void deleteUserRole(UserRole userRole) {
        try {
            this.hiSession.getSession().delete(userRole);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("删除成功," + userRole.getClass().getName());
            }
        } catch (RuntimeException e) {
            logger.error("删除异常", e);
            throw e;
        }
    }

}


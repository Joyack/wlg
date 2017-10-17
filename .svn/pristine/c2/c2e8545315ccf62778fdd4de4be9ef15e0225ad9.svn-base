package com.wlg.Dao.Impl;

import com.wlg.Dao.PermissionDao;
import com.wlg.Model.Permission;
import com.wlg.Util.HiSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Repository
public class PermissionDaoImpl implements PermissionDao{
    @Resource
    private HiSession hiSession;

    /**
    * 初始化Log4j的一个实例
    */
    private static final Logger logger = Logger
        .getLogger(PermissionDaoImpl.class);

    @Override
    public <T> Serializable savePermission(Permission permission) {
        try {
            Serializable id = this.hiSession.getSession().save(permission);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("保存实体成功," + permission.getClass().getName());
            }
             return id;
        } catch (RuntimeException e) {
            logger.error("保存实体异常", e);
            throw e;
        }
   }

    @Override
    public <T> void updatePermission(Permission permission) {
        this.hiSession.getSession().update(permission);
        this.hiSession.getSession().flush();
    }

    @Override
    public <T> void deletePermission(Permission permission) {
        try {
            this.hiSession.getSession().delete(permission);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("删除成功," + permission.getClass().getName());
            }
        } catch (RuntimeException e) {
            logger.error("删除异常", e);
            throw e;
        }
    }
}


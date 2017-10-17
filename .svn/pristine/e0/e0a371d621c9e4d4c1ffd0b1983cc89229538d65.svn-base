package com.wlg.Dao.Impl;

import com.wlg.Dao.PermissionRoleDao;
import com.wlg.Model.PermissionRole;
import com.wlg.Util.HiSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Repository
public class PermissionRoleDaoImpl implements PermissionRoleDao{
    @Resource
    private HiSession hiSession;

    /**
    * 初始化Log4j的一个实例
    */
    private static final Logger logger = Logger
        .getLogger(PermissionRoleDaoImpl.class);

    @Override
    public <T> Serializable savePermissionRole(PermissionRole permissionRole) {
        try {
            Serializable id = this.hiSession.getSession().save(permissionRole);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("保存实体成功," + permissionRole.getClass().getName());
            }
             return id;
        } catch (RuntimeException e) {
            logger.error("保存实体异常", e);
            throw e;
        }
   }

    @Override
    public <T> void updatePermissionRole(PermissionRole permissionRole) {
        this.hiSession.getSession().update(permissionRole);
        this.hiSession.getSession().flush();
    }

    @Override
    public <T> void deletePermissionRole(PermissionRole permissionRole) {
        try {
            this.hiSession.getSession().delete(permissionRole);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("删除成功," + permissionRole.getClass().getName());
            }
        } catch (RuntimeException e) {
            logger.error("删除异常", e);
            throw e;
        }
    }
}


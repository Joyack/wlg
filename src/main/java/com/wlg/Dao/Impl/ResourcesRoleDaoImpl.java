package com.wlg.Dao.Impl;

import com.wlg.Dao.ResourcesRoleDao;
import com.wlg.Model.ResourcesRole;
import com.wlg.Util.HiSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Repository
public class ResourcesRoleDaoImpl implements ResourcesRoleDao{
    @Resource
    private HiSession hiSession;

    /**
    * 初始化Log4j的一个实例
    */
    private static final Logger logger = Logger
        .getLogger(ResourcesRoleDaoImpl.class);

    @Override
    public <T> Serializable saveResourcesRole(ResourcesRole resourcesRole) {
        try {
            Serializable id = this.hiSession.getSession().save(resourcesRole);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("保存实体成功," + resourcesRole.getClass().getName());
            }
             return id;
        } catch (RuntimeException e) {
            logger.error("保存实体异常", e);
            throw e;
        }
   }

    @Override
    public <T> void updateResourcesRole(ResourcesRole resourcesRole) {
        this.hiSession.getSession().update(resourcesRole);
        this.hiSession.getSession().flush();
    }

    @Override
    public <T> void deleteResourcesRole(ResourcesRole resourcesRole) {
        try {
            this.hiSession.getSession().delete(resourcesRole);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("删除成功," + resourcesRole.getClass().getName());
            }
        } catch (RuntimeException e) {
            logger.error("删除异常", e);
            throw e;
        }
    }
}


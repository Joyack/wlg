package com.wlg.Dao.Impl;

import com.wlg.Dao.ResourcesDao;
import com.wlg.Model.Permission;
import com.wlg.Model.Resources;
import com.wlg.Util.HiSession;
import org.apache.log4j.Logger;
import org.omg.CORBA.SystemException;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Repository
public class ResourcesDaoImpl implements ResourcesDao{
    @Resource
    private HiSession hiSession;

    /**
    * 初始化Log4j的一个实例
    */
    private static final Logger logger = Logger
        .getLogger(ResourcesDaoImpl.class);

    @Override
    public <T> Serializable saveResources(Resources resources) {
        try {
            Serializable id = this.hiSession.getSession().save(resources);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("保存实体成功," + resources.getClass().getName());
            }
             return id;
        } catch (RuntimeException e) {
            logger.error("保存实体异常", e);
            throw e;
        }
   }

    @Override
    public <T> void updateResources(Resources resources) {
        this.hiSession.getSession().update(resources);
        this.hiSession.getSession().flush();
    }

    @Override
    public <T> void deleteResources(Resources resources) {
        try {
            this.hiSession.getSession().delete(resources);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("删除成功," + resources.getClass().getName());
            }
        } catch (RuntimeException e) {
            logger.error("删除异常", e);
            throw e;
        }
    }


}


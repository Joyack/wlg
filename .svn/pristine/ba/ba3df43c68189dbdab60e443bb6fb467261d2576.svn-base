package com.wlg.Dao.Impl;

import com.wlg.Dao.UserDao;
import com.wlg.Model.User;
import com.wlg.Util.HiSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Repository
public class UserDaoImpl implements UserDao{
    @Resource
    private HiSession hiSession;

    /**
    * 初始化Log4j的一个实例
    */
    private static final Logger logger = Logger
        .getLogger(UserDaoImpl.class);

    @Override
    public <T> Serializable saveUser(User user) {
        try {
            Serializable id = this.hiSession.getSession().save(user);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("保存实体成功," + user.getClass().getName());
            }
             return id;
        } catch (RuntimeException e) {
            logger.error("保存实体异常", e);
            throw e;
        }
   }

    @Override
    public <T> void updateUser(User user) {
        this.hiSession.getSession().update(user);
        this.hiSession.getSession().flush();
    }

    @Override
    public <T> void deleteUser(User user) {
        try {
            this.hiSession.getSession().delete(user);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("删除成功," + user.getClass().getName());
            }
        } catch (RuntimeException e) {
            logger.error("删除异常", e);
            throw e;
        }
    }
}


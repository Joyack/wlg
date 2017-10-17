package com.wlg.Dao.Impl;

import com.sun.org.apache.xml.internal.serializer.utils.SerializerMessages_zh_CN;
import com.wlg.Dao.DeptDao;
import com.wlg.Dao.Impl.hibernate.BaseDao;
import com.wlg.Model.Deptinfo;
import com.wlg.Util.HiSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
@Repository
public class DeptDaoImpl extends BaseDao implements DeptDao {
    @Resource
    private HiSession hiSession;
    @Override
    public <T> Serializable addDept(Deptinfo deptinfo) {
        Serializable id= hiSession.getSession().save(deptinfo);
         return id;
    }

    @Override
    public int delDept(Deptinfo deptinfo) {
        hiSession.getSession().delete(deptinfo);
        return 1;
    }

    @Override
    public int updateDept(Deptinfo deptinfo) {
        hiSession.getSession().update(deptinfo);
        return 1;
    }
}

package com.wlg.Dao.Impl;

import com.wlg.Dao.SupplierDao;
import com.wlg.Model.PageBean;
import com.wlg.Model.Supplier;
import com.wlg.Util.HiSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
@Repository
public class SupplierDaoImpl implements SupplierDao {
    @Resource
    private HiSession hiSession;

    /**
     * 初始化Log4j的一个实例
     */
    private static final Logger logger = Logger
            .getLogger(SupplierDaoImpl.class);
    @Override
    public PageBean queryForList() {
        return null;
    }

    @Override
    public <T> Serializable saveSupplier(Supplier supplier) {
        return hiSession.getSession().save(supplier);
    }

    @Override
    public <T> void updateSupplier(Supplier supplier) {
         hiSession.getSession().update(supplier);

        this.hiSession.getSession().flush();
    }

    @Override
    public <T> void deleteSupplier(Supplier supplier) {
        hiSession.getSession().delete(supplier);
    }
}

package com.wlg.Dao;

import com.wlg.Model.PageBean;
import com.wlg.Model.Supplier;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
public interface SupplierDao {
    public PageBean queryForList();
    <T> Serializable saveSupplier(Supplier supplier);

    <T> void updateSupplier(Supplier supplier);

    <T> void deleteSupplier(Supplier supplier);
}

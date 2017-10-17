package com.wlg.Service;

import com.wlg.Model.PageBean;
import com.wlg.Model.Supplier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
public interface SupplierService {
    public int addSupplier(Supplier supplier);
    public int delSupplier(Supplier supplier);
    public int updateSupplier(Supplier supplier);
    public PageBean querySupplierList(int pageno, int pageSize, Map<String,Object> params);
    public Supplier querySupplierById(String providerid);

    public List<Map<String,Object>> querySupplierByName(String proname);
}

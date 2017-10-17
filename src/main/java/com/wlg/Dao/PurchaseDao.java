package com.wlg.Dao;

import com.wlg.Model.GoodsInfo;
import com.wlg.Model.PageBean;
import com.wlg.Model.Purchase;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
@Service
public interface PurchaseDao  {
    public <T>Serializable savePurchase(Purchase purchase);
    public Purchase getPurByPurId(Map<String,Object> params);
    public PageBean<Purchase> getPurListByPage(PageBean pageBean,String hql,Map<String,Object> params);
    public List<Purchase> getPurListByParams(Map<String,Object> params);

    public int delPurchase(Purchase purchase);
    public int updatePur(Purchase purchase);
    public int getAllRowCount(String hql);
}

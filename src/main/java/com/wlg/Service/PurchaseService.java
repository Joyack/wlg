package com.wlg.Service;

import com.wlg.Model.PageBean;
import com.wlg.Model.Purchase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
@Service
public interface PurchaseService {
    public int savePurchase(Purchase purchase);
    public Purchase getPurByPurId(Map<String,Object> params);
    public PageBean getPurListByPage(int pageno,int pageSize, Map<String,Object> params);
    public PageBean getPurListByParams(int pageNo,int pageSize,Map<String,Object> params);
    public String getNextPurId();
    public int delPurchase(Purchase purchase);
    public int updatePur(Purchase purchase);
    public PageBean getPurDetailByPurId(int pageno,int pageSize,String cgid);


}

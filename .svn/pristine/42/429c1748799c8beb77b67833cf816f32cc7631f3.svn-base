package com.wlg.Service;

import com.wlg.Model.FaultInfo;
import com.wlg.Model.PageBean;
import com.wlg.Model.StockInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/15 0015.
 */
public interface StockService {
    public PageBean queryStockList(int page,int pageSize,Map<String,Object> map);
    public <T> Serializable addStock(StockInfo stockInfo);
    public int delStock(StockInfo stockInfo);
    public int updateStock(StockInfo stockInfo);

    public PageBean queryStockOutinList(int page,int pageSize,Map<String, Object> map);

    PageBean<Map<String,Object>> getStockList(int page, int pageSize, Map<String, Object> params);
    public PageBean<Map<String, Object>> queryStockByPage(int page, int pageSize, Map<String, Object> params);
    List<Map<String,Object>> exportOutinStock(String id);

    public int addFaultInfo(FaultInfo faultInfo);
    public String getNextFid(String gid);
    public PageBean<Map<String, Object>> getFlautInfo(int page, int pageSize, Map<String, Object> params);
    public int setHqWarning(String gid,int num);
    public StockInfo getStockById(String id);
}

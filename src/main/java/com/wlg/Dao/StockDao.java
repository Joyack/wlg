package com.wlg.Dao;

import com.wlg.Model.FaultInfo;
import com.wlg.Model.StockInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/15 0015.
 */
public interface StockDao {
    public <T>Serializable addStock(StockInfo stockInfo);
    public int updateStock(StockInfo stockInfo);
    public int delStock(StockInfo stockInfo);

    public int addFaultInfo(FaultInfo faultInfo);

    StockInfo getStockById(String id);
}

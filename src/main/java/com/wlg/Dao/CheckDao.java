package com.wlg.Dao;

import com.wlg.Model.CkStockInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/7/5 0005.
 */
public interface CheckDao {
    public int addCkImportData(List<CkStockInfo> ckStockInfoList);
}

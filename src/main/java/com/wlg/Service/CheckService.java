package com.wlg.Service;

import com.wlg.Model.CkStockInfo;
import com.wlg.Model.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/15 0015.
 */
public interface CheckService {
    public int addCkImportData(List<CkStockInfo> list);
    public PageBean getCkCountResultList(int page, int pageSize, Map<String,Object> params);
    public PageBean getCkDetail(int page,int pageSize,Map<String,Object> params);
    public PageBean downLoadCheckModel(int page,int pageSize,Map<String,Object> params);
}

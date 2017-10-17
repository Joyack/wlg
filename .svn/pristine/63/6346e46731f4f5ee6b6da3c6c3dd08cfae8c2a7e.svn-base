package com.wlg.Service;

import com.wlg.Model.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/12 0012.
 */
public interface ReportService {

    /*原材料库存表*/
    public PageBean rmInventory(int page,int pageSize,String currtime);

    /*原材料入库流水*/

    public PageBean rmInStock(int page,int pageSize,String params);

    /*原材料出库流水*/

    public PageBean rmOutStock(int page,int pageSize,String params);

    /*成品库存表*/
    public PageBean fgInventory(int page,int pageSize,String params);

    /*成品入库流水*/
    public PageBean fgInStock(int page,int pageSize,String params);
    /*成品出库流水*/

    public PageBean fgOutStock(int page,int pageSize,String params);


    public PageBean materialSummary(int page,int pageSize,String params);
    public List<Map<String,Object>> getDateList(String flag);
}

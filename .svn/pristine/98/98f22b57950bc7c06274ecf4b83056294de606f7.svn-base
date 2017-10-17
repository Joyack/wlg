package com.wlg.Service;

import com.wlg.Model.AuditInfo;
import com.wlg.Model.CkStockInfo;
import com.wlg.Model.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/15 0015.
 */
public interface AuditService {
    public PageBean<Map<String,Object>> getAllAuditInfo(int page,int pageSize,Map<String,Object> params,String user);
    public int addAuditInfo(AuditInfo auditInfo,String username,String subuser,String csuser,String audituser);
    public List<Map<String,Object>> getAuditDetail(int page,int pageSize,Map<String,Object> params);
    public int updateAudit(AuditInfo auditInfo);
    public AuditInfo getAuditByid(String id);
}

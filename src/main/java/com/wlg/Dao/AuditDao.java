package com.wlg.Dao;

import com.wlg.Model.AuditInfo;

/**
 * Created by Administrator on 2017/7/3 0003.
 */
public interface AuditDao {
    public int addAuditInfo(AuditInfo auditInfo,String user,String subuser,String csuser,String audituser);
    public int cancelAuditInfo(AuditInfo auditInfo);//修改状态  01 正常  00撤销
    public int updateAudit(AuditInfo auditInfo);

    public AuditInfo getAuditByid(String id);
}

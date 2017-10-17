package com.wlg.Dao.Impl;

import com.wlg.Dao.AuditDao;
import com.wlg.Model.AuditInfo;
import com.wlg.Util.HiSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/7/3 0003.
 */
@Repository
public class AuditDaoImpl implements AuditDao {
    @Resource
    private HiSession hiSession;
    @Override
    public int addAuditInfo(AuditInfo auditInfo, String user, String subuser, String csuser, String audituser) {

         hiSession.getSession().save(auditInfo);
         return 1;
    }

    @Override
    public int cancelAuditInfo(AuditInfo auditInfo) {
        auditInfo.setStatus("00");
        hiSession.getSession().update(auditInfo);
        return 1;
    }

    @Override
    public int updateAudit(AuditInfo auditInfo) {
        hiSession.getSession().update(auditInfo);
        return 1;
    }

    @Override
    public AuditInfo getAuditByid(String id) {
        return (AuditInfo) hiSession.getSession().get(AuditInfo.class,id);
    }
}

package com.wlg.Service.Impl;

import com.wlg.Dao.AuditDao;
import com.wlg.Dao.MemberDao;
import com.wlg.Model.AuditInfo;
import com.wlg.Model.PageBean;
import com.wlg.Service.AuditService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/3 0003.
 */
@Service
public class AuditServiceImpl implements AuditService {
    @Resource
    private AuditDao auditDao;

    @Resource
    private MemberDao memberDao;

    @Override
    @Transactional
    public int addAuditInfo(AuditInfo auditInfo,String user,String subuser,String csuser,String audituser) {

        return auditDao.addAuditInfo(auditInfo,user,subuser,csuser,audituser);
    }
    @Override
    @Transactional
    public AuditInfo getAuditByid(String id){
        return auditDao.getAuditByid(id);
    }

    @Override
    public PageBean<Map<String,Object>> getAllAuditInfo(int page,int pageSize,Map<String,Object> params,String user){
        Object[] objects = null;
        List<String> p = new ArrayList<>();
        int parmsize=params.size();
        StringBuffer sql = new StringBuffer(" select * from (select a.id,a.content, a.subperson, " +
                "(select u.uname from user u where u.username=a.subPerson) subpername,a.auditperson, " +
                "(select u.uname from user u where u.username=a.auditperson) auditpername,a.auditperson1, " +
                "(select u.uname from user u where u.username=a.auditperson1) auditpername1,a.csperson, a.title,a.createtime, " +
                "(select g.gname from goodsinfo g where g.id=s.gid ) gname,s.snum,s.auditstatus ,a.sid " +
                "                  from auditinfo a LEFT JOIN stockinfo s on a.sid=s.id where ifnull(a.auditstatus,'')<>'04') t where 1=1   ");
        if (params.size() > 0) {
           // objects = new Object[params.get("findkey") != null && params.get("findkey") != "" ? params.size() + 1 : params.size()];
            for (String key : params.keySet()) {
                Object paramValue = params.get(key);
                if (paramValue == null) {
                    continue;
                }
                if (key.equals("findkey")) {
                    parmsize+=1;
                    sql.append(" and ( t.gname like  ? or t.auditstatus = ? )");
                    p.add(paramValue + "%");
                    p.add(paramValue + "");
                } else if (key.equals("atype")) {
                   if(paramValue.equals("1")){//我提交的
                       sql.append(" and t.subperson=? ");
                       p.add(""+user);
                   }else if(paramValue.equals("2")){//我审核的
                       parmsize+=1;
                       sql.append(" and (t.auditperson=? or t.auditperson1=? )");
                       p.add(""+user);
                       p.add(""+user);
                   }else if(paramValue.equals("3")){
                       sql.append(" and t.csperson like ? ");
                       p.add(user+"%");
                   }
                }else if(key.equals("begintime")){
                    sql.append(" and t.createtime>?");
                    p.add(paramValue+"");
                }else if(key.equals("endtime")){
                    sql.append(" and t.createtime<=?");
                    p.add(paramValue+"");
                }
            }
            sql.append(" order by t.createtime desc");
            objects = new Object[parmsize];
            for (int i = 0; i < objects.length; i++) {
                objects[i] = p.get(i);
            }
        }
        return memberDao.queryMapsBypage(objects,page,pageSize,sql.toString());
    }







    @Override
    public List<Map<String,Object>> getAuditDetail(int page,int pageSize,Map<String,Object> params){
        Object[] objects = null;
        List<String> p = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select * from (select a.id,a.content,a.audittype, a.subperson, " +
                "(select u.uname from user u where u.username=a.subPerson) subpername,a.auditperson, " +
                "(select u.uname from user u where u.username=a.auditperson) auditpername,a.auditperson1, " +
                "(select u.uname from user u where u.username=a.auditperson1) auditpername1,a.csperson, a.title,a.createtime, " +
                "(select g.gname from goodsinfo g where g.id=s.gid ) gname,s.snum,s.auditstatus ,a.sid " +
                "                  from auditinfo a LEFT JOIN stockinfo s on a.sid=s.id and a.auditstatus<>'04') t where 1=1   ");
        if (params.size() > 0) {
            objects = new Object[params.get("findkey") != null && params.get("findkey") != "" ? params.size() + 1 : params.size()];
            for (String key : params.keySet()) {
                Object paramValue = params.get(key);
                if (paramValue == null) {
                    continue;
                }
                if (key.equals("findkey")) {
                    sql.append(" and ( t.gname like '%"+paramValue+"' or t.provider like '%"+paramValue+"' )");

                } else if(key.equals("auditid")){
                    sql.append(" and t.id='"+paramValue+"'");
                }
            }

        }
        return memberDao.queryForPageByParams(page,pageSize,sql.toString()).getResult();
    }

    @Override
    @Transactional
    public int updateAudit(AuditInfo auditInfo) {
        auditDao.updateAudit(auditInfo);
        return 1;
    }
}

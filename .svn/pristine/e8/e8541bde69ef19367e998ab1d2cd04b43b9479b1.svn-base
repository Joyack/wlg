package com.wlg.Service.Impl;

import com.wlg.Dao.CheckDao;
import com.wlg.Dao.MemberDao;
import com.wlg.Model.CkStockInfo;
import com.wlg.Model.PageBean;
import com.wlg.Service.CheckService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/30 0030.
 */
@Service
public class CheckServiceImpl implements CheckService {
    @Resource
    private MemberDao memberDao;

    @Resource
    private CheckDao checkDao;

    @Override
    @Transactional
    public int addCkImportData(List<CkStockInfo> list) {
        return checkDao.addCkImportData(list);
    }

    @Override
    public PageBean getCkCountResultList(int page, int pageSize, Map<String, Object> params) {
        StringBuffer sql=new StringBuffer();
        List<String> p=new ArrayList<>();
        Object[] objects=null;
        sql.append("select * from (" +
                "select t.ckdate,t.ckperson,sum(t.cknum-t.csnum) yk,t.batchno " +
                "from ckstockinfo t group by t.ckdate,t.batchno order by t.ckdate desc) tt where 1=1 ");
        if(params.size()>0){
            objects=new Object[params.size()];
            for(String key:params.keySet()){
                Object paramValue=params.get(key);
                if(paramValue==null){
                    continue;
                }
                if(key.equals("findkey")){
                    sql.append(" and  tt.ckperson = ? "  );
                    p.add(paramValue+"");
                }
                if(key.equals("begintime")){
                    sql.append(" and tt.ckdate >= ? "  );
                    p.add(""+paramValue);
                }
                if(key.equals("endtime")){
                    sql.append(" and  tt.ckdate < ? "  );
                    p.add(""+paramValue);
                }if(key.equals("ckperson")){
                    sql.append(" and  tt.ckperson = ? "  );
                    p.add(""+paramValue);
                }
            }
            for(int i=0;i<objects.length;i++){
                objects[i]=p.get(i);
            }
        }
        return memberDao.queryMapsBypage(objects,page,pageSize,sql.toString());
    }

    @Override
    public PageBean getCkDetail(int page, int pageSize, Map<String, Object> params) {
             List<String> p=new ArrayList<>();
            Object[] objects=null;
            StringBuffer sql=new StringBuffer(" select * from (" +
                    "select t.ckid, t.ckfnum,  g.id,g.gname,g.gspec,g.gtid,(select p.proname from supplier p where p.id=g.providerid) provider,g.unit,t.csnum,t.cknum,(t.cknum-t.csnum) yk ,t.ckreason,t.ckdate,t.batchno  " +
                    "from ckstockinfo t,goodsinfo g  " +
                    "where t.gid=g.id order by t.ckid,t.ckdate desc) tt where 1=1 ");
            if(params.size()>0){
                objects=new Object[params.get("findkey")!=null && params.get("findkey")!=""?params.size()+1:params.size()];
                for(String key:params.keySet()){
                    Object paramValue=params.get(key);
                    if(paramValue==null){
                        continue;
                    }
                    if(key.equals("findkey")){
                        sql.append(" and ( tt.gname like  ? or tt.provider like ? )"  );
                        p.add(paramValue+"%");
                        p.add(paramValue+"%");
                    }
                    else if(key.equals("begintime")){
                        sql.append(" and tt.ckdate >= ? "  );
                        p.add(""+paramValue);
                    }
                    else if(key.equals("endtime")){
                        sql.append(" and  tt.ckdate < ? "  );
                        p.add(""+paramValue);
                    }
                    else if(key.equals("ckdate")){
                        sql.append(" and  tt.ckdate = ? "  );
                        p.add(""+paramValue);
                    }
                    else if(key.equals("batchno")){
                        sql.append(" and  tt.batchno = ? "  );
                        p.add(""+paramValue);
                    }
                }
                for(int i=0;i<objects.length;i++){
                    objects[i]=p.get(i);
                }
            }
            return memberDao.queryMapsBypage(objects,page,pageSize,sql.toString());


    }

    @Override
    public PageBean downLoadCheckModel(int page, int pageSize, Map<String, Object> params) {
        StringBuffer sql=new StringBuffer();
        List<String> p=new ArrayList<>();
        Object[] objects=null;
        sql.append("SELECT tt.*,(tt.storagenum-tt.fsum) nsum from (select g.id,g.gid,g.gname,g.gspec,g.unit,(select p.proname from supplier p where p.id=g.providerid) provider,g.gtid, " +
                "IFNULL(g.storagenum,0) storagenum, " +
                "IFNULL(g.faultnum,0) fsum ,g.providerid " +
                " from goodsinfo g ) tt where 1=1 ");
        if(params.size()>0){
            objects=new Object[params.size()];
            for(String key:params.keySet()){
                Object paramValue=params.get(key);
                if(paramValue==null){
                    continue;
                }
                if(key.equals("findkey")){
                    sql.append(" and  tt.ckperson = ? "  );
                    p.add(paramValue+"");
                }
                if(key.equals("begintime")){
                    sql.append(" and tt.ckdate >= ? "  );
                    p.add(""+paramValue);
                }
                if(key.equals("endtime")){
                    sql.append(" and  tt.ckdate < ? "  );
                    p.add(""+paramValue);
                }if(key.equals("ckperson")){
                    sql.append(" and  tt.ckperson = ? "  );
                    p.add(""+paramValue);
                }
            }
            for(int i=0;i<objects.length;i++){
                objects[i]=p.get(i);
            }
        }
        return memberDao.queryMapsBypage(objects,page,pageSize,sql.toString());
    }
}

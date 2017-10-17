package com.wlg.Service.Impl;

import com.wlg.Dao.MemberDao;
import com.wlg.Dao.StockDao;
import com.wlg.Model.FaultInfo;
import com.wlg.Model.PageBean;
import com.wlg.Model.StockInfo;
import com.wlg.Service.MemberService;
import com.wlg.Service.StockService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/15 0015.
 */
@Service
public class StockServiceImpl implements StockService {
    @Resource
    private MemberDao memberDao;

    @Resource
    private StockDao stockDao;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;



    @Override
    public PageBean<Map<String, Object>> queryStockByPage(int page, int pageSize, Map<String, Object> params) {
        Object[] objects = null;
        List<String> p = new ArrayList<>();
        StringBuffer sql = new StringBuffer(" select * from (   " +
                "                  select g.id,g.gid,g.gname,g.gtid,g.gspec,g.unit,   " +
                "                  (select p.proname from supplier p where p.id=g.providerid) provider,   " +
                "                  ifnull((select sum(s.snum) from stockinfo s where s.sopertype='01' and s.gid=g.id and IFNULL(s.auditstatus,'')<>'04'  and IFNULL(s.auditstatus,'')='00'),0) hisinsum,   " +
                "                  ifnull((select sum(s.snum) from stockinfo s where s.sopertype='02' and s.gid=g.id and IFNULL(s.auditstatus,'')<>'04' and IFNULL(s.auditstatus,'')='00'),0) hisoutsum ,   " +
                "                   ifnull(g.warnnum,0) warnnum,ifnull(g.storagenum,0) storagenum,ifnull(g.faultnum,0) faultnum,  " +
                "                   ifnull((select sum(s.snum ) from stockinfo s where s.gid=g.id and s.auditstatus in('01','03','05')),0) anum, "+
                "                  (ifnull(g.storagenum,0)-ifnull(g.faultnum,0)) nsum from goodsinfo g )t where 1=1 ");
        if (params.size() > 0) {
            objects = new Object[params.get("findkey") != null && params.get("findkey") != "" ? params.size() + 1 : params.size()];
            for (String key : params.keySet()) {
                Object paramValue = params.get(key);
                if (paramValue == null) {
                    continue;
                }
                if (key.equals("findkey")) {
                    sql.append(" and ( t.gname like  ? or t.provider like ? )");
                    p.add(paramValue + "%");
                    p.add(paramValue + "%");
                } else if (key.equals("gid")) {
                    sql.append(" and t.id=?");
                    p.add("" + paramValue);
                }else if(key.equals("gtid")){
                    sql.append(" and t.gtid=?");
                    p.add(""+paramValue);
                }
            }
            for (int i = 0; i < objects.length; i++) {
                objects[i] = p.get(i);
            }
        }

        return memberDao.queryMapsBypage(objects, page, pageSize, sql.toString());
    }
    @Override
    public PageBean queryStockList(int page,int pageSize,Map<String, Object> params) {
        Object[] condi=null;
        List<String> p=new ArrayList<>();

        StringBuffer sql=new StringBuffer("select t.gid,t.gname,t.gspec,t.unit,t2.proname goodsinfo t,supplier t2 where 1=1 and t.providerid=t2.id ");
        if(params.size()>0){
            condi=new Object[params.get("findkey")!=null && params.get("findkey")!=""?params.size()+1:params.size()];

            for (String key:params.keySet()){
                Object pvalue=params.get(key);
                if(pvalue==null || "".equals(pvalue)){
                    continue;
                }
                if(key.equals("gname")){
                    sql.append(" and t.gname=? ");
                    p.add(""+pvalue);
                }else if(key.equals("provider")){
                    sql.append(" and t2.proname= ? ");
                    p.add(""+pvalue);
                }else if(key.equals("gtid")){
                    sql.append(" and t.gtid=? ");
                    p.add(""+pvalue);
                }
            }
            for(int i=0;i<condi.length;i++){
                condi[i]=p.get(i);
            }
        }
        return memberDao.queryMapsBypage(condi,page,pageSize,sql.toString());
    }

    @Override
    @Transactional
    public <T> Serializable addStock(StockInfo stockInfo) {
        if(stockInfo.getSopertype().equals("01")){
            if(!stockInfo.getStype().equals("02")){
                String sid=getNextStockId(stockInfo.getSopertype(),null,null);
                stockInfo.setSid(sid);
            }
        }else if(stockInfo.getSopertype().equals("02")){
            String sid=getNextStockId(stockInfo.getSopertype(),null,stockInfo.getStype1());
            stockInfo.setSid(sid);
        }
      Serializable id=stockDao.addStock(stockInfo);
      return id;
    }

    @Override
    @Transactional
    public int delStock(StockInfo stockInfo) {
         stockDao.delStock(stockInfo);
         return 1;
    }

    @Override
    @Transactional
    public int updateStock(StockInfo stockInfo) {
        stockDao.updateStock(stockInfo);
        return 1;
    }

    @Override
    public PageBean queryStockOutinList(int page,int pageSize,Map<String, Object> params) {
        Object[] condi=null;
        List<String> p=new ArrayList<>();
        StringBuffer sql=new StringBuffer("select * from " +
                "(select t3.id,t3.sid, t.gid,t.gname,t.gspec,t.gtid,t.unit,t3.sperson,t2.proname,t3.sopertype,t3.stype,t3.stype1,t3.snum storagenum,t.faultnum,t3.sdate,  t3.auditstatus," +
                "(case when t3.stype1='03' then (select g.gname from goodsinfo g where g.id=t3.museto)  " +
                "when t3.stype1='04' then (select d.deptname from deptinfo d,user u where d.id=u.deptid and u.username=t3.sperson)  " +
                "when t3.stype1='01' then (select xh.customer from xhcontract xh where xh.id=t3.xhid)end) yt "+
                " from  goodsinfo t,supplier t2,stockinfo t3 " +
                "  where 1=1 and t.providerid=t2.id and t.id=t3.gid and ifnull(t3.auditstatus,'')<>'04')tt where 1=1 ");
        if(params.size()>0){
            condi=new Object[params.get("findkey")!=null && params.get("findkey")!=""?params.size()+2:params.size()];
            for (String key:params.keySet()){
                Object pvalue=params.get(key);
                if(pvalue==null || "".equals(pvalue)){
                    continue;
                }if(key.equals("findkey")){
                     sql.append(" and (tt.gname like ? or tt.proname like ? or tt.gspec like ?)");
                     p.add("%"+pvalue);
                     p.add("%"+pvalue);
                     p.add("%"+pvalue);
                }else if(key.equals("gtid")){
                    sql.append(" and tt.gtid= ? ");
                    p.add(""+pvalue);
                }else if(key.equals("sopertype")){
                    sql.append(" and tt.sopertype=? ");
                    p.add(""+pvalue);
                }
                else if(key.equals("begintime")){
                    sql.append(" and tt.sdate>? ");
                    p.add(""+pvalue);
                }
                else if(key.equals("endtime")){
                    sql.append(" and tt.sdate<=? ");
                    p.add(""+pvalue);
                }
            }
            for(int i=0;i<condi.length;i++){
                condi[i]=p.get(i);
            }

        }
        return memberDao.queryMapsBypage(condi,page,pageSize,sql.toString());
    }

    @Override
    public PageBean<Map<String, Object>> getStockList(int page, int pageSize, Map<String, Object> params) {
        return null;
    }


    public String getNextStockId(String sopertype,String stype,String stype1){
        String maxid="";
        String qz="";
        StringBuffer sql=new StringBuffer("select distinct max( CONVERT(SUBSTR(s.sid,3),SIGNED)) maxid from stockinfo s where 1=1 and ifnull(s.auditstatus,'')<>'04' ");
        if(sopertype!=null &&!sopertype.equals("")){
            sql.append(" and s.sopertype='"+sopertype+"'");
              switch (sopertype){
                   case "01":
                      qz="RK";
                      break;
                   case "02":
                       switch (stype1) {
                           case "01":
                               qz = "FH";//发货出库
                               break;
                           case "02":
                               qz = "TH";//销货退货出库
                               break;
                           case "03":
                               qz = "LL";//领料出库
                               break;
                           case "04":
                               qz = "LY";//领用出库
                               break;
                       }
           }
        }if(stype1!=null && !stype1.equals("")){
            sql.append(" and s.stype1='"+stype1+"'");
        }

        List<Map<String,Object>> t= memberDao.queryBySqlForPage(sql.toString()).getResult();
        if(t.size()>0){
            maxid=t.get(0).get("maxid")==null?"0":t.get(0).get("maxid").toString();
        }
        int nextid=Integer.parseInt((maxid==null || maxid.equals(""))?"0":maxid)+1;
        return qz+String.format("%06d",nextid);
    }


@Override
    public List<Map<String,Object>>  exportOutinStock(String id){
        if(id==null);
        StringBuffer bids=new StringBuffer();
        String[] ids=id.split(",");
        for (int i=0;i<ids.length;i++)
        {
            if (i < ids.length - 1) {
                bids.append("'" + ids[i] + "'");
                bids.append(",");
            } else {
                bids.append("'" + ids[i] + "'");
            }
        }
//        StringBuffer sql=new StringBuffer("select * from " +
//                "(select t3.id,t3.sid, t.gid,t.gname,t.gspec,t.gtid,t.unit,t2.proname,t3.sopertype,t3.stype,t3.stype1,t.storagenum,(select ),t.faultnum,t3.sdate " +
//                " from  goodsinfo t,supplier t2,stockinfo t3 " +
//                "  where 1=1 and t.providerid=t2.id and t.id=t3.gid )t where 1=1 ");
    StringBuffer sql=new StringBuffer("select * from  (select t3.id,t3.sid, t.gid,t.gname,t.gspec,t.gtid,t.unit,t2.proname,t3.sopertype,t3.stype,t3.stype1,t3.sperson, " +
            "t.storagenum,case when t3.sopertype='01' then (select a.cgprice from purchase a where a.gid=t3.gid and a.id=t3.cgid ) " +
            "                  when t3.sopertype='02' then (select a.xhprice from xhcontract a where a.gid=t3.gid and a.id=t3.xhid)end price,t.faultnum,t3.sdate " +
            "                 from  goodsinfo t,supplier t2,stockinfo t3 " +
            "                  where 1=1 and t.providerid=t2.id and t.id=t3.gid and IFNULL(t3.auditstatus,'')<>'04')t where 1=1 ");
        sql.append(" and t.id in ("+bids.toString()+") ");

        return memberDao.queryForPageByParams(1000,1,sql.toString()).getResult();
    }


    @Override
    @Transactional
    public int addFaultInfo(FaultInfo faultInfo) {
        return stockDao.addFaultInfo(faultInfo);
    }
    @Override
    public String getNextFid(String gid){
        String maxid="";
        StringBuffer sql=new StringBuffer("select max(t.fid) maxid from faultinfo t where t.gid='"+gid+"' ");
      List<Map<String,Object>> list= memberDao.queryBySqlForPage(sql.toString()).getResult();
        if(list.size()>0){
            maxid=list.get(0).get("maxid")==null?"0":list.get(0).get("maxid").toString();
        }
        int nextsid=Integer.parseInt((maxid==null || maxid.equals(""))?"0":maxid)+1;
        return ""+nextsid;
    }
    @Override
    public int setHqWarning(String gid,int num){
        StringBuffer sql=new StringBuffer("update goodsinfo g set g.warnnum="+num+" where g.id='"+gid+"' ");
        jdbcTemplate.execute(sql.toString());
        return  1;
    }

    @Override
    @Transactional
    public StockInfo getStockById(String id) {
        return stockDao.getStockById(id);
    }

    /*
   * 查询物品故障和修理记录
   * */
    @Override
    public PageBean<Map<String, Object>> getFlautInfo(int page, int pageSize, Map<String, Object> params) {
        Object[] objects=null;
        List<String> p=new ArrayList<>();
        StringBuffer sql=new StringBuffer("select * from ( " +
                "select f.fid,g.gname,g.gtid,g.gspec,g.unit,(select p.proname from supplier p where p.id=g.providerid) provider,  " +
                "f.ftype ,f.fnum,f.fdate,f.fperson " +
                "from faultinfo f,goodsinfo g  " +
                "where 1=1  and f.gid=g.id  and (f.ftype!='03' and f.ftype!='04')  order by f.fdate desc) t where 1=1 ");
        if(params.size()>0){
            objects=new Object[params.get("findkey")!=null && params.get("findkey")!=""?params.size()+1:params.size()];
            for(String key:params.keySet()){
                Object paramValue=params.get(key);
                if(paramValue==null){
                    continue;
                }
                if(key.equals("findkey")){
                    sql.append(" and ( t.gname like  ? or t.provider like ? )"  );
                    p.add(paramValue+"%");
                    p.add(paramValue+"%");
                }else if(key.equals("begintime")){
                    sql.append(" and t.fdate>= ? ");
                    p.add(""+paramValue);
                }else if(key.equals("endtime")){
                    sql.append(" and t.fdate< ?");
                    p.add(""+paramValue);
                }
            }
            for(int i=0;i<objects.length;i++){
                objects[i]=p.get(i);
            }
        }
        //sql.append(sql2.toString());
        return memberDao.queryMapsBypage(objects,page,pageSize,sql.toString());
    }


}

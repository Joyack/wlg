package com.wlg.Service.Impl;

import com.wlg.Dao.MemberDao;
import com.wlg.Service.DiagramReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zj on 2017/7/24 0024.
 */
@Service
public class DiagramReportServiceImpl implements DiagramReportService {
    @Resource
    private MemberDao memberDao;
    @Override
    public List<Map<String, Object>> outInStocknum(Map<String, Object> map) {
        String str="";
        if (map.size()>0){
            if(map.get("ids")!=null){
               str=map.get("ids").toString();
            }
        }
        StringBuffer sql=new StringBuffer("select * from (select * from ( " +
                "SELECT DATE_FORMAT(s.sdate,'%Y-%m') mon,sum( s.snum) stocknum FROM stockinfo s  " +
                "WHERE 1=1 " +
                "and (s.stype in('01','02') or s.stype1 in('01','02','04')) and s.sopertype=?  " +
                "and DATE_FORMAT(s.sdate,'%Y')=?  ");
              if(str!=null&&!str.equals("")){
                  sql.append(" and s.gid in ("+str+")");
              }

               sql.append( "group by DATE_FORMAT(s.sdate,'%Y%m')  " +
                "union  " +
                "select DATE_FORMAT(r.datelist,'%Y-%m') mon,0 stocknum from calendar r " +
                "where DATE_FORMAT(r.datelist,'%Y')=? " +
                " )t GROUP BY t.mon) t2 " );
        String year="";
        String sopertype="";
        if (map!=null && map.size()>0){
            for (String key:map.keySet()){
                String paramValue=map.get(key).toString();
                if(paramValue==null || paramValue.equals("")){
                    continue;
                }
                if(key.equals("findkey")){
                   // sql.append(" and (t.username ='"+paramValue+"' or t.uname ='"+paramValue+"')");
                }else if(key.equals("year")){
                   year=paramValue;
                }else if(key.equals("sopertype")){
                    sopertype=paramValue;
                }
            }
        }

        return memberDao.queryMapsBypage(new Object[]{sopertype,year,year},1,1000,sql.toString()).getResult();
    }

    @Override
    public List<Map<String, Object>> salesStocknum(Map<String, Object> map) {
        String str="";
        if (map.size()>0){
            if(map.get("ids")!=null){
                str=map.get("ids").toString();
            }
        }
        StringBuffer sql=new StringBuffer("select * from (SELECT * from( " +
                "SELECT " +
                "DATE_FORMAT(s.createtime,'%Y-%m') mon,sum(s.xhnum) num " +
                "FROM " +
                "xhcontract s " +
                "WHERE DATE_FORMAT(s.createtime,'%Y')=? " );
        if(str!=null&&!str.equals("")){
            sql.append(" and s.gid in ("+str+")");
        }

        sql.append( "group by  DATE_FORMAT(s.createtime,'%Y%m')  " +
                " union " +
                " select DATE_FORMAT(r.datelist,'%Y-%m') mon,0 num from calendar r  where DATE_FORMAT(r.datelist,'%Y')=? " +
                ")t  " +
                "GROUP BY t.mon) t2");
        String year="";
        if (map!=null && map.size()>0){
            for (String key:map.keySet()){
                String paramValue=map.get(key).toString();
                if(paramValue==null || paramValue.equals("")){
                    continue;
                }

                if(key.equals("findkey")){
                    // sql.append(" and (t.username ='"+paramValue+"' or t.uname ='"+paramValue+"')");
                }else if(key.equals("year")){
                    year=paramValue;
                }else if(key.equals("ids")){
                    sql.append(" and t2.id in("+paramValue+")");
                }
            }
        }

        return memberDao.queryMapsBypage(new Object[]{year,year},1,1000,sql.toString()).getResult();
    }

    @Override
    public List<Map<String, Object>> purchaseStocknum(Map<String, Object> map) {
        String str="";
        if (map.size()>0){
            if(map.get("ids")!=null){
                str=map.get("ids").toString();
            }
        }
        StringBuffer sql=new StringBuffer("select * from (SELECT * from( " +
                "SELECT " +
                "DATE_FORMAT(s.cgdate,'%Y-%m') mon,sum(s.cgnum) num " +
                "FROM " +
                "purchase s " +
                "WHERE DATE_FORMAT(s.cgdate,'%Y')=?" );
        if(str!=null&&!str.equals("")){
            sql.append(" and s.gid in ("+str+")");
        }

        sql.append( "group by  DATE_FORMAT(s.cgdate,'%Y%m') " +
                " union " +
                " select DATE_FORMAT(r.datelist,'%Y-%m') mon,0 num from calendar r  where DATE_FORMAT(r.datelist,'%Y')=?" +
                ")t  " +
                "GROUP BY t.mon)t2 ");
        String year="";
        if (map!=null && map.size()>0){
            for (String key:map.keySet()){
                String paramValue=map.get(key).toString();
                if(paramValue==null || paramValue.equals("")){
                    continue;
                }

                if(key.equals("findkey")){
                    // sql.append(" and (t.username ='"+paramValue+"' or t.uname ='"+paramValue+"')");
                }else if(key.equals("year")){
                    year=paramValue;
                }
            }
        }

        return memberDao.queryMapsBypage(new Object[]{year,year},1,1000,sql.toString()).getResult();
    }

    @Override
    public List<Map<String, Object>> stocknum(Map<String, Object> map) {
        String str="";
        if (map.size()>0){
            if(map.get("ids")!=null){
                str=map.get("ids").toString();
            }
        }
        StringBuffer sql=new StringBuffer("select * from (SELECT * from( " +
                "SELECT " +
                "DATE_FORMAT(s.sdate,'%Y-%m') mon,sum(case when s.sopertype='01' then s.snum  when s.sopertype='02' then -s.snum end) stocknum " +
                "FROM " +
                "stockinfo s " +
                "WHERE DATE_FORMAT(s.sdate,'%Y')=? " );
        if(str!=null && !str.equals("")){
            sql.append(" and s.gid in( "+str+")");
        }
        sql.append("group by  DATE_FORMAT(s.sdate,'%Y%m')  " +
                " union " +
                " select DATE_FORMAT(r.datelist,'%Y-%m') mon,0 stocknum from calendar r  where DATE_FORMAT(r.datelist,'%Y')=? " +
                ")t GROUP BY t.mon)t2 ");
        String year="";
        if (map!=null && map.size()>0){
            for (String key:map.keySet()){
                String paramValue=map.get(key).toString();
                if(paramValue==null || paramValue.equals("")){
                    continue;
                }

                if(key.equals("findkey")){
                    // sql.append(" and (t.username ='"+paramValue+"' or t.uname ='"+paramValue+"')");
                }else if(key.equals("year")){
                    year=paramValue;
                }
            }
        }

        return memberDao.queryMapsBypage(new Object[]{year,year},1,1000,sql.toString()).getResult();
    }
}

package com.wlg.Service.Impl;

import com.wlg.Dao.MemberDao;
import com.wlg.Model.PageBean;
import com.wlg.Service.ReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zj on 2017/7/12 0012.
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Resource
    private MemberDao memberDao;


    @Override
    public PageBean rmInventory(int page,int pageSize,String params) {
        StringBuffer sql=new StringBuffer("select * from (select t5.id,t5.gid,t5.gname,t5.unit,t5.gtid,t5.gspec,t5.provider, " +
                "                       round(t5.preprice,2) preprice,round(t5.premoney,2) premoney,t5.prestocknum, " +
                "                       round(t5.currinprice,2) currinprice,t5.currinstocknum,round(t5.currinmoney,2) currinmoney,round(t5.curroutprice,2) curroutprice,t5.curroutstocknum,round(t5.curroutmoney,2) curroutmoney, " +
                "                       t5.currjcstocknum,round(t5.currjcprice,2) currjcprice,round(t5.currjcmoney,2) currjcmoney,round(t5.currckprice,2) currckprice,ifnull(t5.currcksnum,0) currcksnum, round(IFNULL((t5.currckprice*t5.currcksnum),0),2) currckmoney from (    " +
                "                                  select t4.*,IFNULL((t4.currjcmoney/t4.currjcstocknum),0) currjcprice,IFNULL((t4.currjcmoney/t4.currjcstocknum),0) currckprice from(   " +
                "                                  select t3.*,(t3.premoney+t3.currinmoney-t3.curroutmoney) currjcmoney  from(  " +
                "                                  select t2.*,(t2.curroutstocknum*t2.curroutprice) curroutmoney,(t2.prestocknum+t2.currinstocknum-t2.curroutstocknum) currjcstocknum from (  " +
                "                                  select tt.*,IFNULL(((tt.premoney+tt.currinmoney)/(tt.prestocknum+tt.currinstocknum)),0) curroutprice  from (  " +
                "                                  select t.*,(t.prestocknum*t.preprice) premoney,(t.currinstocknum*t.currinprice) currinmoney from ( " +
                "                                  select g.id,g.gid,g.gname,g.unit,g.gtid,g.gspec,  " +
                "                                  (select p.proname from supplier p where p.id=g.providerid ) provider,   " +
                "                                   IFNULL((select sum(case when s.sopertype='01' then s.snum when s.sopertype='02' then -s.snum end) from stockinfo s where s.gid=g.id and ifnull(s.auditstatus,'')<>'04' and  DATE_FORMAT(s.sdate,'%Y-%m')=? ),0) prestocknum,  " +
                "                                  IFNULL((select sum(case when s.sopertype='01' then (select cg.cgprice from purchase cg where cg.id=s.cgid  )   " +
                "                                                 when  s.sopertype='02' then (select xh.xhprice from xhcontract xh where xh.id=s.xhid ) end) /count(*) from stockinfo s where s.gid=g.id and ifnull(s.auditstatus,'')<>'04' and  DATE_FORMAT(s.sdate,'%Y-%m')=? ),0) preprice,  " +
                "                                   IFNULL((select sum(s.snum) from stockinfo s where s.gid=g.id and s.sopertype='01' and ifnull(s.auditstatus,'')<>'04' and  DATE_FORMAT(s.sdate,'%Y-%m')=?),0) currinstocknum,  " +
                "                                  IFNULL((select sum(s.snum) from stockinfo s where s.gid=g.id and s.sopertype='02' and ifnull(s.auditstatus,'')<>'04' and  DATE_FORMAT(s.sdate,'%Y-%m')=?),0) curroutstocknum,  " +
                "                                 IFNULL((select sum( (select cg.cgprice from purchase cg where cg.id=s.cgid  )) /count(*) from stockinfo s where s.gid=g.id and s.sopertype='01' and ifnull(s.auditstatus,'')<>'04' and  DATE_FORMAT(s.sdate,'%Y-%m')=?),0) currinprice,  " +
                "                                  (select sum(ck.cknum) from ckstockinfo ck where ck.gid=g.id) currcksnum   " +
                "                                   from goodsinfo g where g.gtid in('01','02') )t)tt)t2)t3 )t4)t5) t6 where 1=1  ");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
        Date d=null;
        try {
            d=format.parse(params);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(d);
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String premon = format.format(m);
        return memberDao.queryMapsBypage(new Object[]{ premon,premon,params,params,params},page,pageSize,sql.toString());
    }

    @Override
    public PageBean rmInStock(int page,int pageSize,String params) {
        StringBuffer sql=new StringBuffer("select * from ( " +
                "select t.id,t.gid,t.gname,t.unit,t.gtid,t.gspec, (select p.proname from supplier p where p.id=t.providerid ) provider, " +
                "      t.stocknum,round(t.price,2) price, round((t.stocknum*t.price),2) money from (  " +
                "    select g.*,  " +
                "           IFNULL((select sum(s.snum) from stockinfo s where s.gid=g.id and s.sopertype='01' and DATE_FORMAT(s.sdate,'%Y-%m')=?),0) stocknum,  " +
                "           IFNULL((select sum( (select cg.cgprice from purchase cg where cg.id=s.cgid  )) /count(*)  " +
                "                   from stockinfo s where s.gid=g.id and s.sopertype='01'  and DATE_FORMAT(s.sdate,'%Y-%m')=?),0) price  " +
                "    from goodsinfo g where 1=1 and g.gtid in('01','02'))t)t2");
        return memberDao.queryMapsBypage(new Object[]{params,params},page,pageSize,sql.toString());
    }

    /*
    * 原材料出库流水
    * */
    @Override
    public PageBean rmOutStock(int page,int pageSize,String params) {
        StringBuffer sql=new StringBuffer("select * from (select t2.id,t2.gid,t2.gname,t2.unit,t2.gtid,t2.gspec,t2.provider,t2.curroutstocknum,round(ifnull(t2.curroutprice,0),2) curroutprice,round(ifnull((t2.curroutstocknum*t2.curroutprice),0),2) curroutmoney from (  " +
                "                 select tt.*, ifnull(((tt.premoney+tt.currinmoney)/(tt.prestocknum+tt.currinstocknum)),0)  curroutprice  from (   " +
                "                 select t.*,(t.prestocknum*t.preprice) premoney,(t.currinstocknum*t.currinprice) currinmoney from (   " +
                "                 select g.id,g.gid,g.gname,g.unit,g.gtid,g.gspec,   " +
                "                 (select p.proname from supplier p where p.id=g.providerid ) provider,   " +
                "                  IFNULL((select sum(case when s.sopertype='01' then s.snum when s.sopertype='02' then -s.snum end) from stockinfo s where s.gid=g.id and ifnull(s.auditstatus,'')<>'04' and  DATE_FORMAT(s.sdate,'%Y-%m')=?),0) prestocknum,  " +
                "                 (select sum(case when s.sopertype='01' then (select cg.cgprice from purchase cg where cg.id=s.cgid  )  " +
                "                                when  s.sopertype='02' then (select xh.xhprice from xhcontract xh where xh.id=s.xhid ) end) /count(*) from stockinfo s where s.gid=g.id ) preprice,  " +
                "                  IFNULL((select sum(s.snum) from stockinfo s where s.gid=g.id and s.sopertype='01' and  DATE_FORMAT(s.sdate,'%Y-%m')=?),0) currinstocknum,  " +
                "                 IFNULL((select sum(s.snum) from stockinfo s where s.gid=g.id and s.sopertype='02' and ifnull(s.auditstatus,'')<>'04' and  DATE_FORMAT(s.sdate,'%Y-%m')=?),0) curroutstocknum,   " +
                "                  ifnull((select sum( (select cg.cgprice from purchase cg where cg.id=s.cgid  )) /count(*) from stockinfo s where s.gid=g.id and ifnull(s.auditstatus,'')<>'04'and s.sopertype='01' ),0)  currinprice,   " +
                "                 (select sum(ck.cknum) from ckstockinfo ck where ck.gid=g.id) currcksnum  " +
                "                 from goodsinfo g where g.gtid in('01','02') )t)tt)t2)t3 where 1=1");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
        Date d=null;
        try {
            d=format.parse(params);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(d);
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String premon = format.format(m);
        return memberDao.queryMapsBypage(new Object[]{premon,params,params},1,1000,sql.toString());
    }

    @Override
    public PageBean fgInventory(int page,int pageSize,String params) {
        StringBuffer sql=new StringBuffer("select * from (select t.* ,(t.prestocknum+t.currinstocknum-t.curroutstocknum) currjcstocknum from ( " +
                "select g.id,g.gid,g.gname,g.unit,g.gtid,g.gspec, " +
                "(select p.proname from supplier p where p.id=g.providerid ) provider, " +
                " IFNULL((select sum(case when s.sopertype='01' then s.snum when s.sopertype='02' then -s.snum end) from stockinfo s where s.gid=g.id and  DATE_FORMAT(s.sdate,'%Y-%m')=?),0) prestocknum, " +
                "IFNULL((select sum(s.snum) from stockinfo s where s.gid=g.id and s.sopertype='02' and ifnull(s.auditstatus,'')<>'04' and DATE_FORMAT(s.sdate,'%Y-%m')=?),0) curroutstocknum, " +
                "IFNULL((select sum(s.snum) from stockinfo s where s.gid=g.id and s.sopertype='01'and ifnull(s.auditstatus,'')<>'04' and DATE_FORMAT(s.sdate,'%Y-%m')=?),0) currinstocknum, " +
                "(select sum(ck.cknum) from ckstockinfo ck where ck.gid=g.id) currcksnum " +
                " from goodsinfo g where g.gtid in('03') )t)t2 where 1=1 ");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
        Date d=null;
        try {
            d=format.parse(params);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(d);
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String premon = format.format(m);
        return memberDao.queryMapsBypage(new Object[]{
                premon,params,params
        },1,1000,sql.toString());
    }

    @Override
    public PageBean fgInStock(int page,int pageSize,String params) {
        StringBuffer sql=new StringBuffer("select * from (select g.id,g.gid,g.gname,g.unit,g.gtid,g.gspec,(select p.proname from supplier p where p.id=g.providerid) provider ," +
                "IFNULL((select sum(s.snum) from stockinfo s where s.gid=g.id and s.sopertype='01' and ifnull(s.auditstatus,'')<>'04' and  DATE_FORMAT(s.sdate,'%Y-%m')=?),0) instocknum  " +
                " from goodsinfo g where g.gtid in('03') )t where 1=1 ");

        return memberDao.queryMapsBypage(new Object[]{params},1,1000,sql.toString());
    }

    @Override
    public PageBean fgOutStock(int page,int pageSize,String params) {
        StringBuffer sql=new StringBuffer("select * from (select g.id,g.gid,g.gname,g.unit,g.gtid,g.gspec, (select p.proname from supplier p where p.id=g.providerid) provider," +
                "IFNULL((select sum(s.snum) from stockinfo s where s.gid=g.id and s.sopertype='02' and ifnull(s.auditstatus,'')<>'04' and  DATE_FORMAT(s.sdate,'%Y-%m')=?),0) outstocknum  " +
                " from goodsinfo g where g.gtid in('03') )t where 1=1");

        return memberDao.queryMapsBypage(new Object[]{params},1,1000,sql.toString());
    }

    @Override
    public PageBean materialSummary(int page,int pageSize,String params){
        StringBuffer sql=new StringBuffer("select * from (select t4.id,t4.gid,t4.gname,t4.unit,t4.gtid,t4.gspec,t4.provider,t4.yt,t4.fgspec,t4.fgprovider, t4.stocknum,round(ifnull(t4.curroutprice,0),2) price,round(ifnull((t4.stocknum*t4.curroutprice),0),2) money from (  " +
                " select t3.*, ifnull(((t3.premoney+t3.currinmoney)/(t3.prestocknum+t3.currinstocknum)),0)  curroutprice  from (  " +
                "                                  select t2.*,(t2.prestocknum*t2.preprice) premoney,(t2.currinstocknum*t2.currinprice) currinmoney from (  " +
                "select t.* from ( " +
                "select g.id,g.gid,g.gname,g.unit,g.gtid,g.gspec, s.sopertype,s.stype,s.stype1,  " +
                "(select p.proname from supplier p where p.id=g.providerid) provider, " +
                "(select t.gname from goodsinfo t where t.id=s.museto) yt,'瓦良格' fgprovider,  " +
                "(select t.gspec from goodsinfo t where t.id=s.museto) fgspec , " +
                "IFNULL((select sum(case when s.sopertype='01' then s.snum when s.sopertype='02' then -s.snum end) from stockinfo s where s.gid=g.id and  s.sopertype='02' and s.stype1='03' and DATE_FORMAT(s.sdate,'%Y-%m')=? ),0) prestocknum,  " +
                "                                  (select sum(case when s.sopertype='01' then (select cg.cgprice from purchase cg where cg.id=s.cgid  )  " +
                "                                                  when  s.sopertype='02' then (select xh.xhprice from xhcontract xh where xh.id=s.xhid ) end) /count(*) from stockinfo s where s.gid=g.id ) preprice,  " +
                "                                   IFNULL((select sum(s.snum) from stockinfo s where s.gid=g.id and s.sopertype='01' and  DATE_FORMAT(s.sdate,'%Y-%m')=?),0) currinstocknum,  " +
                "                                   IFNULL((select sum(s.snum) from stockinfo s where s.gid=g.id and s.sopertype='02' and s.stype1='03' and IFNULL(s.auditstatus,'')<>'04'  and  DATE_FORMAT(s.sdate,'%Y-%m')=?),0) stocknum,  " +
                "                                   ifnull((select sum( (select cg.cgprice from purchase cg where cg.id=s.cgid  )) /count(*) from stockinfo s where s.gid=g.id and s.sopertype='01' ),0)  currinprice " +
                "from stockinfo s left JOIN goodsinfo g on s.gid=g.id  and ifnull(s.stype1,'')='03' and IFNULL(s.auditstatus,'')<>'04' and g.gtid in('01','02') )t where 1=1 and IFNULL(t.id,'')<>'' group by t.yt,t.id  " +
                " )t2)t3 )t4)t5 where 1=1  ");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
        Date d=null;
        try {
            d=format.parse(params);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(d);
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String premon = format.format(m);
        return memberDao.queryMapsBypage(new Object[]{premon,params,params},page,pageSize,sql.toString());
    }


    @Override
    public List<Map<String,Object>> getDateList(String type){
        StringBuffer sql=null;
        if(type!=null){
            if(type.equals("1")){
                 sql=new StringBuffer("select * from (select DISTINCT DATE_FORMAT(s.datelist,'%Y-%m') datetime from calendar s)t");

            }else if(type.equals("2")){
                 sql=new StringBuffer("select * from (select DISTINCT DATE_FORMAT(s.datelist,'%Y') datetime from calendar s)t ");

            }
        }
         return memberDao.queryMapsBypage(null,1,10000,sql.toString()).getResult();
    }

}

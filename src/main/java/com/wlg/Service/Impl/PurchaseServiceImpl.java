package com.wlg.Service.Impl;

import com.wlg.Dao.BaseDao;
import com.wlg.Dao.MemberDao;
import com.wlg.Dao.PurchaseDao;
import com.wlg.Model.PageBean;
import com.wlg.Model.Purchase;
import com.wlg.Service.MemberService;
import com.wlg.Service.PurchaseService;
import com.wlg.Util.HiSession;
import com.wlg.Util.HqlParamUtil;
import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Resource
    private com.wlg.Dao.BaseDao baseDao;
    @Resource
    private PurchaseDao purchaseDao;

    @Resource
    private MemberService memberService;
    @Resource
    private MemberDao memberDao;

    @Resource
    HiSession hiSession;

    @Override
    @Transactional
    public int savePurchase(Purchase purchase) {
       // if(StringUtils.isEmpty(purchase.getCgid())) return 2;
       // Purchase u = this.baseDao.findUniqueByProperty(Purchase.class,"id",purchase.getId());

        this.purchaseDao.savePurchase(purchase);

        return 1;
    }

    @Override
    @Transactional
    public Purchase getPurByPurId(Map<String, Object> params) {
        return this.purchaseDao.getPurByPurId(params);

    }

    @Override
    public PageBean getPurListByPage(int pageno,int pageSize ,Map<String, Object> params ) {
        String hql="select * FROM purchase WHERE 1=1 ";
       // return this.purchaseDao.getPurListByPage(page,hql,params);
        return memberService.queryForPageByParams(pageSize,pageno,hql,params);
    }

    @Override
    public  PageBean getPurListByParams(int pageno,int pageSize,Map<String, Object> params) {
        Object[] condi=null;
        List<String> p=new ArrayList<>();
        StringBuffer sql=new StringBuffer("select * from (select tt.*,(tt.cgnum-tt.storagednum) storagenum,(case when (tt.cgnum-tt.storagednum)>0 then '01' when (tt.cgnum-tt.storagednum)<=0 then '02' end) instate from (select p.id cid, p.cgid cgid,g.id gid,g.gid gno,g.gname,g.unit, g.gspec,p.cgdate,p.cgnum , " +
                " (select  ifnull(sum(case when s.sopertype='01' then s.snum when s.sopertype='02' && s.auditstatus='00' then -s.snum end),0) from stockinfo s where s.cgid=p.id  ) storagednum, " +
                " (select su.proname from supplier su where 1=1 and su.id=g.providerid) proname ,p.cgprice,p.cgmoney,ifnull(g.faultnum,0) faultnum, " +
                " ifnull((select sum(s.snum) from stockinfo s where s.cgid=p.id and s.auditstatus in('01','02','05') and s.sopertype='02' and s.stype1='02' and s.sthstate='00' ),0) afaultnum, "+
                " ifnull((select sum(s.snum) from stockinfo s where s.cgid=p.id and s.auditstatus in('01','02','05') and s.sopertype='02' ),0) anum ,p.comments"+
                " from purchase p,goodsinfo g where 1=1 and p.gid=g.id )tt )t2 where 1=1 ");
        if(params.size()>0){
            condi=new Object[params.get("findkey")!=null && params.get("findkey")!=""?params.size()+1:params.size()];
            for (String key:params.keySet()) {
                Object paramValue = params.get(key);
                if (paramValue == null || paramValue.equals("")) {
                    continue;
                }
                if (key.equals("findkey")) {
                    sql.append(" and (t2.cgid like ? or t2.gname like ? )");
                    p.add(paramValue+"%");
                    p.add(paramValue+"%");
                } else if (key.equals("instate")) {
                    sql.append(" and t2.instate=? ");
                    p.add(""+paramValue);
                }
            }
            for(int i=0;i<condi.length;i++){
                condi[i]=p.get(i);
            }
            }

       // return this.memberService.queryBySQLForPage(pageSize,pageno,sql.toString(),params);
          return  this.memberDao.queryMapsBypage(condi,pageno,pageSize,sql.toString());
    }

    @Override
    public  PageBean getPurDetailByPurId(int pageno,int pageSize,String cgid) {
        StringBuffer sql=new StringBuffer("select t2.id, t2.sid,DATE_FORMAT(t2.sdate,'%Y-%m-%d %H:%i:%s') sdate,t2.sopertype,t2.stype,t2.stype1,t2.sperson,t2.snum,ifnull(t2.auditstatus,'') auditstatus" +
                "  from purchase t,stockinfo t2  where 1=1  and t.id=t2.cgid and ifnull(t2.auditstatus,'')<>'04' " +
                "and t.id=?  order by t2.sdate desc ");

        return  this.memberDao.queryMapsBypage(new Object[]{cgid},pageno,pageSize,sql.toString());
    }

    @Override
    @Transactional
    public int delPurchase(Purchase purchase) {
        purchaseDao.delPurchase(purchase);
        return 1;
    }

    @Override
    public String getNextPurId(){
        String maxid="";
        StringBuffer sql=new StringBuffer("select distinct max( CONVERT(SUBSTR(t.cgid,11),SIGNED)) maxid from  purchase t");
        SimpleDateFormat f=new SimpleDateFormat("yyyyMMdd");
        Date curr=new Date();
        f.format(curr);
        List<Map<String,Object>> t= memberDao.queryBySqlForPage(sql.toString()).getResult();
        if(t.size()>0){
            maxid=t.get(0).get("maxid")==null?"0":t.get(0).get("maxid").toString();
        }
        int nextid=Integer.parseInt((maxid==null || maxid.equals(""))?"0":maxid)+1;
        //String restr=String.format("%04d",Integer.parseInt(s!=null&& !s.equals("")?s:"0")+1);
        return "CG"+ f.format(curr)+String.format("%04d",nextid);
    }


    @Override
    @Transactional
    public int updatePur(Purchase purchase) {
        return purchaseDao.updatePur(purchase);
    }




}

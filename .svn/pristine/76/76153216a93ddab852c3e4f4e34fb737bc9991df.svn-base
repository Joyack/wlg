package com.wlg.Service.Impl;

import com.wlg.Dao.MemberDao;
import com.wlg.Dao.XhContractDao;
import com.wlg.Model.PageBean;
import com.wlg.Model.Purchase;
import com.wlg.Model.StockInfo;
import com.wlg.Model.XhContract;
import com.wlg.Service.XhContractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/29 0029.
 */
@Service
public class XhContractServiceImpl implements XhContractService {
    @Resource
    private XhContractDao xhContractDao;

    @Resource
    private MemberDao memberDao;

    @Override
    @Transactional
    public int saveXhContract(XhContract xhContract) {
        this.xhContractDao.saveXhContract(xhContract);
        return 1;
    }


    @Override
    public PageBean getXhContractListByParams(int pageNo, int pageSize, Map<String, Object> params) {
        Object[] condi=null;
        List<String> p=new ArrayList<>();
        StringBuffer sql=new StringBuffer("select * from (select tt.*,(tt.xhnum-tt.outednum) outnum, (case when tt.xhnum=tt.outednum  then  '02' when tt.xhnum > tt.outednum then  '01'  end) outstate " +
                "                       from (select p.id id, p.xhid xhid,g.id gid,g.gid gno,g.gname,g.unit, g.gspec,p.xhdate,p.xhnum ,   " +
                "                       (select  ifnull(sum(case when s.sopertype='02' && s.auditstatus='00' then s.snum  when sopertype='01' && s.auditstatus='00' then -s.snum  end),0) from stockinfo s where s.xhid=p.id  ) outednum,g.gtid,  " +
                "                        (select su.proname from supplier su where 1=1 and su.id=g.providerid) proname ,p.xhprice,p.xhmoney ,p.customer,g.storagenum,(ifnull(g.storagenum,0)-ifnull(g.faultnum,0)) nnum, " +
                "                         ifnull((select sum(s.snum) from stockinfo s where s.gid=g.id and s.auditstatus in('01','02','05') ),0)  anum, " +
                "                         ifnull((select sum(s.snum) from stockinfo s where s.gid=g.id and s.auditstatus in('01','02','05') and s.sopertype='02' and s.stype1='01' ),0)  aoutnum " +
                "                        from xhcontract p,goodsinfo g where 1=1 and p.gid=g.id )tt ) t2 where 1=1 ");


        if(params.size()>0){
            condi=new Object[params.get("findkey")!=null && params.get("findkey")!=""?params.size()+1:params.size()];
            for (String key:params.keySet()) {
                Object paramValue = params.get(key);
                if (paramValue == null || paramValue.equals("")) {
                    continue;
                }
                if (key.equals("findkey")) {
                    sql.append(" and (t2.xhid like ? or t2.gname like ? )");
                    p.add("%"+paramValue+"%");
                    p.add("%"+paramValue+"%");
                } else if (key.equals("outstate")) {
                    sql.append(" and t2.outstate=? ");
                    p.add(""+paramValue);
                }
            }
            for(int i=0;i<condi.length;i++){
                condi[i]=p.get(i);
            }
        }
        return  this.memberDao.queryMapsBypage(condi,pageNo,pageSize,sql.toString());
    }

    @Override
    public String getNextXhId() {
        String maxid="";
        StringBuffer sql=new StringBuffer("select distinct max( CONVERT(SUBSTR(t.xhid,11),SIGNED)) maxid from  xhcontract t");
        SimpleDateFormat f=new SimpleDateFormat("yyyyMMdd");
        Date curr=new Date();
        f.format(curr);
        List<Map<String,Object>> t= memberDao.queryBySqlForPage(sql.toString()).getResult();
        if(t.size()>0){
            maxid=t.get(0).get("maxid")==null?"0":t.get(0).get("maxid").toString();
        }
        int nextid=Integer.parseInt((maxid==null || maxid.equals(""))?"0":maxid)+1;
        //String restr=String.format("%04d",Integer.parseInt(s!=null&& !s.equals("")?s:"0")+1);
        return "XH"+ f.format(curr)+String.format("%04d",nextid);
    }

    @Override
    @Transactional
    public int delXhContract(XhContract xhContract) {
        this.xhContractDao.delXhContract(xhContract);
        return 1;
    }

    @Override
    @Transactional
    public int updateXhContract(XhContract xhContract) {
        return 0;
    }

    @Override
    public PageBean getXhContractDetailById(int pageno, int pageSize, String xhid) {
        StringBuffer sql=new StringBuffer("select t2.id, t2.sid,DATE_FORMAT(t2.sdate,'%Y-%m-%d %H:%i:%s') sdate,t2.sopertype,t2.sperson,t2.snum,ifnull(t2.auditstatus,'')auditstatus " +
                " from xhcontract t,stockinfo t2  where 1=1  and t.id=t2.xhid  and ifnull(t2.auditstatus,'')<>'04' " +
                "                 and t.id=? order by t2.sdate desc ");

        return  this.memberDao.queryMapsBypage(new Object[]{xhid},pageno,pageSize,sql.toString());

    }

    @Override
    @Transactional
    public XhContract getXhContractById(String id) {
        return xhContractDao.getXhContractById(id);
    }

}

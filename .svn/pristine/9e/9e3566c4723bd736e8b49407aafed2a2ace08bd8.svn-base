package com.wlg.Service.Impl;

import com.wlg.Dao.GoodsDao;
import com.wlg.Dao.Impl.hibernate.BaseDao;
import com.wlg.Dao.MemberDao;
import com.wlg.Model.GoodsInfo;
import com.wlg.Model.PageBean;
import com.wlg.Model.User;
import com.wlg.Service.GoodsService;
import com.wlg.Util.HqlParamUtil;
import com.wlg.Util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
@Service
public class GoodsServiceImpl implements GoodsService {
   @Resource
   private MemberDao memberDao;

   @Resource
   private GoodsDao goodsDao;

    @Resource
    private com.wlg.Dao.BaseDao baseDao;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public PageBean<Map<String, Object>> queryGoodsBySql(int page, int pageSize, Map<String, Object> params) {
        Object[] condi=null;
        List<String> p=new ArrayList<>();
        StringBuffer sql=new StringBuffer("select * from (select g.*,(select p.proname from supplier p where p.id=g.providerid) providername, " +
                "(select count(1) from stockinfo s where s.gid=g.id and ifnull(s.auditstatus,'')<>'04' ) gsnum "+
                "from goodsinfo g ) t where 1=1 ");
        if(params.size()>0){
            condi=new Object[params.size()];
            for(String key:params.keySet()){
                Object paramValue=params.get(key);
                if(paramValue==null && "".equals(paramValue)){
                    continue;
                }
                if(key.equals("findkey")){
                    sql.append(" and t.gname like ? ");
                    p.add(paramValue+"%");
                }
                else if(key.equals("gtid")){
                    sql.append(" and t.gtid=? ");
                    p.add(""+paramValue);
                }else if(key.equals("gid")){
                    sql.append(" and t.id=?");
                    p.add(""+paramValue);
                }else if(key.equals("providerid")){
                    sql.append(" and t.providerid=?");
                    p.add(""+paramValue);
                }else if(key.equals("gid")){
                    sql.append(" and t.gid=?");
                    p.add(""+paramValue);
                }
            }
            for(int i=0;i<condi.length;i++){
                condi[i]=p.get(i);
            }
        }

        return memberDao.queryMapsBypage(condi,page,pageSize,sql.toString());
      //  return memberDao.queryForPageByParams(pageSize,page,sql.toString());
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public GoodsInfo queryGoodsById(String id) {
        StringBuffer sql=new StringBuffer();
        GoodsInfo g=this.baseDao.findUniqueByProperty(GoodsInfo.class,"id",id);

        return g;
    }

    @Override
    public PageBean queryGoodsByName(String gname) {
        return goodsDao.getGoodsByName(gname);
    }

    @Override
    @Transactional
    public int deleteGoods(GoodsInfo goodsInfo) {
    this.goodsDao.deleteGoods(goodsInfo);
    return 1;
    }

    @Override
    @Transactional
    public int save(GoodsInfo goodsInfo) {
        if(StringUtils.isEmpty(goodsInfo.getGname())) return 2;
        GoodsInfo u = this.baseDao.findUniqueByProperty(GoodsInfo.class,"gname",goodsInfo.getGname());

        this.goodsDao.saveGoods(goodsInfo);

        return 1;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int updateGoods(GoodsInfo goodsInfo) {
        return goodsDao.updateGoods(goodsInfo);
    }

    @Override
    public PageBean getGoodsByName(String gname) {
        return goodsDao.getGoodsByName(gname);
    }

    @Override
    public String getNextGoodsId() {
        String maxid="";
        StringBuffer sql=new StringBuffer("select DISTINCT max(SUBSTR(t.gid , 3 )) maxid from goodsinfo t ");
        List<Map<String,Object>> t=memberDao.queryBySqlForPage(sql.toString()).getResult();
        if(t.size()>0){
            maxid=t.get(0).get("maxid")==null?"0":t.get(0).get("maxid").toString();
        }
        int nextid=Integer.parseInt((maxid==null || maxid.equals(""))?"0":maxid)+1;
        return "wp"+String.format("%04d",nextid);
    }

    @Override
    public List<Map<String, Object>> getAllGoodsName() {
        StringBuffer sql=new StringBuffer("select * from (select g.*,(select p.proname from supplier p where p.id=g.providerid) providername " +
                "from goodsinfo g ) t where 1=1 ");
        return memberDao.queryForPageByParams(10000,1,sql.toString()).getResult();
    }

    @Override
    public List<Map<String,Object>> isExistsGoods(Map<String,Object> params){
        StringBuffer sql=new StringBuffer("select * from goodsinfo g where 1=1 ");
        if(params.size()>0){
            for(String key:params.keySet()){
                Object paramValue=params.get(key);
                if(paramValue==null || "".equals(paramValue)){
                    continue;
                }
                if(key.equals("gname")){
                    sql.append(" and g.gname='"+paramValue+"'");
                }else if(key.equals("gspec")){
                    sql.append(" and g.gspec='"+paramValue+"'");
                }else if(key.equals("providerid")){
                    sql.append(" and g.providerid='"+paramValue+"'");
                }
            }
        }
        List<Map<String,Object>> list=memberDao.queryBySqlForPage(sql.toString()).getResult();
        return list;
    }
}

package com.wlg.Dao.Impl;

import com.wlg.Dao.GoodsDao;
import com.wlg.Dao.Impl.hibernate.BaseDao;
import com.wlg.Dao.MemberDao;
import com.wlg.Model.GoodsInfo;
import com.wlg.Model.PageBean;
import com.wlg.Util.HiSession;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.management.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/2 0002.
 */
@Repository
public class GoodsDaoImpl extends BaseDao implements GoodsDao {
    @Resource
    private HiSession hiSession;
    @Resource
    private MemberDao memberDao;




    @Override
    public List<String> queryMapsBypage(String GoodTypeId) {
        return null;
    }

    @Override
    public GoodsInfo queryGoodsById(String gid) {
      return null;

    }



    @Override
    public int deleteGoods(GoodsInfo goodsInfo) {
        this.hiSession.getSession().delete(goodsInfo);

        return 1;
    }

    @Override
    public <T>Serializable saveGoods(GoodsInfo goodsInfo) {
        try {
            Serializable id = this.hiSession.getSession().save(goodsInfo);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("保存实体成功," + goodsInfo.getClass().getName());
            }
            return id;
        } catch (RuntimeException e) {
            logger.error("保存实体异常", e);
            throw e;
        }

    }

    @Override
    public int updateGoods(GoodsInfo goodsInfo){
        this.hiSession.getSession().update(goodsInfo);
         return 1;
    }

    @Override
    public PageBean getGoodsByName(String gname) {
        StringBuffer sql=new StringBuffer();
        sql.append("SELECT t.id, t.gid, t.gname,t.gtid,t.gspec,t.provider,t.unit " +
                "from goodsinfo t where t.gname='"+gname+"'");

        PageBean pageBean=memberDao.queryBySqlForPage(sql.toString());
        return pageBean;
    }


}

package com.wlg.Dao.Impl;

import com.wlg.Dao.BaseDao;
import com.wlg.Dao.StockDao;
import com.wlg.Model.FaultInfo;
import com.wlg.Model.StockInfo;
import com.wlg.Util.HiSession;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/15 0015.
 */
@Repository
public class StockDaoImpl extends com.wlg.Dao.Impl.hibernate.BaseDao implements StockDao {
    @Resource
    private BaseDao baseDao;
    @Resource
    private HiSession hiSession;
    @Override
    public <T> Serializable addStock(StockInfo stockInfo) {
        return baseDao.save(stockInfo);
    }

    @Override
    public int updateStock(StockInfo stockInfo) {
       hiSession.getSession().update(stockInfo);
       return 1;
    }

    @Override
    public int delStock(StockInfo stockInfo) {
        hiSession.getSession().delete(stockInfo);
        return 1;
    }

    @Override
    public int addFaultInfo(FaultInfo faultInfo) {
       hiSession.getSession().save(faultInfo);
       return 1;
    }

    @Override
    public StockInfo getStockById(String id) {
        return hiSession.getSession().get(StockInfo.class,id);
    }

}

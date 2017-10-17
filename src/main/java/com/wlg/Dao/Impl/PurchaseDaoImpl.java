package com.wlg.Dao.Impl;

import com.wlg.Dao.Impl.hibernate.BaseDao;
import com.wlg.Dao.PurchaseDao;
import com.wlg.Model.GoodsInfo;
import com.wlg.Model.PageBean;
import com.wlg.Model.Purchase;
import com.wlg.Model.Supplier;
import com.wlg.Util.HiSession;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

@Repository
public class PurchaseDaoImpl extends BaseDao implements PurchaseDao {


    @Resource
    private HiSession hiSession;

    @Autowired
    public void setSessionFacotry(SessionFactory sessionFacotry) {
        super.setSessionFactory(sessionFacotry);
    }
    @Override
    public <T>Serializable savePurchase(Purchase purchase) {
        try {
            Serializable id = this.hiSession.getSession().save(purchase);
            this.hiSession.getSession().flush();
            if (logger.isDebugEnabled()) {
                logger.debug("保存实体成功," + purchase.getClass().getName());
            }
            return id;
        } catch (RuntimeException e) {
            logger.error("保存实体异常", e);
            throw e;
        }
    }
/*编辑合同*/


    @Override
    public Purchase getPurByPurId(Map<String,Object> params) {
        Map<String,Object> param=new HashMap<String,Object>();
       StringBuffer sql=new StringBuffer("from Purchase p where 1=1 ");
        if (params != null) {
            for (String key : params.keySet()) {
                if (params.get(key) == null) {
                    continue;
                }
                String paramValue = params.get(key).toString();
                if (key.equals("purId") && !paramValue.equals("")) {
                    sql.append("and p.id=? ");
                    param.put("purId", paramValue);
                }

            }
        }
        List<Purchase> plist= super.find(sql.toString(),params);
         return plist.get(0);
    }

    @Override
    public PageBean<Purchase> getPurListByPage(PageBean pageBean, String hql, Map<String, Object> params) {
        return super.findPageBean(pageBean,hql,params);
    }

    @Override
    public List<Purchase> getPurListByParams(Map<String, Object> params) {
        Map<String,Object> param=new HashMap<>();
        StringBuffer sql=new StringBuffer("select * from purchase p where 1=1 ");
        if (params != null) {
            for (String key : params.keySet()) {
                if (params.get(key) == null) {
                    continue;
                }
                String paramValue = params.get(key).toString();
                if (key.equals("purId") && !paramValue.equals("")) {
                    sql.append("and p.id=? ");
                    param.put("purId", paramValue);
                }
            }
        }
        List<Purchase> plist= super.find(sql.toString(),params);
        return plist;
    }

    @Override
    public int delPurchase(Purchase purchase) {

        hiSession.getSession().delete(purchase);
        return 1;
    }


    /*编辑修改*/
    @Override
    @Transactional
    public int updatePur(Purchase purchase)
    {
       hiSession.getSession().update(purchase);
        return 1;
    }

    @Override
    public int getAllRowCount(String hql){
        return this.getSession().createQuery(hql).list().size();
    }
}

package com.wlg.Dao.Impl;

import com.wlg.Dao.XhContractDao;
import com.wlg.Model.Purchase;
import com.wlg.Model.StockInfo;
import com.wlg.Model.XhContract;
import com.wlg.Util.HiSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/29 0029.
 */
@Repository
public class XhContractDaoImpl implements XhContractDao {
    @Resource
    private HiSession hiSession;

    @Override
    public <T> Serializable saveXhContract(XhContract xhContract) {
        Serializable id=hiSession.getSession().save(xhContract);
        return id;
    }

    @Override
    public XhContract getXhContractById(String id) {
        return hiSession.getSession().get(XhContract.class,id);
    }

    @Override
    public List<XhContract> getXhContractListByParams(Map<String, Object> params) {
        return null;
    }

    @Override
    public int delXhContract(XhContract xhContract) {
        hiSession.getSession().delete(xhContract);
        return 1;
    }

    @Override
    public int updateXhContract(XhContract purchase) {
        hiSession.getSession().update(purchase);
        return 1;
    }

    @Override
    public int getAllRowCount(String hql) {
        return 0;
    }
}

package com.wlg.Dao;

import com.wlg.Model.PageBean;
import com.wlg.Model.Purchase;
import com.wlg.Model.XhContract;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
@Service
public interface XhContractDao {
    public <T>Serializable saveXhContract(XhContract purchase);
    public XhContract getXhContractById(String id);
    public List<XhContract> getXhContractListByParams(Map<String, Object> params);

    public int delXhContract(XhContract xhContract);
    public int updateXhContract(XhContract purchase);

    public int getAllRowCount(String hql);
}

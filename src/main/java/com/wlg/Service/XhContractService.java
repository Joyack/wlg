package com.wlg.Service;

import com.wlg.Model.PageBean;
import com.wlg.Model.Purchase;
import com.wlg.Model.XhContract;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
public interface XhContractService {
    public int saveXhContract(XhContract purchase);
    public XhContract getXhContractById(String id);
    public PageBean getXhContractListByParams(int pageNo, int pageSize, Map<String, Object> params);
    public String getNextXhId();
    public int delXhContract(XhContract purchase);
    public int updateXhContract(XhContract purchase);
    public PageBean getXhContractDetailById(int pageno, int pageSize, String xhid);


}

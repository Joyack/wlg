package com.wlg.Dao.Impl;

import com.wlg.Dao.CheckDao;
import com.wlg.Model.CkStockInfo;
import com.wlg.Model.FaultInfo;
import com.wlg.Model.StockInfo;
import com.wlg.Util.HiSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/7/5 0005.
 */
@Repository
public class CheckDaoImpl implements CheckDao {

    @Resource
    private HiSession hiSession;
    @Override
    public int addCkImportData(List<CkStockInfo> ckStockInfoList) {
        int n=0;
        if(ckStockInfoList!=null&&ckStockInfoList.size()>0){
            for(CkStockInfo ckStockInfo : ckStockInfoList){
                CkStockInfo c = new CkStockInfo();
               hiSession.getSession().save(ckStockInfo);
            }
            }
        return 1;
    }
}

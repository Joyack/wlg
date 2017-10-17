package com.wlg.Service.Impl;

import com.wlg.Dao.Impl.hibernate.BaseDao;
import com.wlg.Dao.MemberDao;
import com.wlg.Dao.SupplierDao;
import com.wlg.Model.PageBean;
import com.wlg.Model.Supplier;
import com.wlg.Service.SupplierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
@Service
public class SupplierServiceImpl implements SupplierService {
    @Resource
    private MemberDao memberDao;
    @Resource
    private SupplierDao supplierDao;
    @Resource
    private com.wlg.Dao.BaseDao baseDao;

    @Override
    @Transactional
    public int addSupplier(Supplier supplier) {
        supplierDao.saveSupplier(supplier);
        return 1;
    }

    @Override
    @Transactional
    public int delSupplier(Supplier supplier) {
        supplierDao.deleteSupplier(supplier);
        return 1;
    }

    @Override
    @Transactional
    public int updateSupplier(Supplier supplier) {
        supplierDao.updateSupplier(supplier);
        return 1;
    }

    @Override
    public PageBean querySupplierList(int pageno, int pageSize, Map<String, Object> params) {
        Object[] condi=null;
        List<String> p=new ArrayList<>();
        StringBuffer sql=new StringBuffer("select * from (" +
                " select t.* ,group_concat(t2.gname separator '，') prodname FROM supplier t LEFT JOIN goodsinfo t2 on  (t.id=t2.providerid) " +
                " GROUP BY t.id, t.proname ) tt where 1=1 ");

         if(params.size()>0){
            condi=new Object[params.get("findkey")!=null && params.get("findkey")!=""?params.size()+2:params.size()];
            for(String key:params.keySet()){
                Object paramValue=params.get(key);
                if(paramValue==null && "".equals(paramValue)){
                    continue;
                }
                if(key.equals("findkey")){
                    sql.append(" and (tt.proname like ? or tt.productname like ? or tt.industry like ? ) ");
                    p.add(paramValue+"%");
                    p.add(paramValue+"%");
                    p.add(paramValue+"%");

                }
            }
            for(int i=0;i<condi.length;i++){
                condi[i]=p.get(i);
            }
        }
        return memberDao.queryMapsBypage(condi,pageno,pageSize,sql.toString());
    }

    @Override
    public Supplier querySupplierById(String providerid) {
        return null;
    }

    @Override
    public List<Map<String, Object>> querySupplierByName(String proname) {
        StringBuffer sql=new StringBuffer("select * from supplier t where t.proname='"+proname+"'");
        PageBean p= memberDao.queryBySqlForPage(sql.toString());
        if(p.getResult().size()>0){
            return p.getResult();
        }
        return null;
    }
}

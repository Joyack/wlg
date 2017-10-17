package com.wlg.Service.Impl;

import com.wlg.Dao.BaseDao;
import com.wlg.Dao.DeptDao;
import com.wlg.Dao.MemberDao;
import com.wlg.Model.Deptinfo;
import com.wlg.Model.PageBean;
import com.wlg.Service.DeptService;
import com.wlg.Util.HqlParamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Resource
    private MemberDao memberDao;

    @Resource
    private DeptDao deptDao;

    @Resource
    private BaseDao baseDao;
    @Override
    @Transactional
    public int addDept(Deptinfo deptinfo) {

        deptDao.addDept(deptinfo);
        return 1;
    }

    @Override
    @Transactional
    public int delDept(Deptinfo deptinfo) {
        deptDao.delDept(deptinfo);
        return 1;
    }

    @Override
    @Transactional
    public int updateDept(Deptinfo deptinfo) {
        deptDao.updateDept(deptinfo);
        return 1;
    }

    @Override
    public PageBean getDeptList(int page, int pageSize, Map<String, Object> map) {
        Object[] condi=null;
        List<String> p=new ArrayList<>();
        StringBuffer sql=new StringBuffer("select d.* from deptinfo d where 1=1 ");
        String findkey="";
        if(map.size()>0){
            condi=new Object[map.get("findkey")!=null && map.get("findkey")!=""?map.size()+2:map.size()];

            for(String key:map.keySet()){

                String paramvalue=map.get(key).toString();
                if(paramvalue==null){
                    continue;
                }
                if(key.equals("deptname")){
                    sql.append(" and d.deptname=? ");
                    findkey=paramvalue;
                    p.add(""+paramvalue);
                }
            }
            for(int i=0;i<condi.length;i++){
                condi[i]=p.get(i);
            }
        }sql.append(" order by d.deptid desc ");
        return memberDao.queryMapsBypage(condi,page,pageSize,sql.toString());
    }

    @Override
    @Transactional
    public List<Deptinfo> getDeptList(Deptinfo deptinfo) {
        return this.baseDao.findByQueryString(" FROM Deptinfo WHERE 1=1 "+ HqlParamUtil.getFieldValue(deptinfo));
    }


    @Override
    public String getNextDeptId() {
        String maxid="";
        StringBuffer sql=new StringBuffer("select DISTINCT max(SUBSTR(t.deptid , 3 )) maxid from deptinfo t ");
        List<Map<String,Object>> t=memberDao.queryBySqlForPage(sql.toString()).getResult();
        if(t.size()>0){
            maxid=t.get(0).get("maxid")==null?"0":t.get(0).get("maxid").toString();
        }
        int nextid=Integer.parseInt((maxid==null || maxid.equals(""))?"0":maxid)+1;
        return "bm"+String.format("%04d",nextid);
    }
}

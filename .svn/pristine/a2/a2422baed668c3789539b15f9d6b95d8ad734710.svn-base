package com.wlg.Service.Impl;

import com.wlg.Dao.BaseDao;
import com.wlg.Dao.MemberDao;
import com.wlg.Dao.RoleDao;
import com.wlg.Model.PageBean;
import java.util.List;
import java.util.Map;

import com.wlg.Model.ResourcesRole;
import com.wlg.Util.Contant;
import com.wlg.Util.HqlParamUtil;
import com.wlg.Model.Role;
import com.wlg.Service.MemberService;
import com.wlg.Service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Service
public class RoleServiceImpl implements RoleService{
    @Resource
    private RoleDao roleDao;
    @Resource
    private BaseDao baseDao;
    @Resource
    private MemberService memberService;
    @Resource
    private MemberDao memberDao;

    /**
    * 初始化Log4j的一个实例
    */
    private static final Logger logger = Logger
        .getLogger(RoleServiceImpl.class);

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int saveRole(Role role) {
        this.roleDao.saveRole(role);
        return 1;

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int updateRole(Role role) {
        this.roleDao.updateRole(role);
        return 1;

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int deleteRole(Role role) {
        Role r = this.baseDao.findUniqueByID(Role.class,role.getId());
        if(r==null)return 2;
        this.roleDao.deleteRole(r);
        return 1;

    }

    @Override
    public PageBean sreachRoleForPage(Integer page, Role role, Map<String,Object> map) {
        if(page==null)page=1;
        StringBuffer sql=new StringBuffer("select * from (select * from role r where 1=1");
        if(map!=null && map.size()>0){
            for (String key:map.keySet()){
                Object o=map.get(key);
                if(o==null && o.equals("")){
                    continue;
                }
                if(key.equals("findkey")){
                    sql.append(" and r.name='"+o+"' or r.rname like '"+o+"%'");
                }
            }
        }sql.append(") t order by t.rid asc");
       // return memberService.queryForPage(10,page,"FROM Role WHERE 1=1",role,role.getRid());
        return  memberDao.queryMapsBypage(null,page,10,sql
        .toString());
    }


    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Role> getRoleList(Role role) {
        return this.baseDao.findByQueryString("FROM Role WHERE 1=1 "+ HqlParamUtil.getFieldValue(role));
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public Role RoleByUserName(String userName) {
        if(StringUtils.isEmpty(userName)) return null;
        String roleName = Contant.getRoleTempName(userName);
        Role role = this.roleDao.getRoleByName(roleName);
        if(role==null){
            role = new Role();
            role.setRname(roleName);
            role.setRname(roleName);
            try{
                role.setId(this.roleDao.saveRole(role).toString());
                return role;
            }catch (RuntimeException e){
                return null;
            }
        }else{
            return role;
        }
    }

    @Override
    public void saveRoleResources(List<ResourcesRole> res){

    }

    @Override
    public List<Role> searchRolesByResourceId(String roleid) {
        return null;
    }

}


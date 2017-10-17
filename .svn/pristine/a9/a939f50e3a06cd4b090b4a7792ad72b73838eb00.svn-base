package com.wlg.Service.Impl;

import com.wlg.Dao.BaseDao;
import com.wlg.Dao.PermissionRoleDao;
import com.wlg.Model.PageBean;

import java.util.ArrayList;
import java.util.List;

import com.wlg.Model.Permission;
import com.wlg.Model.Role;
import com.wlg.Util.HqlParamUtil;
import com.wlg.Model.PermissionRole;
import com.wlg.Service.MemberService;
import com.wlg.Service.PermissionRoleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Service
public class PermissionRoleServiceImpl implements PermissionRoleService{
    @Resource
    private PermissionRoleDao permissionRoleDao;
    @Resource
    private BaseDao baseDao;
    @Resource
    private MemberService memberService;

    /**
    * 初始化Log4j的一个实例
    */
    private static final Logger logger = Logger
        .getLogger(PermissionRoleServiceImpl.class);

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int savePermissionRole(PermissionRole permissionRole) {
        this.permissionRoleDao.savePermissionRole(permissionRole);
        return 1;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int updatePermissionRole(PermissionRole permissionRole) {
        this.permissionRoleDao.updatePermissionRole(permissionRole);
        return 1;

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int deletePermissionRole(PermissionRole permissionRole) {
         this.permissionRoleDao.deletePermissionRole(permissionRole);
        return 1;

    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public PageBean sreachPermissionRoleForPage(Integer pageno, PermissionRole permissionRole) {
        if(pageno==null)pageno=1;
        return memberService.queryForPage(10,pageno,"FROM PermissionRole WHERE 1=1",permissionRole,null);
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public List<PermissionRole> getPermissionRoleList(PermissionRole permissionRole) {
        return this.baseDao.findByQueryString("FROM PermissionRole WHERE 1=1 "+ HqlParamUtil.getFieldValue(permissionRole));
    }


    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public String getFilterChainDefinitionsForPermission() {
        StringBuffer sb = new StringBuffer("");
        List<Permission> ps = this.baseDao.loadAll(Permission.class);
        if(ps!=null&&ps.size()>0){
            List<PermissionRole> prs = null;
            boolean flag = false;
            for(Permission permission : ps){
                sb.append("/").append(permission.getUrl()).append("*").append(" = authc,permsOrFilter[");
                prs = new ArrayList();
                prs = this.baseDao.findByProperty(PermissionRole.class,"permid",permission.getId());
                if(prs.size()>0){
                    Role role;
                    for(int i=0;i<prs.size();i++){
                        role = null;
                        role = this.baseDao.findUniqueByProperty(Role.class,"id",prs.get(i).getRoleid());
                        if(role!=null){
                            if(!flag)
                                flag = true;
                            if(i==prs.size()-1){
                                sb.append(role.getRname());
                            }else{
                                sb.append(role.getRname());
                                sb.append(",");
                            }
                        }
                    }
                }else{
                    sb.append("no_one");
                }

                if(!flag){
                    //如果权限与角色关系存在，但角色无记录，属于数据错误，但权限需要作出屏蔽
                    sb.append("no_one");
                }

                sb.append("]\n");
            }
        }
        return sb.toString();
    }
}


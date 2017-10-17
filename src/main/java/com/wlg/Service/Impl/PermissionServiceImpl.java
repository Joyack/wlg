package com.wlg.Service.Impl;

import com.wlg.Dao.BaseDao;
import com.wlg.Dao.PermissionDao;
import com.wlg.Model.PageBean;
import java.util.List;
import com.wlg.Util.HqlParamUtil;
import com.wlg.Model.Permission;
import com.wlg.Service.MemberService;
import com.wlg.Service.PermissionService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Service
public class PermissionServiceImpl implements PermissionService{
    @Resource
    private PermissionDao permissionDao;
    @Resource
    private BaseDao baseDao;
    @Resource
    private MemberService memberService;

    /**
    * 初始化Log4j的一个实例
    */
    private static final Logger logger = Logger
        .getLogger(PermissionServiceImpl.class);

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int savePermission(Permission permission) {
        this.permissionDao.savePermission(permission);
        return 1;

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int add_DefultPermission(String Prefix) {
        String audc[] = {"add","update","delete","check"};
        Permission permission;
        for(String param : audc){
            permission = new Permission();
            permission.setUrl(Prefix+"/"+param+"_test");
            this.permissionDao.savePermission(permission);
        }
        return 1;

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int updatePermission(Permission permission) {
        this.permissionDao.updatePermission(permission);
        return 1;

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int deletePermission(Permission permission) {
        Permission p  = this.baseDao.findUniqueByProperty(Permission.class,"id",permission.getId());
        if(p==null)return 2;
        this.permissionDao.deletePermission(p);
        return 1;
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Permission> getPermissionList(Permission permission) {
        return this.baseDao.findByQueryString("FROM Permission WHERE 1=1 "+ HqlParamUtil.getFieldValue(permission));
    }

    @Override
    public PageBean sreachPermissionForPage(Integer pageno, Permission permission) {
        if(pageno==null)pageno=1;
        return memberService.queryForPage(10,pageno,"FROM Permission WHERE 1=1",permission,null);
    }
}


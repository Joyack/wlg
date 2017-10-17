package com.wlg.Service.Impl;

import com.wlg.Dao.BaseDao;
import com.wlg.Dao.RoleDao;
import com.wlg.Dao.UserRoleDao;
import com.wlg.Model.PageBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wlg.Model.Role;
import com.wlg.Model.User;
import com.wlg.Service.UserService;
import com.wlg.Util.HqlParamUtil;
import com.wlg.Model.UserRole;
import com.wlg.Service.MemberService;
import com.wlg.Service.UserRoleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Service
public class UserRoleServiceImpl implements UserRoleService{
    @Resource
    private UserRoleDao userRoleDao;
    @Resource
    private BaseDao baseDao;
    @Resource
    private MemberService memberService;
    @Resource
    private RoleDao roleDao;
    @Resource
    private UserService userService;

    /**
    * 初始化Log4j的一个实例
    */
    private static final Logger logger = Logger
        .getLogger(UserRoleServiceImpl.class);

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int saveUserRole(UserRole userRole) {
        this.userRoleDao.saveUserRole(userRole);
        return 1;

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int updateUserRole(UserRole userRole) {
        this.userRoleDao.updateUserRole(userRole);
        return 1;

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int deleteUserRole(UserRole userRole) {
        this.userRoleDao.deleteUserRole(userRole);
        return 1;

    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public PageBean sreachUserRoleForPage(Integer page, UserRole userRole) {
        if(page==null)page=1;
        return memberService.queryForPage(10,page,"FROM UserRole WHERE 1=1",userRole,null);
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public List<UserRole> getUserRoleList(UserRole userRole) {
        return this.baseDao.findByQueryString("FROM UserRole WHERE 1=1 "+ HqlParamUtil.getFieldValue(userRole));
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public Set<String> sreachRolesForUserName(String userName) {
        Set<String> s = new HashSet<String>();
        User user = this.userService.getUserByUserName(userName);
        if(user!=null){
            List<UserRole> rs = this.baseDao.findByProperty(UserRole.class,"userid",user.getId());
            if(rs!=null&&rs.size()>0){
                Role role;
                for(UserRole ur : rs){
                    role = null;
                    role = this.baseDao.findUniqueByProperty(Role.class,"id",ur.getRoleid());
                    if(role!=null){
                        s.add(role.getRname());
                    }

                }
            }

        }
        return s;
    }
}


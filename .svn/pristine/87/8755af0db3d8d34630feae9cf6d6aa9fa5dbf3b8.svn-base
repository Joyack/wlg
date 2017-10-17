package com.wlg.Service.Impl;

import com.wlg.Dao.*;
import com.wlg.Model.PageBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.wlg.Model.Role;
import com.wlg.Model.UserRole;
import com.wlg.Util.*;
import com.wlg.Model.User;
import com.wlg.Service.MemberService;
import com.wlg.Service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserDao userDao;
    @Resource
    private BaseDao baseDao;
    @Resource
    private MemberService memberService;
    @Resource
    private UserRoleDao userRoleDao;
    @Resource
    private RoleDao roleDao;

    @Resource
    private MemberDao memberDao;

    /**
    * 初始化Log4j的一个实例
    */
    private static final Logger logger = Logger
        .getLogger(UserServiceImpl.class);

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int saveUser(User user) {
        if(StringUtils.isEmpty(user.getUsername())) return 2;
        User u = this.baseDao.findUniqueByProperty(User.class,"username",user.getUsername());
        if(u!=null) return 3;
        user.setPassword(MD5Util.MD5Encode(user.getPassword(),"utf-8"));
        this.userDao.saveUser(user);

        return 1;
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public int checkUserInfo(User user) {
        if(StringUtils.isEmpty(user.getUsername())) return 2;
        if(StringUtils.isEmpty(user.getPassword())) return 3;
        User u = this.baseDao.findUniqueByProperty(User.class,"username",user.getUsername());
        if(u==null) return 4;
        if(!u.getPassword().equals(MD5Util.MD5Encode(user.getPassword(),"utf-8")))
            return 5;
        else
            return 1;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int saveUserByMeter(User user) {
        if(StringUtils.isEmpty(user.getUsername())) return 2;
        User u = this.baseDao.findUniqueByProperty(User.class,"username",user.getUsername());
        if(u!=null) return 3;
        user.setPassword(user.getPassword());
        this.userDao.saveUser(user);
        return 1;

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int saveUser(User user, String role_id) {
        User u = this.baseDao.findUniqueByProperty(User.class,"username",user.getUsername());
        if(u!=null){
            return 2;
        }else{

            if(role_id==null) return 0;
            user.setPassword(MD5Util.MD5Encode(user.getPassword(),"utf-8"));
            Serializable uid = this.userDao.saveUser(user);

            UserRole ur = new UserRole();
            ur.setRoleid(role_id);
            ur.setUserid(uid.toString());
            this.userRoleDao.saveUserRole(ur);

            return 1;
        }
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int updateUserByself(User user, String oldPassword,PrincipalCollection principalCollection) {
        if(StringUtils.isEmpty(user.getUsername())) return 2;
        if(principalCollection==null) return 3;
        User u = this.baseDao.findUniqueByProperty(User.class,"username",user.getUsername());
        if(u==null) return 4;
        if(!u.getUsername().equals(principalCollection.toString())) return 5;
        String password = "";
        if(!StringUtils.isEmpty(oldPassword)){
            if(MD5Util.MD5Encode(oldPassword,"utf-8").equals(u.getPassword())) {
                u.setPassword(MD5Util.MD5Encode(user.getPassword(),"utf-8"));
                password = u.getPassword();
            }else {
                return 6;
            }
        }

        u.setUname(user.getUname());
        u.setEmail(user.getEmail());
        u.setPhonenumber(user.getPhonenumber());
        u.setPost(user.getPost());
        this.userDao.updateUser(u);

        return 1;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int updateUser(User user) {
        if(!StringUtils.isEmpty(user.getPassword())){
            //if(MD5Util.MD5Encode(user.getPassword(),"utf-8").equals(user.getPassword())) {
                user.setPassword(MD5Util.MD5Encode(user.getPassword(),"utf-8"));

        }
        this.userDao.updateUser(user);
        return 1;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int updateUserByUserName(User user) {
        if(StringUtils.isEmpty(user.getUsername())) return 2;
        User u = this.baseDao.findUniqueByProperty(User.class,"username",user.getUsername());
        if(u==null)return 3;
        u.setUname(user.getUname());
        u.setEmail(user.getEmail());
        u.setPhonenumber(user.getPhonenumber());
        if(!StringUtils.isEmpty(user.getPassword()))
            u.setPassword(user.getPassword());
        this.userDao.updateUser(u);
        return 1;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int updateUserPasswordByUserName(User user) {
        if(StringUtils.isEmpty(user.getUsername())) return 2;
        if(StringUtils.isEmpty(user.getPassword())) return 2;
        User u = this.baseDao.findUniqueByProperty(User.class,"username",user.getUsername());
        if(u!=null){
            u.setPassword(user.getPassword());
            this.userDao.updateUser(u);

            return 1;
        }
         return 0;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int deleteUser(User user) {
        if(StringUtils.isEmpty(user.getId())) return 2;
        User u = this.baseDao.findUniqueByID(User.class,user.getId());
        if(u==null) return 3;

        this.userDao.deleteUser(u);
        Role role = this.roleDao.getRoleByName(Contant.getRoleTempName(u.getUsername()));
        if(role!=null){
            //删除临时角色
            this.roleDao.deleteRole(role);
            //删除角色资源关系
            this.baseDao.executeSql("delete from userrole where roleid=?",new Object[]{role.getId()});
            //删除角色权限关系
            this.baseDao.executeSql("delete from permissionrole where roleid=?",new Object[]{role.getId()});
        }

        return 1;

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public int deleteUserByUserName(User user) {
        if(StringUtils.isEmpty(user.getUsername())) return 2;
        User u = this.baseDao.findUniqueByProperty(User.class,"username",user.getUsername());
        if(u==null) return 3;

        this.userDao.deleteUser(u);
        Role role = this.roleDao.getRoleByName(Contant.getRoleTempName(u.getUsername()));
        if(role!=null){
            //删除临时角色
            this.roleDao.deleteRole(role);
            //删除角色资源关系
            this.baseDao.executeSql("delete from userrole where roleid=?",new Object[]{role.getId()});
            //删除角色权限关系
            this.baseDao.executeSql("delete from permissionrole where roleid=?",new Object[]{role.getId()});
        }
        return 1;

    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public PageBean sreachUserForPage(Integer page, User user) {
        if(page==null)page=1;
        return memberService.queryBySQLForPage(15,page,"FROM User WHERE 1=1",user,null);
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public List<User> getUserList(User user) {
        return this.baseDao.findByQueryString(" FROM User WHERE 1=1 "+ HqlParamUtil.getFieldValue(user));
    }
    @Override
    public PageBean getUserListByParams(int page, int pageSize, Map<String,Object> map){
        StringBuffer sql=new StringBuffer("select * from (select u.id,u.email,u.phonenumber,u.username,u.uname,u.roleid,u.deptid , " +
                " (select r.rname from role r where r.id=u.roleid ) rname , " +
                " (select r.name from role r where r.id=u.roleid ) rolename , " +
                " (select d.deptname from deptinfo d where d.id=u.deptid ) deptname  " +
                " from user u ) t where 1=1 ");
        if (map!=null && map.size()>0){
            for (String key:map.keySet()){
                String paramValue=map.get(key).toString();
                if(paramValue==null || paramValue.equals("")){
                    continue;
                }
                if(key.equals("findkey")){
                    sql.append(" and (t.username ='"+paramValue+"' or t.uname ='"+paramValue+"')");
                }
                if(key.equals("roleid")){
                    sql.append(" and t.rolename ='"+paramValue+"'");
                }
            }
        }

        return memberDao.queryMapsBypage(null,page,pageSize,sql.toString());
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)
    public User getUserByUserName(String username) {
        return this.baseDao.findUniqueByProperty(User.class,"username",username);
    }


    @Override
    public List<Map<String, Object>> getUserByUserName1(String username) {
        StringBuffer sql=new StringBuffer("select u.id,u.username,u.uname,u.email,r.rname,d.deptname,r.id rid,d.id did from user u,deptinfo d,role r where u.roleid=r.id and u.deptid=d.id and u.username='"+username+"'");
        return memberDao.queryForPageByParams(10,1,sql.toString()).getResult();
    }



}


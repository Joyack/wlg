package com.wlg.Interceptor;

import com.wlg.Model.User;
import com.wlg.Service.UserRoleService;
import com.wlg.Service.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by lvlf on 2016/7/12.
 *
 * Subject验证的过程可以有效地划分分以下三个步骤：
 * 1.收集Subject提交的身份和证明；
 * 2.向Authenticating提交身份和证明；
 * 3.如果提交的内容正确，允许访问，否则重新尝试验证或阻止访问
 * Realm：可以有1个或多个Realm，可以认为是安全实体数据源，即用于获取安全实体的；
 * 可以是JDBC实现，也可以是LDAP实现，或者内存实现等等；由用户提供；
 * 注意：Shiro不知道你的用户/权限存储在哪及以何种格式存储；所以我们一般在应用中都需要实现自己的Realm
 *
 *
 */
public class CustomMonitorRealm extends AuthorizingRealm {
	/*
	 * @Autowired UserService userService;
	 *
	 * @Autowired RoleService roleService;
	 *
	 * @Autowired LoginLogService loginLogService;
	 */
    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;

    private Logger logger = Logger.getLogger(CustomMonitorRealm.class);

    public CustomMonitorRealm() {
        super();
    }




    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
		/* 这里编写授权代码 资源列表*/
        String username = (String) principals.getPrimaryPrincipal();
        logger.info("***********************authc name:授权认证：username: " + username +" "+ principals.getRealmNames());


//        roleNames.add("admin");
//        permissions.add("user.do?myjsp");
//        permissions.add("login.do?main");
//        permissions.add("login.do?logout");


//        Set<Role> roleSet =  userService.findUserByUsername(username).getRoleSet();
//        //角色名的集合
//        Set<String> roles = new HashSet<String>();
//        //权限名的集合
//        Set<String> permissions = new HashS2et<String>();
//        Iterator<Role> it = roleSet.iterator();
//        while(it.hasNext()){
//            roles.add(it.next().getRname());
//            for(Permission per:it.next().getPermissionSet()){
//                permissions.add(per.getName());
//            }
//        }

        Set<String> roleNames = this.userRoleService.sreachRolesForUserName(username);
        Set<String> permissions = this.userRoleService.sreachRolesForUserName(username);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roleNames);
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		/* 这里编写认证代码 */
        logger.info("***********************authc name:authc pass:");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        logger.info("***********************authc name:authc name:" + token.getUsername());
        User user = userService.getUserByUserName(token.getUsername());

        //如果帐号不存在，输出
        //throw new UnknownAccountException();

        //如果帐号被禁用，输出
        //throw new DisabledAccountException();

		if (user != null) {
            logger.info("***********************authc name:" + token.getUsername() + " user:" + user.getUname() + "  pwd:" + user.getPassword() + "  getname:" + getName());
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
        }else{
            // 认证没有通过
            return null;
        }
    }
//
//
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

}

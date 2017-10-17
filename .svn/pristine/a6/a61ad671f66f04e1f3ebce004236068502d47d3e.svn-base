package com.wlg.Controller;

import com.wlg.Interceptor.CustomAuthorizationService;
import com.wlg.Interceptor.CustomMonitorRealm;
import com.wlg.Model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * Created by Administrator on 2016/11/18.
 */
@Controller
@RequestMapping(value = "shiro")
public class TestShiro  extends BaseController{

    @Resource
    private CacheManager cacheManager;

    @Resource
    private CustomAuthorizationService customAuthorizationService;

    @RequestMapping(value = "remove")
    public void remove(){
//        this.iAuthService.reCreateFilterChains(); //更新filterChainDefinitions
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User user = (User) session.getAttribute("userinfo");


        Cache<Object, Object> authorizationCache = cacheManager.getCache(CustomMonitorRealm.class.getName()+".authorizationCache");
        if (authorizationCache != null) {
            for (Object key : authorizationCache.keys()) {
                if(key.toString().equals(user.getUsername()))
                    authorizationCache.remove(key);
            }
        }

//        authorizationCache.clear();
        System.out.println(authorizationCache.size());
    }

    @RequestMapping(value="getUserName.do")
    public void getUserName(){
        PrincipalCollection principalCollection =
                (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        System.out.println(principalCollection.getRealmNames());
    }


    @RequestMapping(value = "getau.do")
    public void getau(){
//        this.customAuthorizationService.reCreateFilterChains(); //更新filterChainDefinitions
        Cache<Object, Object> authorizationCache = cacheManager.getCache(CustomMonitorRealm.class.getName()+".authorizationCache");
        System.out.println(authorizationCache.size());
    }


    @RequestMapping(value = "test.do")
    @ResponseBody
    public void test() throws ParseException {
        PrincipalCollection principalCollection =
                (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        System.out.println(principalCollection);
    }
}

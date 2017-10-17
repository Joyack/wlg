package com.wlg.Interceptor;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by lvliagnfeng on 2016/11/19.
 */
//资源或
// AuthorizationFilter抽象类事项了javax.servlet.Filter接口，它是个过滤器。  
public class CustomPermsAuthorizationFilter extends AuthorizationFilter {

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = this.getSubject(request, response);
        String[] perms = (String[])((String[])mappedValue);
        boolean isPermitted = true;
        if(perms != null && perms.length > 0) {
            if(perms.length == 1) {
                if(!subject.isPermitted(perms[0])) {
                    return false;
                }
            } else if(perms.length >= 1) {
                isPermitted = false;
                for(String perm : perms){
                    if(subject.isPermitted(perm)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
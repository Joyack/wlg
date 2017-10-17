package com.wlg.Interceptor;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * Created by lvliagnfeng on 2016/11/19.
 */
//角色或
// AuthorizationFilter抽象类事项了javax.servlet.Filter接口，它是个过滤器。  
public class CustomRolesAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) throws Exception {
        Subject subject = getSubject(req, resp);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }

        for (int i = 0; i < rolesArray.length; i++) {

            if (subject.hasRole(rolesArray[i])) {
                return true;
            }
        }

        return false;
    }

}
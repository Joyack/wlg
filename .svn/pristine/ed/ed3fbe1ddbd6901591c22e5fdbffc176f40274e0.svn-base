package com.wlg.Controller;


import com.wlg.Model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/25 0025.
 */
public class LoginFilter implements Filter{


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
         System.err.println("----------初始化了---------------");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userinfo");
        String url = request.getServletPath();
        String contextPath = request.getContextPath();
        if (url.equals("")) {
            url += "/";
        }
        String currentURL = request.getRequestURI(); // 取得根目录所对应的绝对路径:
        String targetURL = currentURL.substring(currentURL.indexOf("/", 1),
                currentURL.length()); // 截取到当前文件名用于比较



        if (!"/login/main.do".equals(url) && !"/html/index.jsp".equals(url)&& !"/login/check.do".equals(url)) {// 若访问后台资源
            // 过滤到login

            if (user == null) {// 转入管理员登陆页面
                response.sendRedirect(contextPath + "/html/index.jsp");
                return;
            }
        }
        // 1 直接访问登录后台
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}

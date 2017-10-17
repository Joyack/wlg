package com.wlg.Controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 该基类为了方便在Controller获取request,response,session
 *
 */
@Scope("prototype")
public abstract class BaseController{
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.response.setContentType("text/json;charset=UTF-8");
    }
}

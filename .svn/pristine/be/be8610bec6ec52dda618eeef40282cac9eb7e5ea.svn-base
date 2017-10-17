package com.wlg.Interceptor;

/**
 * 默认规则
 * Created by LvLiangFeng on 2016/11/23.
 */
public class DefaultMetaSource {
    public static String getDefaultMetaSourceForAnon(){
        StringBuffer sb = new StringBuffer("");

        sb.append("/login/main.do* = anon\n\n");
        sb.append("/login/check.do* = anon\n\n");
        sb.append("/html/index.jsp* = anon\n\n");
        sb.append("/html/userLogin.html* = anon\n\n");
        return sb.toString();
    }

    public static String getDefaultMetaSourceForJspAction(){
        StringBuffer sb = new StringBuffer("");
        sb.append("/*.jsp* = authc\n");
        sb.append("/*/*.jsp* = authc\n");
        sb.append("/*/*/*.jsp* = authc\n\n");

        sb.append("/*.html* = authc\n");
        sb.append("/*/*.html* = authc\n");
        sb.append("/*/*/*.html* = authc\n\n");

        sb.append("/api/*.do* = anon\n");
        sb.append("/*.do* = authc\n");
        sb.append("/*/*.do* = authc\n");
        sb.append("/*/*/*.do* = authc\n\n");

        sb.append("/** = authc\n\n");
        sb.append("/*/*/** = authc\n\n");


//        sb.append("/user.jsp = authc,roleOrFilter[zj,fgfg,jhj]\n\n");
//        sb.append("/mrs.jsp = authc,roleOrFilter[zj,jhj]\n\n");
//        sb.append("/mrs.jsp = authc,roleOrFilter[zj,fgfg,jhj]\n\n");
//        sb.append("/mrs.jsp = authc,roleOrFilter[zj,fgfg,jhj]\n\n");
//        sb.append("/mrs.jsp = authc,roleOrFilter[zj,fgfg,jhj]\n\n");
//        sb.append("/mrs.jsp = authc,roleOrFilter[zj,fgfg,jhj]\n\n");
        return sb.toString();
    }

}

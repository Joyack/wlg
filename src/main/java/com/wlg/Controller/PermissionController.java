package com.wlg.Controller;
import com.wlg.Interceptor.CustomAuthorizationService;
import com.wlg.Model.Permission;
import com.wlg.Service.PermissionService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Scope;
import java.util.List;
import com.wlg.Util.JSONHelper;
import org.springframework.web.bind.annotation.RequestMethod;;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import com.wlg.Model.PageBean;

import javax.annotation.Resource;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Controller
@Scope("prototype")
@RequestMapping(value="permission")
public class PermissionController extends BaseController{
    @Resource
    private PermissionService permissionService;
    @Resource
    private CustomAuthorizationService customAuthorizationService;
    /**
     * 初始化Log4j的一个实例
     */
    private static final Logger logger = Logger
            .getLogger(PermissionController.class);

    @RequestMapping(value="/add_Permission.do",method = RequestMethod.POST)
    @ResponseBody
    public String add_Permission(Permission permission) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.permissionService.savePermission(permission);
        }catch (RuntimeException e){
            logger.info(e);
        }

        if(i==1){
            this.customAuthorizationService.reCreateFilterChains();
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/add_DefultPermission.do",method = RequestMethod.POST)
    @ResponseBody
    public String add_DefultPermission(String Prefix) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.permissionService.add_DefultPermission(Prefix);
        }catch (RuntimeException e){
            logger.info(e);
        }
        if(i==1){
            this.customAuthorizationService.reCreateFilterChains();
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/delete_Permission.do",method = RequestMethod.POST)
    @ResponseBody
    public String delete_Permission(Permission permission) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.permissionService.deletePermission(permission);
        }catch (RuntimeException e){
            logger.info(e);
        }
        if(i==1){
            this.customAuthorizationService.reCreateFilterChains();
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/update_Permission.do",method = RequestMethod.POST)
    @ResponseBody
    public String update_Permission(Permission permission) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.permissionService.updatePermission(permission);
        }catch (RuntimeException e){
        }
        if(i==1){
            this.customAuthorizationService.reCreateFilterChains();
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/check_PermissionList.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_PermissionList(Permission permission) throws IOException {
        JSONObject json = new JSONObject();
        List<Permission> s = this.permissionService.getPermissionList(permission);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.toJSONStringDateOfCustom(s));
        return null;
    }

    @RequestMapping(value="/check_PermissionForpage.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_PermissionForPage(Integer pageno,Permission permission) throws IOException {
        PageBean pageBean = this.permissionService.sreachPermissionForPage(pageno,permission);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(pageBean));
        return null;
    }



}


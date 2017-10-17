package com.wlg.Controller;
import com.wlg.Model.PermissionRole;
import com.wlg.Service.PermissionRoleService;
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
@RequestMapping(value="permissionRole")
public class PermissionRoleController extends BaseController{
    @Resource
    private PermissionRoleService permissionRoleService;
    /**
     * 初始化Log4j的一个实例
     */
    private static final Logger logger = Logger
            .getLogger(PermissionRoleController.class);

    @RequestMapping(value="/add_PermissionRole.do",method = RequestMethod.POST)
    @ResponseBody
    public String add_PermissionRole(PermissionRole permissionRole) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.permissionRoleService.savePermissionRole(permissionRole);
        }catch (RuntimeException e){
            logger.info(e);
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/delete_PermissionRole.do",method = RequestMethod.POST)
    @ResponseBody
    public String delete_PermissionRole(PermissionRole permissionRole) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.permissionRoleService.deletePermissionRole(permissionRole);
        }catch (RuntimeException e){
            logger.info(e);
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/update_PermissionRole.do",method = RequestMethod.POST)
    @ResponseBody
    public String update_PermissionRole(PermissionRole permissionRole) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.permissionRoleService.updatePermissionRole(permissionRole);
        }catch (RuntimeException e){
            logger.info(e);
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/check_PermissionRoleList.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_PermissionRoleList(PermissionRole permissionRole) throws IOException {
        JSONObject json = new JSONObject();
        List<PermissionRole> s = this.permissionRoleService.getPermissionRoleList(permissionRole);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.toJSONStringDateOfCustom(s));
        return null;
    }

    @RequestMapping(value="/check_PermissionRoleForpageBean.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_PermissionRoleForPageBean(Integer pageno,PermissionRole permissionRole) throws IOException {
        PageBean pageBean = this.permissionRoleService.sreachPermissionRoleForPage(pageno,permissionRole);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(pageBean));
        return null;
    }
}


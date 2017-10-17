package com.wlg.Controller;
import com.wlg.Model.Role;
import com.wlg.Service.RoleService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.List;
import com.wlg.Util.JSONHelper;
import org.springframework.web.bind.annotation.RequestMethod;;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.Map;

import com.wlg.Model.PageBean;

import javax.annotation.Resource;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Controller
@Scope("prototype")
@RequestMapping(value="role")
public class RoleController extends BaseController{
    @Resource
    private RoleService roleService;
    /**
     * 初始化Log4j的一个实例
     */
    private static final Logger logger = Logger
            .getLogger(RoleController.class);

    @RequestMapping(value="/add_Role.do",method = RequestMethod.POST)
    @ResponseBody
    public String add_Role(Role role) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.roleService.saveRole(role);
        }catch (RuntimeException e){
            logger.info(e);
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/delete_Role.do",method = RequestMethod.POST)
    @ResponseBody
    public String delete_Role(Role role) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.roleService.deleteRole(role);
        }catch (RuntimeException e){
            logger.info(e);
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/update_Role.do",method = RequestMethod.POST)
    @ResponseBody
    public String update_Role(Role role) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.roleService.updateRole(role);
        }catch (RuntimeException e){
            logger.info(e);
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/check_RoleList.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_RoleList(Role role) throws IOException {
        JSONObject json = new JSONObject();
        List<Role> s = this.roleService.getRoleList(role);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.toJSONStringDateOfCustom(s));
        return null;
    }

    @RequestMapping(value="/check_RoleByUserName.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_RoleByUserName(String username) throws IOException {
        JSONObject json = new JSONObject();
        Role role = this.roleService.RoleByUserName(username);
        json.put("roleid",role.getId());
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/check_RoleForPage.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_RoleForPage(Integer page,Role role) throws IOException {
        Map<String,Object> map=new HashMap<>();
        String findkey=request.getParameter("findkey");
        if(findkey!=null && !findkey.equals("")){
            map.put("findkey",findkey);
        }

        PageBean pageBean = this.roleService.sreachRoleForPage(page,role,map);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(pageBean));
        return null;
    }

}


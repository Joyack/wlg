package com.wlg.Controller;
import com.wlg.Interceptor.AuthorizationCacheManagementService;
import com.wlg.Interceptor.CustomAuthorizationService;
import com.wlg.Model.ResourcesRole;
import com.wlg.Service.ResourcesRoleService;
import com.wlg.Service.RoleService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Scope;
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
@RequestMapping(value="resourcesRole")
public class ResourcesRoleController extends BaseController{
    @Resource
    private ResourcesRoleService resourcesRoleService;
    @Resource
    private CustomAuthorizationService customAuthorizationService;
    @Resource
    private AuthorizationCacheManagementService authorizationCacheManagementService;
    @Resource
    private RoleService roleService;
    /**
     * 初始化Log4j的一个实例
     */
    private static final Logger logger = Logger
            .getLogger(ResourcesRoleController.class);

    @RequestMapping(value="/add_ResourcesRole.do",method = RequestMethod.POST)
    @ResponseBody
    public String add_ResourcesRole(ResourcesRole resourcesRole) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.resourcesRoleService.saveResourcesRole(resourcesRole);
        }catch (RuntimeException e){
            logger.info(e);
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/delete_ResourcesRole.do",method = RequestMethod.POST)
    @ResponseBody
    public String delete_ResourcesRole(ResourcesRole resourcesRole) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.resourcesRoleService.deleteResourcesRole(resourcesRole);
        }catch (RuntimeException e){
            logger.info(e);
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/update_ResourcesRole.do",method = RequestMethod.POST)
    @ResponseBody
    public String update_ResourcesRole(ResourcesRole resourcesRole, String username) throws IOException {
        JSONObject json = new JSONObject();
        if(StringUtils.isEmpty(username)){
            json.put("status",4);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(json.toString());
            return null;
        }
        int i = 0;
        try {
            i = this.resourcesRoleService.updateResourcesRole(resourcesRole);
        }catch (RuntimeException e){
            logger.info(e);
        }
        if(i==1){
            this.authorizationCacheManagementService.removeAuthorizationCache(username);
            this.customAuthorizationService.reCreateFilterChains();
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }


    @RequestMapping(value="/update_ResourcesRoleByMy.do",method = RequestMethod.POST)
    @ResponseBody
    public String update_ResourcesRoleByMy(ResourcesRole resourcesRole) throws IOException {
        JSONObject json = new JSONObject();
        int i = this.resourcesRoleService.updateResourcesRole(resourcesRole);
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/check_ResourcesRoleListByPrincipalUserName.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_ResourcesRoleListByPrincipalUserName() throws IOException {
        JSONObject json = new JSONObject();
        PrincipalCollection principalCollection =
                (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        String username = null;
        List<Map<String,Object>> s = null;
        if(principalCollection!=null){
            username = principalCollection.toString();
            s = this.resourcesRoleService.getResourcesRoleListByPrincipal(username);
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.toJSONStringDateOfCustom(s));
        return null;
    }

    @RequestMapping(value="/check_ResourcesRoleListByUserName.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_ResourcesRoleListByUserName(String username) throws IOException {
        JSONObject json = new JSONObject();
        List<Map<String,Object>> s = this.resourcesRoleService.getResourcesRoleList(username);
        json.put("roleid",this.roleService.RoleByUserName(username).getId());
        json.put("data",JSONHelper.toJSONStringDateOfCustom(s));
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }


    @RequestMapping(value="/check_ResourcesRoleList.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_ResourcesRoleList(ResourcesRole resourcesRole) throws IOException {
        JSONObject json = new JSONObject();
        List<Map<String,Object>> s = this.resourcesRoleService.getResourcesRoleList(resourcesRole);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.toJSONString(s));
        return null;
    }


    @RequestMapping(value="/check_ResourcesRoleForPage.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_ResourcesRoleForPage(Integer page,ResourcesRole resourcesRole) throws IOException {
        PageBean pageBean = this.resourcesRoleService.sreachResourcesRoleForPage(page,resourcesRole);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(pageBean));
        return null;
    }
}


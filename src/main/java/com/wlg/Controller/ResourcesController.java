package com.wlg.Controller;
import com.wlg.Interceptor.CustomAuthorizationService;
import com.wlg.Model.Permission;
import com.wlg.Model.Resources;
import com.wlg.Model.User;
import com.wlg.Service.PermissionService;
import com.wlg.Service.ResourcesService;
import com.wlg.Service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Scope;

import java.util.*;

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
@RequestMapping(value="resources")
public class ResourcesController extends BaseController{
    @Resource
    private ResourcesService resourcesService;

    @Resource
    private CustomAuthorizationService customAuthorizationService;

    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionsService;
    /**
     * 初始化Log4j的一个实例
     */
    private static final Logger logger = Logger
            .getLogger(ResourcesController.class);

    @RequestMapping(value="/add_Resources.do",method = RequestMethod.GET)
    @ResponseBody
    public String add_Resources(Resources resources) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.resourcesService.saveResources(resources);
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

    @RequestMapping(value="/delete_Resources.do",method = RequestMethod.POST)
    @ResponseBody
    public String delete_Resources(Resources resources) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.resourcesService.deleteResources(resources);
        }catch (RuntimeException e){
            logger.info(e);
        }
        if(i==1){
            //this.customAuthorizationService.reCreateFilterChains();
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/update_Resources.do",method = RequestMethod.POST)
    @ResponseBody
    public String update_Resources(Resources resources) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.resourcesService.updateResources(resources);
        }catch (RuntimeException e){
            logger.info(e);
        }
        if(i==1){
            //this.customAuthorizationService.reCreateFilterChains();
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/check_ResourcesList.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_ResourcesList(Resources resources) throws IOException {
        JSONObject json = new JSONObject();
        List<Resources> s = this.resourcesService.getResourcesList(resources);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.toJSONStringDateOfCustom(s));
        return null;
    }

    @RequestMapping(value="/check_ResourcesForPage.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_ResourcesForPage(Integer page,Resources resources) throws IOException {
        PageBean pageBean = this.resourcesService.sreachResourcesForPage(page,resources);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(pageBean));
        return null;
    }


    @RequestMapping(value="/queryAllResourcesByRole.do",method = RequestMethod.GET)
    @ResponseBody
    public String queryAllResourcesByRoleorUser(Integer page,Resources resources) throws IOException {
        User user=(User)request.getSession().getAttribute("userinfo");
        String username="";
        JSONObject json=new JSONObject();

        if(user==null){
           username="zhangjing";
        }else{
            username=user.getUsername();
        }
        String roleid=userService.getUserByUserName(username).getRoleid();
        List<Map<String,Object>> resourcesList=resourcesService.getMenuListByUser(username,roleid);
        json.put("result",resourcesList);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/queryAllResourcesByMenuid.do",method = RequestMethod.GET)
    @ResponseBody
    public String queryAllResourcesByMenuid(int page,int pageSize,String resourceid) throws IOException{
        List<Map<String,Object>> resourcesList=resourcesService.getMenuListByParentid(page,pageSize,resourceid);
        JSONObject json=new JSONObject();
        json.put("result",resourcesList);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return  null;
    }

    @RequestMapping(value="/queryAllResources.do",method = RequestMethod.GET)
    @ResponseBody
    public String queryAllResources() throws IOException{
        String ruid=request.getParameter("ruid");
        String ruflag=request.getParameter("ruflag");
        List<Map<String,Object>> resourcesList=resourcesService.getAllResources();
          if(ruflag!=null) {
              List<Map<String, Object>> permlist = resourcesService.getPermissionByRoleorUser(ruflag, ruid);
              List<String> permids = new ArrayList<>();
              if (permlist.size() > 0) {
                  for (Map<String, Object> map : permlist) {
                      permids.add(map.get("resourceid").toString());
                  }
              }
              for (Map<String, Object> map : resourcesList) {

                  if (permids.contains(map.get("id"))) {
                      map.put("hasAuth", "1");
                  }
              }
          }
        JSONObject json=new JSONObject();
        json.put("result",resourcesList);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return  null;
    }


    /*
    * 给用户分配权限
    * */
    @RequestMapping(value="/updateRoleorUserRes.do",method = RequestMethod.POST)
    @ResponseBody
    public String updateUserRes()throws IOException{
        JSONObject json = new JSONObject();
        String  roleId = request.getParameter("roleId");
        String resids = request.getParameter("resourceIds");//资源Id列表，多个Id用“,”分隔
        String ruflag=request.getParameter("ruflag");
        String userId=request.getParameter("userId");

        resids = resids+",16,17";
        String rIds[] = resids.split(",");
        if(rIds != null) {
            List<Permission> rrs = new ArrayList<Permission>();
            if(ruflag.equals("role")) {
               List<Map<String,Object>> permissions= resourcesService.getPermissionByRoleorUser(ruflag,roleId);
               if(permissions.size()>0){
                   for (Map<String,Object> map:permissions){
                       Permission p=new Permission();
                       p.setId(map.get("id").toString());
                       permissionsService.deletePermission(p);
                   }
               }
                for (String sId : rIds) {
                    Resources r=resourcesService.getResourceByid(sId);
                    if(r!=null && r.getId()!=null) {
                        Permission rr = new Permission();
                        rr.setRoleid(roleId);
                        rr.setResourceid(sId);
                        rrs.add(rr);
                    }
                }
            }else if(ruflag.equals("user")){
                List<Map<String,Object>> permissions= resourcesService.getPermissionByRoleorUser(ruflag,userId);
                if(permissions.size()>0){
                    for (Map<String,Object> map:permissions){
                        Permission p=new Permission();
                        p.setId(map.get("id").toString());
                        permissionsService.deletePermission(p);
                    }
                }
                for (String sId : rIds) {
                    Resources r=resourcesService.getResourceByid(sId);
                    if(r!=null && r.getId()!=null) {
                        Permission rr = new Permission();
                        rr.setUserid(userId);
                        rr.setResourceid(sId);
                        rrs.add(rr);
                    }
                }
            }
            resourcesService.saveRoleorUserRes(rrs);
            json.put("msg","success");
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());

        return null;
    }


}


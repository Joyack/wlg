package com.wlg.Controller;
import com.wlg.Model.UserRole;
import com.wlg.Service.UserRoleService;
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
@RequestMapping(value="userRole")
public class UserRoleController extends BaseController{
    @Resource
    private UserRoleService userRoleService;
    /**
     * 初始化Log4j的一个实例
     */
    private static final Logger logger = Logger
            .getLogger(UserRoleController.class);

    @RequestMapping(value="/add_UserRole.do",method = RequestMethod.POST)
    @ResponseBody
    public String add_UserRole(UserRole userRole) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.userRoleService.saveUserRole(userRole);
        }catch (RuntimeException e){
            logger.info(e);
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/delete_UserRole.do",method = RequestMethod.POST)
    @ResponseBody
    public String delete_UserRole(UserRole userRole) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.userRoleService.deleteUserRole(userRole);
        }catch (RuntimeException e){
            logger.info(e);
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/update_UserRole.do",method = RequestMethod.POST)
    @ResponseBody
    public String update_UserRole(UserRole userRole) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.userRoleService.updateUserRole(userRole);
        }catch (RuntimeException e){
            logger.info(e);
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/check_UserRoleList.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_UserRoleList(UserRole userRole) throws IOException {
        JSONObject json = new JSONObject();
        List<UserRole> s = this.userRoleService.getUserRoleList(userRole);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.toJSONStringDateOfCustom(s));
        return null;
    }

    @RequestMapping(value="/check_UserRoleForPage.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_UserRoleForPage(Integer page,UserRole userRole) throws IOException {
        PageBean pageBean = this.userRoleService.sreachUserRoleForPage(page,userRole);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(pageBean));
        return null;
    }
}


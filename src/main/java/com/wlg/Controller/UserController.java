package com.wlg.Controller;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.wlg.Dao.DeptDao;
import com.wlg.Model.Deptinfo;
import com.wlg.Model.Role;
import com.wlg.Model.User;
import com.wlg.Service.DeptService;
import com.wlg.Service.RoleService;
import com.wlg.Service.UserService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import com.wlg.Util.JSONHelper;
import org.springframework.web.bind.annotation.RequestMethod;;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.Map;

import com.wlg.Model.PageBean;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Controller
@Scope("prototype")
@RequestMapping(value="user")
public class UserController extends BaseController{
    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;
    @Resource
    private DeptService deptService;
    /**
     * 初始化Log4j的一个实例
     */
    private static final Logger logger = Logger
            .getLogger(UserController.class);



    @RequestMapping(value="/add_User.do",method = RequestMethod.POST)
    @ResponseBody
    public String add_User(User user) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        User u=this.userService.getUserByUserName(user.getUsername());
        if(u==null) {
            try {
                i = this.userService.saveUser(user);
            } catch (RuntimeException e) {
                logger.info(e);
            }
        }else{
            json.put("status","2");
        }

        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/delete_User.do",method = RequestMethod.POST)
    @ResponseBody
    public String delete_User(User user) throws IOException {
        JSONObject json = new JSONObject();
        session.setAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY,"admin");
        PrincipalCollection principalCollection =
                (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        int i = 0;
        if(!principalCollection.toString().equals(user.getUsername())) {
            try {
                i = this.userService.deleteUser(user);
            } catch (RuntimeException e) {
                logger.info(e);
            }
        }else{
            i = 5;
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }


    /**
     * 查询登陆用户名
     * @param
     * @return
     */
    @RequestMapping(value="/check_PrincipalUserName.do", method = RequestMethod.GET)
    public void check_PrincipalUserName(){
        JSONObject JSON = new JSONObject();
        PrincipalCollection principalCollection =
                (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        JSON.put("username",principalCollection.toString());
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(JSON.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 管理员更新用户资料
     * @param user
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/update_User.do",method = RequestMethod.POST)
    @ResponseBody
    public String update_User(User user) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try{
            i =  this.userService.updateUser(user);
        }catch (RuntimeException e){
            logger.info(e);
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }


    /**
     * 获取登陆用户资料
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/check_getPrincipalUser.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_getPrincipalUser() throws IOException {
        //获取登陆用户
        JSONObject json = new JSONObject();
        PrincipalCollection principalCollection =
                (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        List<Map<String,Object>> user = null;
        if(principalCollection!=null) {
            user = this.userService.getUserByUserName1(principalCollection.toString());
            if(user!=null){
               // user.get(0).put("id",null);
                json.put("result",user.get(0));
            }
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    /**
     * 用户修改资料
     * @param user
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/update_UserByself.do",method = RequestMethod.POST)
    @ResponseBody
    public String updateUserByself(User user) throws IOException {
        String oldPassword = request.getParameter("oldpasd");
        JSONObject json = new JSONObject();
        int i = 0;
        //获取登陆用户
        PrincipalCollection principalCollection =
                (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        try{
            i = this.userService.updateUserByself(user,oldPassword,principalCollection);
        }catch (RuntimeException e){
            logger.info(e);
        }

        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }


    @RequestMapping(value="/update_UserPassword.do",method = RequestMethod.POST)
    @ResponseBody
    public String update_UserPassword(User user) throws IOException {
        JSONObject json = new JSONObject();
        int i = 0;
        try {
            i = this.userService.updateUserPasswordByUserName(user);
        }catch (RuntimeException e){
            logger.info(e);
        }
        json.put("status",i);
        return json.toString();
    }

    @RequestMapping(value="/check_UserList.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_UserList(int page) throws IOException {
        JSONObject json = new JSONObject();
        Map<String,Object> map=new HashMap<>();
        String param=request.getParameter("param");
        if(param!=null && !"".equals(param)){
            map.put("findkey",param);
        }
        PageBean userlist=userService.getUserListByParams(page,10,map);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(userlist));
        return null;
    }




    @RequestMapping(value="/check_UserListView.do",method = RequestMethod.GET)
    public ModelAndView UserList(Integer page,User user){
        request.setAttribute("pageBean",this.userService.sreachUserForPage(page,user));
        return new ModelAndView("/page/user/UserList");
    }


    @RequestMapping(value="/check_UserForPage.do",method = RequestMethod.GET)
    @ResponseBody
    public String check_UserForPage(Integer page,User user) throws IOException {
        PageBean pageBean = this.userService.sreachUserForPage(page,user);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(pageBean));
        return null;
    }

    @RequestMapping(value="/addUser.do",method = RequestMethod.POST)
    public ModelAndView addUser(@RequestParam(value = "file", required = false) MultipartFile file, User user, String role_id, ModelMap model){
        if(file.getSize()>0) {
            String path = request.getSession().getServletContext().getRealPath("upload/userLogo");
            String fileName = file.getOriginalFilename();
            //重命名文件名
            // String fileName = new Date().getTime()+".jpg";
            String extName = fileName.substring(fileName.lastIndexOf("."));
            String newName = user.getUsername() + extName;
            System.out.println(path);
            File targetFile = new File(path, newName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            try {
                file.transferTo(targetFile);
                user.setLogourl(newName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //保存
        try {
            int i = this.userService.saveUser(user,role_id);
        } catch (Exception e) {
            logger.info(e);
        }
        return new ModelAndView("/page/user/UserCreat");
    }
    @RequestMapping(value="/getUserByRole.do",method = RequestMethod.GET)
    public String getUserByRole(String roleid,int page,int pageSize) throws IOException{
        Map<String ,Object> map=new HashMap<>();
        if(roleid!=null){
            map.put("roleid",roleid);
        }
       PageBean p= userService.getUserListByParams(1,1000,map);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(p));
        return null;
    }
}


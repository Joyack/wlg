package com.wlg.Controller;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.wlg.Model.Deptinfo;
import com.wlg.Model.PageBean;
import com.wlg.Service.DeptService;
import com.wlg.Util.JSONHelper;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
@Controller
@Scope("prototype")
@RequestMapping(value="dept")
public class DeptContorller extends BaseController {
    @Resource
    private DeptService deptService;

    @RequestMapping(value="/addDept.do",method = RequestMethod.POST)
   public String addDept(Deptinfo deptinfo) throws IOException{
        JSONObject json=new JSONObject();
      Serializable id= deptService.addDept(deptinfo);
      if(id!=null){
          json.put("msg","1");
      }else {
          json.put("msg","0");
      }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
       return null;
   }
    @RequestMapping(value="/delDept.do",method = RequestMethod.POST)
    public String delDept(Deptinfo deptinfo)throws IOException{
        JSONObject json=new JSONObject();
       int i=deptService.delDept(deptinfo);
        if(i>0){
            json.put("msg","1");
        }else {
            json.put("msg","0");
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/getNextDeptid.do",method = RequestMethod.POST)
    public String getNextDeptid() throws IOException{
        JSONObject json=new JSONObject();
        String i=deptService.getNextDeptId();
        if(i!=null){
            json.put("msg",i);
        }else {
            json.put("msg","-1");
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }
    @RequestMapping(value="/updateDept.do",method = RequestMethod.POST)
    public String updateDept(Deptinfo deptinfo)throws IOException{
        JSONObject json=new JSONObject();
        int i=deptService.updateDept(deptinfo);
        if(i>0){
            json.put("msg","1");
        }else {
            json.put("msg","0");
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/check_DeptList.do",method = RequestMethod.GET)
    @ResponseBody
    public String getAllDept(int page,int pageSize) throws Exception{
        Map<String,Object> map=new HashMap<>();
        String findkey=request.getParameter("deptname");
        if(findkey!=null && !findkey.equals("")){
            map.put("deptname",findkey);
        }
        PageBean<Deptinfo> p=deptService.getDeptList(page,pageSize,map);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(p));
        return null;
    }
}

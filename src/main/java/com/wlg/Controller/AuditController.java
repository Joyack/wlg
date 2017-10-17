package com.wlg.Controller;

import com.wlg.Model.AuditInfo;
import com.wlg.Model.PageBean;
import com.wlg.Model.StockInfo;
import com.wlg.Model.User;
import com.wlg.Service.AuditService;
import com.wlg.Service.StockService;
import com.wlg.Util.JSONHelper;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/29 0029.
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "audit")
public class AuditController extends BaseController{
    @Resource
    private AuditService auditService;
    @Resource
    private StockService stockService;
    @RequestMapping(value="/getAuditListByPage.do", method = RequestMethod.GET)
    @ResponseBody
    public String getAuditListByPage(int page,int pageSize){
        User u=(User) session.getAttribute("userinfo");
        Map<String,Object> params=new HashMap<>();
        String atype=request.getParameter("atype");
        String findkey=request.getParameter("findkey");
        String begintime=request.getParameter("begintime");
        String endtime=request.getParameter("endtime");
        String user="";
        if(atype!=null && !atype.equals("")){
            params.put("atype",atype);
        }if(findkey!=null && !findkey.equals("")){
            params.put("findkey",findkey);
        }if(begintime!=null && !begintime.equals("")){
            params.put("begintime",begintime);
        }if(endtime!=null && !endtime.equals("")){
            params.put("endtime",endtime);
        }
        if(u!=null){
            user=u.getUsername();
        }else{
            user="zhangjing";
        }

        PageBean p= auditService.getAllAuditInfo(page,pageSize,params,user);
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            response.getWriter().write(JSONHelper.bean2json(p));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/addAuditInfo.do", method = RequestMethod.POST)
    public String saveAutitinfo(AuditInfo auditInfo){
        JSONObject json=new JSONObject();
        String userName="";//登录用户
        String subName="";//提交人
        String auditPerson="";//审核人
        String csperson="";//抄送人
        int i =0;
        try{
             i = this.auditService.addAuditInfo(auditInfo,userName,subName,csperson,auditPerson);
        }catch (RuntimeException e){

           // log.info("添加审批信息出错"+e);
        }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            response.getWriter().write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value="/updateAudit.do", method = RequestMethod.POST)
    public String updateAudit(String auditid) throws IOException{
        JSONObject json = new JSONObject();
        //String username=request.getParameter("username");//审核人
        String agree=request.getParameter("agree");//审核意见
       String type=request.getParameter("type");//审核通过or不通过
       String auditstatus=request.getParameter("auditstatus");
        Date d=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dateStr=format.format(d);//审核时间
        AuditInfo auditInfo=auditService.getAuditByid(auditid);
        User u=(User)session.getAttribute("userinfo");
        String userName = u.getUsername();
        int i=0;
        if (type!=null && !type.equals("")){
            if(type.equals("success")){//通过
                if(auditInfo!=null){
                   if( auditInfo.getAuditstatus().equals("01")){
                       if( auditInfo.getAuditperson().equals(userName)){
                           auditInfo.setAuditstatus(auditstatus);
                           auditInfo.setAuditagree(agree);
                           auditInfo.setAudittime(dateStr);
                           auditInfo.setUpdatetime(dateStr);
                           auditInfo.setUpdateauthor(userName);
                         i=  auditService.updateAudit(auditInfo);
                         StockInfo stockInfo=stockService.getStockById(auditInfo.getSid());
                         stockInfo.setAuditstatus(auditstatus);
                         stockService.updateStock(stockInfo);
                           json.put("msg",i);
                       }else{
                           json.put("msg","0");
                       }

                   }else if(auditInfo.getAuditstatus().equals("02")){//仓库审核通过
                       if( auditInfo.getAuditperson1().equals(userName)){//领导审核人=登录人
                           auditInfo.setAuditstatus(auditstatus);
                           auditInfo.setAuditagree1(agree);
                           auditInfo.setAudittime1(dateStr);
                           auditInfo.setUpdatetime(dateStr);
                           auditInfo.setUpdateauthor(userName);
                           i=  auditService.updateAudit(auditInfo);
                           StockInfo stockInfo=stockService.getStockById(auditInfo.getSid());
                           stockInfo.setAuditstatus(auditstatus);
                           stockService.updateStock(stockInfo);
                           json.put("msg",i);
                       }else{
                           json.put("msg","0");
                       }
                   }
                }

            }else if(type.equals("fail")){//不通过
                if(auditInfo!=null){
                    if( auditInfo.getAuditstatus().equals("01")) {
                        if (auditInfo.getAuditperson().equals(userName)) {
                            auditInfo.setAuditstatus(auditstatus);
                            auditInfo.setAuditagree(agree);
                            auditInfo.setAudittime(dateStr);
                            auditInfo.setUpdatetime(dateStr);
                            auditInfo.setUpdateauthor(userName);
                            i=auditService.updateAudit(auditInfo);
                            StockInfo stockInfo=stockService.getStockById(auditInfo.getSid());
                            stockInfo.setAuditstatus(auditstatus);
                            stockService.updateStock(stockInfo);
                            json.put("msg",i);
                        }
                    }else if(auditInfo.getAuditstatus().equals("02")){
                        if (auditInfo.getAuditperson1().equals(userName)) {
                            auditInfo.setAuditstatus(auditstatus);
                            auditInfo.setAuditagree1(agree);
                            auditInfo.setAudittime1(dateStr);
                            auditInfo.setUpdatetime(dateStr);
                            auditInfo.setUpdateauthor(userName);
                            i=auditService.updateAudit(auditInfo);
                            StockInfo stockInfo=stockService.getStockById(auditInfo.getSid());
                            stockInfo.setAuditstatus(auditstatus);
                            stockService.updateStock(stockInfo);
                            json.put("msg",i);
                        }
                    }
                }

            }else if(type.equals("cancel")){//撤销
                auditInfo.setAuditstatus("00");
                auditInfo.setUpdatetime(dateStr);
                auditInfo.setUpdateauthor(userName);
                i=auditService.updateAudit(auditInfo);
                StockInfo stockInfo=stockService.getStockById(auditInfo.getSid());
                stockInfo.setAuditstatus(auditstatus);
                stockService.updateStock(stockInfo);
                json.put("msg",i);
            }
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    @RequestMapping(value="/getAuditDetail.do", method = RequestMethod.GET)
    public String getAuditDetail(int page,int pageSize){
        User u=(User)session.getAttribute("userinfo");
            Map<String,Object> params=new HashMap<>();
            String atype=request.getParameter("atype");
            String auditid=request.getParameter("auditid");
            String user="subuser";
            if(atype!=null && !atype.equals("")){
                params.put("atype",atype);
            }else if(auditid!=null && !auditid.equals("")){
                params.put("auditid",auditid);
            }
            List<Map<String,Object>> p= auditService.getAuditDetail(page,pageSize,params);
            JSONObject json=new JSONObject();
            json.put("result",p);
            response.setHeader("Access-Control-Allow-Origin", "*");
            try {
                response.getWriter().write(json.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
}

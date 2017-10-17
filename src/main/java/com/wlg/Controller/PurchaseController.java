package com.wlg.Controller;

import com.wlg.Model.PageBean;
import com.wlg.Model.Purchase;
import com.wlg.Model.User;
import com.wlg.Service.PurchaseService;
import com.wlg.Util.JSONHelper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by LvLiangFeng on 2016/11/23.
 */

@Controller
@Scope("prototype")
@RequestMapping(value="purchase")
public class PurchaseController extends BaseController{
    @Resource
    private PurchaseService purchaseService;
    /**
     * 初始化Log4j的一个实例
     */
    private static final Logger logger = Logger
            .getLogger(PurchaseController.class);

    @RequestMapping(value="/addPurInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public String add_Purchase(Purchase cgInfo) throws IOException {
        User u= (User) session.getAttribute("userinfo");
        JSONObject JSON = new JSONObject();
        int n=0;
        if(u!=null){
        cgInfo.setId(UUID.randomUUID().toString());

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        cgInfo.setCgdate(cgInfo.getCreatetime());
       // cgInfo.setCreatetime(dateString);
        cgInfo.setCreateauthor(u.getUsername());
        n=purchaseService.savePurchase(cgInfo);
            if(n==0){
                JSON.put("msg","0");
            }else{
                JSON.put("msg","1");
            }
        }else{
            JSON.put("msg","2");//未登录;
        }

        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(JSON.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

/*编辑*/
  @RequestMapping(value="/updatePurInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public String update_Purchase(Purchase cgInfo) throws IOException {
        User u= (User) session.getAttribute("userinfo");
        JSONObject JSON = new JSONObject();
        int n=0;
        if(u!=null){
            cgInfo.setId(UUID.randomUUID().toString());

            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(currentTime);
            cgInfo.setCgdate(cgInfo.getCreatetime());
            // cgInfo.setCreatetime(dateString);
            cgInfo.setCreateauthor(u.getUsername());
            n=purchaseService.savePurchase(cgInfo);
            if(n==0){
                JSON.put("msg","0");
            }else{
                JSON.put("msg","1");
            }
        }else{
            JSON.put("msg","2");//未登录;
        }

        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(JSON.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/deletePur.do",method = RequestMethod.POST)
    @ResponseBody
    public String delete_Purchase(Purchase purchase) throws IOException {
        JSONObject json = new JSONObject();

        int i = 0;
        //if(!principalCollection.toString().equals(user.getUsername())) {
            try {
                i = this.purchaseService.delPurchase(purchase);
            } catch (RuntimeException e) {
                logger.info(e);
            }
        json.put("status",i);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }



    @RequestMapping(value="/getPurList.do",method = RequestMethod.GET)
    @ResponseBody
    public String getPurchaseByParams(int page,int pageSize){
        Map<String,Object> params=new HashMap<>();
        String findkey=request.getParameter("findkey");
        String cinstate=request.getParameter("cinstate");
        if(findkey!=null && !"".equals(findkey) ){
            params.put("findkey",findkey);
        }if(cinstate!=null &&!"".equals(cinstate)){
            params.put("instate",cinstate);
        }
         PageBean purchaseList=this.purchaseService.getPurListByParams(page,pageSize,params);

        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(JSONHelper.bean2json(purchaseList));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    @RequestMapping(value="/getPurchaseByPage.do",method = RequestMethod.GET)
    @ResponseBody
    public String getPurchaseByPage(int pageno,int pageSize){
        Map<String,Object> params=new HashMap<>();
        String purid=request.getParameter("purId");
        if(purid!=null && !"".equals(purid) ){
            params.put("purid",purid);
        }
        PageBean page=new PageBean();
        page.setPageNo(pageno);
        page.setPageSize(pageSize);
       PageBean result=this.purchaseService.getPurListByPage( pageno, pageSize,params);
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(JSONHelper.bean2json(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/getNextPurId.do", method = RequestMethod.GET)
    @ResponseBody
    public String getMaxGoodsId() throws IOException {

        String nextPurId=this.purchaseService.getNextPurId();
        // String nextGoodsId=maxPurId.substring(0,maxPurId.lastIndexOf("0")+1)+(Integer.parseInt(maxPurId.substring(2))+1);
        JSONObject json=new JSONObject();
        json.put("nextPurId",nextPurId);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());

        return null;
    }

    @RequestMapping(value="/getPurDetailById.do", method = RequestMethod.GET)
    @ResponseBody
    public String getPurDetailById(int page,int pageSize,String cgid) throws IOException {
        String id=request.getParameter("id");
       PageBean pageBean= this.purchaseService.getPurDetailByPurId(page,pageSize,id);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(pageBean));
        return null;
    }


}


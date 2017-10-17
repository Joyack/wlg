package com.wlg.Controller;

import com.wlg.Model.PageBean;
import com.wlg.Model.Purchase;
import com.wlg.Model.XhContract;
import com.wlg.Service.PurchaseService;
import com.wlg.Service.XhContractService;
import com.wlg.Util.JSONHelper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by zj on 2016/11/23.
 */

@Controller
@Scope("prototype")
@RequestMapping(value="xh")
public class XhContractController extends BaseController{
    @Resource
    private XhContractService xhContractService;
    /**
     * 初始化Log4j的一个实例
     */
    private static final Logger logger = Logger
            .getLogger(XhContractController.class);

    /*
    * 录入销货合同
    * */
    @RequestMapping(value="/addXhContract.do",method = RequestMethod.POST)
    @ResponseBody
    public String addXhContract(XhContract xhContract) throws IOException {

        JSONObject JSON = new JSONObject();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String time=dateString.substring(10);
        xhContract.setXhdate(xhContract.getCreatetime());
       // xhContract.setCreatetime(dateString);

        int n=xhContractService.saveXhContract(xhContract);
        if(n==0){
            JSON.put("msg","0");
        }else{

            //************************日志操作*************************************
            List<XhContract> uc = new ArrayList<XhContract>();
            uc.add(xhContract);
           // this.logsService.addLogs(new Location(), new Logs().setLogs(userDetails.getUsername(), LogContant.purModel,LogContant.addPur(cgInfo.getCid()), JSONHelper.toJSONString(uc),LogContant.ADD));
            JSON.put("msg","1");
        }
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(JSON.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/deleteXhContract.do",method = RequestMethod.POST)
    @ResponseBody
    public String deleteXhContract(XhContract xhContract) {
        JSONObject json = new JSONObject();

        int i = 0;
        //if(!principalCollection.toString().equals(user.getUsername())) {
            try {
                i = this.xhContractService.delXhContract(xhContract);
            } catch (RuntimeException e) {
                logger.info(e);
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



    @RequestMapping(value="/getXhContractList.do",method = RequestMethod.GET)
    @ResponseBody
    public String getXhContractList(int page,int pageSize){
        Map<String,Object> params=new HashMap<>();
        String findkey=request.getParameter("findkey");
        String outstate=request.getParameter("outstate");
        if(findkey!=null && !"".equals(findkey) ){
            params.put("findkey",findkey);
        }if(outstate!=null &&!"".equals(outstate)){
            params.put("outstate",outstate);
        }
         PageBean purchaseList=this.xhContractService.getXhContractListByParams(page,pageSize,params);

        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(JSONHelper.bean2json(purchaseList));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @RequestMapping(value="/getNextXhId.do", method = RequestMethod.GET)
    @ResponseBody
    public String getNextXhId() throws IOException {

        String nextPurId=this.xhContractService.getNextXhId();
        JSONObject json=new JSONObject();
        json.put("nextXhId",nextPurId);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());

        return null;
    }

    @RequestMapping(value="/getXhContractDetailById.do", method = RequestMethod.GET)
    @ResponseBody
    public String getXhContractDetailById(int page,int pageSize,String xhid) throws IOException {
        String id=request.getParameter("id");
       PageBean pageBean= this.xhContractService.getXhContractDetailById(page,pageSize,id);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(pageBean));
        return null;
    }


}


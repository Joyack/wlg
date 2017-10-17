package com.wlg.Controller;

import com.wlg.Model.Deptinfo;
import com.wlg.Model.PageBean;
import com.wlg.Service.DeptService;
import com.wlg.Service.ReportService;
import com.wlg.Util.JSONHelper;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
@Controller
@Scope("prototype")
@RequestMapping(value="report")
public class ReportContorller extends BaseController {
    @Resource
    private ReportService reportService;


    /*原材料库存表*/
    @RequestMapping(value="/rmInventory.do",method = RequestMethod.GET)
    @ResponseBody
    public String rmInventory(int page,int pageSize) throws Exception{
        String currtime=request.getParameter("datetime");
        PageBean p=reportService.rmInventory(page,pageSize,currtime);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(p));
        return null;
    }

    /*原材料入库流水*/
    @RequestMapping(value="/rmInStock.do",method = RequestMethod.GET)
    @ResponseBody
    public String rmInStock(int page,int pageSize) throws Exception{
        String currtime=request.getParameter("datetime");

        PageBean  p=reportService.rmInStock(page,pageSize,currtime);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(p));
        return null;
    }

    /*原材料出库流水*/
    @RequestMapping(value="/rmOutStock.do",method = RequestMethod.GET)
    @ResponseBody
    public String rmOutStock(int page,int pageSize) throws Exception{
        String currtime=request.getParameter("datetime");
        PageBean  p=reportService.rmOutStock(page,pageSize,currtime);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(p));
        return null;
    }




    /*成品库存表*/
    @RequestMapping(value="/fgInventory.do",method = RequestMethod.GET)
    @ResponseBody
    public String fgInventory(int page,int pageSize) throws Exception{
        String currtime=request.getParameter("datetime");
        PageBean p=reportService.fgInventory(page,pageSize,currtime);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(p));
        return null;
    }

    /*成品入库流水*/
    @RequestMapping(value="/fgInStock.do",method = RequestMethod.GET)
    @ResponseBody
    public String fgInStock(int page,int pageSize) throws Exception{
        String currtime=request.getParameter("datetime");
        PageBean  p=reportService.fgInStock(page,pageSize,currtime);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(p));
        return null;
    }

    /*成品出库流水*/
    @RequestMapping(value="/fgOutStock.do",method = RequestMethod.GET)
    @ResponseBody
    public String fgOutStock(int page,int pageSize) throws Exception{
        String currtime=request.getParameter("datetime");
        PageBean p=reportService.fgOutStock(page,pageSize,currtime);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(p));
        return null;
    }

    /*物料汇总表*/
    @RequestMapping(value="/materialSummary.do",method = RequestMethod.GET)
    @ResponseBody
    public String materialSummary(int page,int pageSize) throws Exception{
        String currtime=request.getParameter("datetime");
        PageBean p=reportService.materialSummary(page,pageSize,currtime);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.bean2json(p));
        return null;
    }

    @RequestMapping(value="/getDateList.do",method = RequestMethod.GET)
    @ResponseBody
    public String getDateList(String flag) throws Exception{
        JSONObject json=new JSONObject();
        List<Map<String,Object>> p=reportService.getDateList(flag);
        json.put("result",p);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }


}

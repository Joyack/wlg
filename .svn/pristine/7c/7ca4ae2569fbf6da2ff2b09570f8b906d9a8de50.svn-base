package com.wlg.Controller;

import com.wlg.Model.PageBean;
import com.wlg.Service.DiagramReportService;
import com.wlg.Service.ReportService;
import com.wlg.Util.JSONHelper;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
@Controller
@Scope("prototype")
@RequestMapping(value="dreport")
public class DiagramReportController extends BaseController {
    @Resource
    private DiagramReportService drService;

    @RequestMapping(value="/outInStocknum.do",method = RequestMethod.GET)
    @ResponseBody
    public String outInStocknum(String year ,String sopertype) throws IOException{
        Map<String,Object> map=new HashMap<>();
        String ids=request.getParameter("ids");
        StringBuffer gidstrs = new StringBuffer();
        if(ids!=null) {
            String[] gids = ids.split(",");

            for (int i=0;i<gids.length;i++) {
                if(i<gids.length-1){
                    gidstrs.append("'" + gids[i] + "',");
                }else{
                    gidstrs.append("'" + gids[i] +"'");
                }

            }
        }

        if(year!=null && !year.equals("")){
            map.put("year",year);
        }if(sopertype!=null && !sopertype.equals("")){
            map.put("sopertype",sopertype);
        }if(gidstrs!=null && !gidstrs.equals("")){
            map.put("ids",gidstrs);
        }
        List<Map<String,Object>> p=drService.outInStocknum(map);
        JSONObject json=new JSONObject();
          json.put("result",p);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }
    @RequestMapping(value="/salesStocknum.do",method = RequestMethod.GET)
    @ResponseBody
    public String salesStocknum(String year)throws IOException{
        Map<String,Object> map=new HashMap<>();
        String ids=request.getParameter("ids");
        StringBuffer gidstrs = new StringBuffer();
        if(ids!=null) {
            String[] gids = ids.split(",");

            for (int i=0;i<gids.length;i++) {
                if(i<gids.length-1){
                    gidstrs.append("'" + gids[i] + "',");
                }else{
                    gidstrs.append("'" + gids[i] +"'");
                }

            }
        }
        if(year!=null && !year.equals("")){
            map.put("year",year);
        }if(gidstrs!=null && !gidstrs.equals("")){
            map.put("ids",gidstrs);
        }
        List<Map<String,Object>> p=drService.salesStocknum(map);
        JSONObject json=new JSONObject();
        json.put("result",p);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }
    @RequestMapping(value="/purchaseStocknum.do",method = RequestMethod.GET)
    @ResponseBody
    public String purchaseStocknum(String year )throws IOException{
        Map<String,Object> map=new HashMap<>();
        String ids=request.getParameter("ids");
        StringBuffer gidstrs = new StringBuffer();
        if(ids!=null) {
            String[] gids = ids.split(",");

            for (int i=0;i<gids.length;i++) {
                if(i<gids.length-1){
                    gidstrs.append("'" + gids[i] + "',");
                }else{
                    gidstrs.append("'" + gids[i] +"'");
                }

            }
        }
        if(year!=null && !year.equals("")){
            map.put("year",year);
        }if(gidstrs!=null && !gidstrs.equals("")){
            map.put("ids",gidstrs);
        }
        List<Map<String,Object>> p=drService.purchaseStocknum(map);
        JSONObject json=new JSONObject();
        json.put("result",p);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }
    @RequestMapping(value="/stocknum.do",method = RequestMethod.GET)
    @ResponseBody
    public String stocknum(String year)throws IOException{
        Map<String,Object> map=new HashMap<>();
        String ids=request.getParameter("ids");
        StringBuffer gidstrs = new StringBuffer();
        if(ids!=null) {
            String[] gids = ids.split(",");

            for (int i=0;i<gids.length;i++) {
                if(i<gids.length-1){
                    gidstrs.append("'" + gids[i] + "',");
                }else{
                    gidstrs.append("'" + gids[i] +"'");
                }

            }
        }
        if(year!=null && !year.equals("")){
            map.put("year",year);
        }if(gidstrs!=null && !gidstrs.equals("")){
            map.put("ids",gidstrs);
        }
        List<Map<String,Object>> p=drService.stocknum(map);
        JSONObject json=new JSONObject();
        json.put("result",p);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }


}

package com.wlg.Controller;

import com.wlg.Model.GoodsInfo;
import com.wlg.Model.PageBean;
import com.wlg.Model.Supplier;
import com.wlg.Service.GoodsService;
import com.wlg.Service.SupplierService;
import com.wlg.Util.JSONHelper;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
@Controller
@Scope("prototype")
@RequestMapping(value="supplier")
public class SupplierController extends BaseController {
    @Resource
    private SupplierService supplierService;
    @Resource
    private GoodsService goodsService;

    @RequestMapping(value="/querySupplierList.do", method = RequestMethod.GET)
    @ResponseBody
    public String querySupplierList(int page,int pageSize){
        PageBean<Map<String,Object>> pageBean;
        Map<String,Object> param=new HashMap<>();

        String findkey=request.getParameter("findkey");
        String pid=request.getParameter("pid");
        if(findkey!=null && !findkey.equals("")){
            param.put("findkey",findkey);
        }if(pid !=null && !pid.equals("")){
            param.put("pid",pid);
        }
        pageBean = this.supplierService.querySupplierList(page,pageSize,param);

        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(JSONHelper.bean2json(pageBean));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增物品信息
     * @return
     */
    @RequestMapping(value="/addSupplier.do", method = RequestMethod.POST)
    @ResponseBody
    public String addSupplier(Supplier supplier){
        Map<String ,Object> params=new HashMap<>();
        String proname=request.getParameter("proname");
        String productid=request.getParameter("productid");
        String[] productids=null;
         JSONObject json = new JSONObject();
        List p=supplierService.querySupplierByName(proname);
        if(p!=null &&p.size()>0){
            json.put("err_msg","公司名字重复");
            json.put("msg","2");

        }else{

            int i=supplierService.addSupplier(supplier);
            if(i>0){
                json.put("msg","1");
            }else{
                json.put("msg","0");
            }
            //************************日志操作*************************************
            List<Supplier> uc = new ArrayList<>();
            uc.add(supplier);
            //  this.logsService.addLogs(new Location(), new Logs().setLogs(userDetails.getUsername(),LogContant.goodsModel,LogContant.addGoods(gInfo.getGname()),JSONHelper.toJSONString(uc),LogContant.ADD));
            //添加成功
            json.put("msg","1");
        }
        try{

            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
            json.put("msg","0");
            response.setHeader("Access-Control-Allow-Origin", "*");
            try {
                response.getWriter().write(json.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }
    @RequestMapping(value="/delSupplier.do", method = RequestMethod.POST)
    @ResponseBody
   public String delSupplier(Supplier supplier){
        JSONObject json=new JSONObject();
       // Map<String,Object> map=new HashMap<>();
      //  map.put("providerid",supplier.getId());
        // PageBean p=goodsService.queryGoodsBySql(1,10,map);
       // if(p.getResult()!=null && p.getResult().size()>0){

        //}
        int i=this.supplierService.delSupplier(supplier);
        if(i>0) {
            json.put("msg", "1");
        }else{
            json.put("msg","0");
        }
    try{

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
    } catch (IOException e) {
        e.printStackTrace();
        json.put("msg","0");
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            response.getWriter().write(json.toString());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
        return null;
}




    /**
     * 修改物品信息
     * @return
     */
    @RequestMapping(value="/updateSupplier.do", method = RequestMethod.POST)
    @ResponseBody
    public String updateSupplier(Supplier supplier){
        Map<String ,Object> params=new HashMap<>();
        String proname=request.getParameter("proname");
        String oldproname=request.getParameter("oldproname");
        String productid=request.getParameter("productid");
        String[] productids=null;
        JSONObject json = new JSONObject();

            List p=supplierService.querySupplierByName(proname);
            if(p!=null &&p.size()>0){
                if(!oldproname.equals(proname)){
                json.put("err_msg","公司名字重复");
                json.put("msg","2");

            }else{
                    int i=supplierService.updateSupplier(supplier);
                    if(i>0){
                        json.put("msg","1");
                    }else{
                        json.put("msg","0");
                    }
                }
        } int i=supplierService.updateSupplier(supplier);
        if(i>0){
            json.put("msg","1");
        }else{
            json.put("msg","0");
        }

            //************************日志操作*************************************
            List<Supplier> uc = new ArrayList<>();
            uc.add(supplier);
            //  this.logsService.addLogs(new Location(), new Logs().setLogs(userDetails.getUsername(),LogContant.goodsModel,LogContant.addGoods(gInfo.getGname()),JSONHelper.toJSONString(uc),LogContant.ADD));
            //添加成功

        try{

            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
            json.put("msg","0");
            response.setHeader("Access-Control-Allow-Origin", "*");
            try {
                response.getWriter().write(json.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

}

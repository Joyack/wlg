package com.wlg.Controller;


import com.wlg.Model.GoodsInfo;
import com.wlg.Model.PageBean;
import com.wlg.Model.StockInfo;
import com.wlg.Service.GoodsService;
import com.wlg.Util.JSONHelper;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zj on 2017/4/27 0027.
 * 物品管理
 */
@Controller
@Scope("prototype")
@RequestMapping(value="goods")
public class GoodsController extends BaseController {
    Log log  = LogFactory.getLog(GoodsController.class);
   // @Resource
   // private LogsService logsService;

    @Resource
    private GoodsService goodsService;

    //UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @RequestMapping(value="/queryGoodsList.do", method = RequestMethod.GET)
    @ResponseBody
    public String getGoodsList(int page,int pageSize){
        PageBean<Map<String,Object>> pageGoods;
        Map<String,Object> param=new HashMap<>();
        String findkey=request.getParameter("findkey");
        String gtid=request.getParameter("gtid");
        String providerid=request.getParameter("providerid");
        String gid=request.getParameter("gid");
        if(gtid!=null && !"".equals(gtid)){
            param.put("gtid",gtid);
        }
        if(providerid!=null && !"".equals(providerid)){
            param.put("providerid",providerid);
        }
        if(gid!=null && !gid.equals("")){
            param.put("gid",gid);
        }if(findkey!=null && !findkey.equals("")){
            param.put("findkey",findkey);
        }
        pageGoods = this.goodsService.queryGoodsBySql(page,pageSize,param);

        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(JSONHelper.bean2json(pageGoods));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 删除物品信息
     * @return
     */
    @RequestMapping(value="/deleteGoods.do", method = RequestMethod.POST)
    @ResponseBody
    public String deleteGoods(GoodsInfo gInfo){
        JSONObject JSON = new JSONObject();
       // UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(gInfo.getId()!=null&&!gInfo.getId().equals("")) {
            GoodsInfo u = this.goodsService.queryGoodsById( gInfo.getId());
            if (u!=null) {

                int n = 0;
                try{
                    n = this.goodsService.deleteGoods(u);
                }catch (RuntimeException e){
                    log.info("delete GOODS :"+e);
                }

                if(n==0){
                    JSON.put("msg","0");
                }else{

                    //************************日志操作*************************************
                    List<GoodsInfo> uc = new ArrayList<>();
                    uc.add(u);
                  // this.logsService.addLogs(new Location(), new Logs().setLogs(userDetails.getUsername(),LogContant.goodsModel,LogContant.delGoods(u.getGname()),JSONHelper.toJSONString(uc),LogContant.DELETE));
                    JSON.put("msg","1");
                }

            }

        }else{
            JSON.put("msg","error");
        }

        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(JSON.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增物品信息
     * @return
     */
    @RequestMapping(value="/addGoods.do", method = RequestMethod.POST)
    @ResponseBody
    public String addGoods(GoodsInfo gInfo){
        //UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date currDate=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetstr=format.format(currDate);
        JSONObject json = new JSONObject();
        Map<String,Object> params=new HashMap<>();
        params.put("gname",gInfo.getGname());
        params.put("gspec",gInfo.getGspec());
        params.put("providerid",gInfo.getProviderid());
        List<Map<String,Object>> glist=goodsService.isExistsGoods(params);
        if(glist.size()>0){
            json.put("err_msg","物品名称重复");
            json.put("msg","0");

        }else{
            gInfo.setCreatetime(datetstr);
            gInfo.setModifytime(datetstr);
            gInfo.setMperson("");
            gInfo.setId(UUID.randomUUID().toString());
            int i=goodsService.save(gInfo);
            //************************日志操作*************************************
            List<GoodsInfo> uc = new ArrayList<>();
            uc.add(gInfo);
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


    /**
     * 修改物品信息前查询
     * @return
     */
    @RequestMapping(value="/updateGoodsBefore.do", method = RequestMethod.POST)
    @ResponseBody
    public String updateBefore(String goodsId) throws IOException {
        //UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JSONObject json = new JSONObject();
        GoodsInfo goodsInfo=goodsService.queryGoodsById(goodsId);
        json.put("goods",JSONHelper.bean2json(goodsInfo));
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }

    /**
     * 修改物品信息
     * @return
     */
    @RequestMapping(value="/updateGoods.do", method = RequestMethod.POST)
    @ResponseBody
    public String updateGoods(GoodsInfo gInfo){
      //  UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JSONObject json = new JSONObject();
        GoodsInfo goodsInfo=goodsService.queryGoodsById(gInfo.getId());
        try{
            int i=goodsService.updateGoods(gInfo);
            //************************日志操作*************************************
            List<GoodsInfo> uc = new ArrayList<>();
            uc.add(gInfo);
           // this.logsService.addLogs(new Location(), new Logs().setLogs(userDetails.getUsername(),LogContant.goodsModel,LogContant.updateGoods(goodsInfo.getGname()),JSONHelper.toJSONString(uc),LogContant.UPDATE));
            //修改成功
            json.put("msg","1");
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


    @RequestMapping(value="/getNextGoodsId.do", method = RequestMethod.GET)
    @ResponseBody
    public String getMaxGoodsId() throws IOException {

        String nextGoodsId=this.goodsService.getNextGoodsId();
        JSONObject json=new JSONObject();
       json.put("nextGoodsId",nextGoodsId);
       response.setHeader("Access-Control-Allow-Origin", "*");
       response.getWriter().write(json.toString());

        return null;
    }


    @RequestMapping(value="/getAllGoodsName.do", method = RequestMethod.GET)
    @ResponseBody
    public String getAllGoodsName()throws IOException{
        JSONObject json=new JSONObject();
        List list= this.goodsService.getAllGoodsName();
        json.put("allGoods",list);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());

        return  null;
    }
    @RequestMapping(value="/importGoods.do", method=RequestMethod.POST)
    @ResponseBody
    public String importGoods() throws IOException {
        JSONObject json=new JSONObject();
        List<Map<String,Object>> listmap = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();


        boolean flag = true;
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            try {
                InputStream in = file.getInputStream();
                //listmap = ExcelUtil.importExcelByIs(in);

            } catch (Exception e) {
                json.put("msg","文件导入失败！");
                e.printStackTrace();
            }finally{
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        List<GoodsInfo> ms = new ArrayList<>();
        GoodsInfo goods = null;
        Map<String,Object> map = null;
        int count = 0;
        StringBuffer gname = new StringBuffer();
        List<String> title = new ArrayList<>();

        title.add("物品名称");
        title.add("物品类型");
        title.add("规格型号");
        title.add("单位");
        title.add("供应商");
        for (int i = 0; i < listmap.size(); i++) {
            for(String s : title){
                if(listmap.get(i).get(s)==null || "".equals(String.valueOf(listmap.get(i).get(s)))){
                    flag = false;
                    json.put("msg","文件导入失败！物品信息填写不完整！");
                    break;//有空的值 失败
                }
            }
        }
        if(flag) {
            for (int i = 0; i < listmap.size(); i++) {
                map = listmap.get(i);
//                GoodsInfo goodsInfo=this.goodsService.queryGoodsByName(map.get("物品名称").toString());
//                if(goodsInfo!=null){
//                    continue;
//                }
                goods = new GoodsInfo();
                goods.setGid(this.goodsService.getNextGoodsId());
                if (map.get("物品名称") != null && !("").equals(map.get("物品名称"))) {
                    goods.setGname(String.valueOf(map.get("物品名称")));
                }
                if (map.get("物品类型") != null && !("").equals(map.get("物品类型"))) {
                    goods.setGtid(String.valueOf(map.get("物品类型")).equals("主料") ? "01" : "02");
                }
                if (map.get("规格型号") != null && !("").equals(map.get("规格型号"))) {
                    goods.setGspec(String.valueOf(map.get("规格型号")));
                }
                if (map.get("单位") != null && !("").equals(map.get("单位"))) {
                    goods.setUnit(String.valueOf(map.get("单位")));
                }
                if (map.get("供应商") != null && !("").equals(map.get("供应商"))) {
                    goods.setProviderid(String.valueOf(map.get("供应商")));
                }
                goods.setId(UUID.randomUUID().toString());
                int n=this.goodsService.save(goods);

                gname.append(goods.getGname() + ",");
                if (n>0) {
                    count++;
                }
                ms.add(goods);
            }

            if (ms.size() > 0 && ms != null) {
                if (ms.size() == count) {
                    json.put("msg", "1");
                } else {
                    json.put("msg", "0");
                }
              //  this.logsService.addLogs(new Location(), new Logs().setLogs(userDetails.getUsername(), LogContant.goodsModel, LogContant.importGoods(gname.toString()), JSONHelper.toJSONString(ms), LogContant.IMPORT));

            } else {
                json.put("msg", "0");
            }
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }
    @RequestMapping(value="/downGoodsTemp.do", method=RequestMethod.GET)
    @ResponseBody
    public String downGoodsTemp() throws UnsupportedEncodingException {

        List<String> headList = new ArrayList<String>();//表头字段
        List<String> fieldList = new ArrayList<String>();//属性字段
        String path = null;
        String fileName = null;

            headList.add("物品名称");
            headList.add("物品类型");
            headList.add("规格型号");
            headList.add("单位");
            headList.add("供应商");
            fileName="物品信息表";
            System.err.println("----path-----"+request.getSession().getServletContext().getRealPath("/"));


           path = request.getSession().getServletContext().getRealPath("/")
                    + "/excel/CCGL-GoodsInfo.xls";   //excel模板



        try {
            InputStream in = new FileInputStream(new File(path));
            Workbook work = null;
            Sheet sheet = null;
            try {
                work = WorkbookFactory.create(new File(path));
                sheet = work.getSheetAt(0);
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
            // 得到excel的第0张表

            sheet.createFreezePane(0, 3, 0, 3);
            sheet.setDefaultRowHeightInPoints(20);
            // 得到第2行的第一个单元格的样式
            Row rowCellStyle = sheet.getRow(2);
            CellStyle content = rowCellStyle.getCell(0).getCellStyle();

            rowCellStyle = sheet.getRow(0);
            CellStyle top = rowCellStyle.getCell(0).getCellStyle();

            rowCellStyle = sheet.getRow(2);
            CellStyle time = rowCellStyle.getCell(0).getCellStyle();

            rowCellStyle = sheet.getRow(2);
            CellStyle title = rowCellStyle.getCell(0).getCellStyle();

            Row row = sheet.getRow(0);
            Cell cell = row.getCell(0);
            cell.setCellStyle(top);
            cell.setCellValue(fileName);

            row = sheet.getRow(2);
            cell = row.getCell(0);
            cell.setCellValue("物品名称");
            cell.setCellStyle(time);

            int i = 3;// 计数器
            row = sheet.createRow(i);// 得到行
            row.setHeightInPoints(20);
            for (int j = 0; j < headList.size(); j++) {
                cell = row.createCell(j);
            }
            OutputStream os = response.getOutputStream();// 取得输出流
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition",
                    "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
            work.write(os);
            os.close();
        }catch (FileNotFoundException e) {
            System.out.println("文件路径错误");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("文件输入流错误");
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args){
      List<String> slist=new ArrayList<>();
      slist.add("resourceid=kkk");
      slist.add("resourceid=bbb");
      String k="bbb";
      boolean b=slist.contains(k);
      int a=Arrays.binarySearch(slist.toArray(),k);
        System.err.println(slist);
      System.err.println(b);
        System.err.println(a);


    }




}

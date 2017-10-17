package com.wlg.Controller;

import com.wlg.Model.*;
import com.wlg.Service.CheckService;
import com.wlg.Service.GoodsService;
import com.wlg.Service.SupplierService;
import com.wlg.Util.ImportExcelUtil;
import com.wlg.Util.JSONHelper;
import net.sf.json.JSONObject;
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
//--------------------------------------------


/**
 * Created by Administrator on 2017/6/15 0015.
 */
@Controller
@Scope("prototype")
@RequestMapping(value="check")
public class CheckController extends BaseController {
  @Resource
  private CheckService checkService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private SupplierService supplierService;


    /*
   * 导入  库存盘点-导入盘点单
   * */
    @RequestMapping(value="/putinStock.do", method = RequestMethod.POST)
    @ResponseBody
    public String putinStockInfo() throws Exception {
        JSONObject j = new JSONObject();
        List<Map<String,Object>> listmap = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();


        boolean flag = true;
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            try {
                InputStream in = file.getInputStream();
                listmap = ExcelUtil.importExcelByIs(in);
                //ExcelUtil.
                for(int i=0;i<listmap.size();i++){
                    Map<String,Object> map=new HashMap<>();
                    String kc=listmap.get(i).get("库存数量").toString();
                    String pdkc=listmap.get(i).get("盘点库存数量").toString();
                    listmap.get(i).put("盈亏",Integer.parseInt(pdkc==null?"0":pdkc)-Integer.parseInt(kc==null?"0":kc));
                }

            } catch (Exception e) {
                j.put("msg","文件导入失败！");
                e.printStackTrace();
            }finally{
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(JSONHelper.toJSONString(listmap));
        return null;
    }



    @RequestMapping(value="/putinCkStockData.do", method = RequestMethod.POST)
    @ResponseBody
    public String putinCkStockData() throws Exception {
        JSONObject j=new JSONObject();
        request.setCharacterEncoding("utf-8");
        String jsonDataStr=request.getParameter("jsonData");

        if(jsonDataStr!=null && !jsonDataStr.equals("")) {
            jsonDataStr = new String(jsonDataStr.trim().getBytes("utf-8"), "utf-8");
        }
        List<CkStockInfo> ms = new ArrayList<>();
        List<Map<String,Object>> listmap=JSONHelper.parseJSON2List(jsonDataStr);
        CkStockInfo ckStockInfo = null;
        Map<String,Object> map = null;
        PageBean<Map<String,Object>> ckResultList=null;
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String batchno=dateString;
        batchno=batchno.replace("-","");
        batchno=batchno.replace(":","");
        batchno=batchno.replace(" ","");
        boolean flag=true;
        List<String> title = new ArrayList<>();

        title.add("库存数量");
        title.add("正常数量");
        title.add("故障数量");
        title.add("物品ID");
        title.add("盘点库存数量");
        title.add("盘点正常数量");
        title.add("盘点故障数量");
        for (int i = 0; i < listmap.size(); i++) {
            //for(String s : title){
            if(listmap.get(i).get("物品名称")==null || "".equals(String.valueOf(listmap.get(i).get("物品名称")))){
                flag = false;
                j.put("msg","文件导入失败！信息填写不完整！");
                break;//有空的值 失败
            }
            //}
        }
        for(int i=0;i<listmap.size();i++){
            map = listmap.get(i);
            ckStockInfo = new CkStockInfo();
            ckStockInfo.setId(UUID.randomUUID().toString());

            if(map.get("物品ID")!=null&&!("").equals(map.get("物品ID"))){
                ckStockInfo.setGid(String.valueOf(map.get("物品ID")));
            }
            if(map.get("库存数量")!=null&&!("").equals(map.get("库存数量"))){
                ckStockInfo.setCsnum(String.valueOf(map.get("库存数量")));
            }
            if(map.get("正常数量")!=null&&!("").equals(map.get("正常数量"))){
                ckStockInfo.setCsnnum(String.valueOf(map.get("正常数量")));
            }
            if(map.get("故障数量")!=null&&!("").equals(map.get("故障数量"))){
                ckStockInfo.setCsfnum(String.valueOf(map.get("故障数量")));
            }
            if(map.get("盘点库存数量")!=null&&!("").equals(map.get("盘点库存数量"))){
                ckStockInfo.setCknum(String.valueOf(map.get("盘点库存数量")));
            } if(map.get("盘点正常数量")!=null&&!("").equals(map.get("盘点正常数量"))){
                ckStockInfo.setCknnum(String.valueOf(map.get("盘点正常数量")));
            }
            if(map.get("盘点故障数量")!=null&&!("").equals(map.get("盘点故障数量"))){
                ckStockInfo.setCkfnum(String.valueOf(map.get("盘点故障数量")));
            } if(map.get("原因")!=null&&!("").equals(map.get("原因"))){
                ckStockInfo.setCkreason(String.valueOf(map.get("原因")));
            }
            User u = (User)session.getAttribute("userinfo");
            ckStockInfo.setCkperson(u.getUsername());

            ckStockInfo.setCkdate(dateString);
            ckStockInfo.setCkid(""+(i+1));

            ckStockInfo.setBatchno(batchno);
            ms.add(ckStockInfo);
        }

        if(ms.size()>0 && ms!=null) {
            int n=this.checkService.addCkImportData(ms);
            if(n==1){
                j.put("msg","1");
            }else{
                j.put("msg","0");
            }
            Map<String,Object> params=new HashMap<>();
            params.put("batchno",batchno);
            List<Map<String,Object>> p=checkService.getCkDetail(1,10000,params).getResult();
            if(p!=null &&p.size()>0){
                for (Map<String,Object> ckdate:p){
                    GoodsInfo goodsInfo=goodsService.queryGoodsById(ckdate.get("id").toString());
                    if(goodsInfo!=null){
                        goodsInfo.setStoragenum(Integer.parseInt(ckdate.get("cknum").toString()));
                        goodsInfo.setFaultnum(Integer.parseInt(ckdate.get("ckfnum").toString()));
                        goodsService.updateGoods(goodsInfo);
                    }
                }
            }
          //  this.logsService.addLogs(new Location(), new Logs().setLogs(userDetails.getUsername(), LogContant.hqModel,LogContant.putinCkData(dateString), JSONHelper.toJSONString(ms),LogContant.IMPORT));

        }else{
            j.put("msg","0");
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(j.toString());
        return null;
    }

    @RequestMapping(value="/getCkResultList.do", method = RequestMethod.GET)
    public String getCkResultList(int page,int pageSize)throws UnsupportedEncodingException{
        Map<String,Object> params=new HashMap<>();
        request.setCharacterEncoding("utf-8");
        String findkey=request.getParameter("findkey");
        String begintime=request.getParameter("begintime");
        String endtime=request.getParameter("endtime");
        if(findkey!=null && !findkey.equals("")){
            findkey = new String(findkey.trim().getBytes("utf-8"), "utf-8");
            params.put("findkey",findkey);
        }
        if(begintime!=null && !begintime.equals("")){
            params.put("begintime",begintime);
        }
        if(endtime!=null && !endtime.equals("")){
            params.put("endtime",endtime);
        }

        PageBean<Map<String,Object>> p=this.checkService.getCkCountResultList(page,pageSize,params);
        JSONObject json=new JSONObject();
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(JSONHelper.bean2json(p));
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/getCkDetail.do", method = RequestMethod.GET)
    public String getCkDetail(int page,int pageSize)throws UnsupportedEncodingException{
        Map<String,Object> params=new HashMap<>();
        String ckdate=request.getParameter("ckdate");
        String batchno=request.getParameter("batchno");
        if(ckdate!=null && !ckdate.equals("")) {
            params.put("ckdate", request.getParameter("ckdate"));
        }if(batchno!=null && !batchno.equals("")){
            params.put("batchno",batchno);
        }
        PageBean<Map<String,Object>> p=this.checkService.getCkDetail(page,pageSize,params);
        JSONObject json=new JSONObject();
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(JSONHelper.bean2json(p));
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    //下载盘点模板
    @RequestMapping(value="/downLoadCheckModel.do", method = RequestMethod.GET)
    public String downLoadCheckModel()throws UnsupportedEncodingException{
        request.setCharacterEncoding("utf-8");
        Map<String,Object> params=new HashMap<>();
        String findkey=request.getParameter("findkey");
        String flag=request.getParameter("downExcel");
        if(findkey!=null && !findkey.equals("")){
            findkey = new String(findkey.trim().getBytes("utf-8"), "utf-8");
            params.put("findkey",findkey);
        }

        List<String> headList = new ArrayList<String>();//表头字段
        List<String> fieldList = new ArrayList<String>();//属性字段
        String path = null;
        String fileName = null;
        headList.add("物品编号");
        headList.add("物品名称");
        headList.add("物品类型");
        headList.add("规格型号");
        headList.add("单位");
        headList.add("供应商");
        headList.add("库存数量");
        headList.add("正常数量");
        headList.add("故障数量");
        headList.add("盘点库存数量");
        headList.add("盘点正常数量");
        headList.add("盘点故障数量");
        headList.add("物品id");
        fieldList.add("gid");
        fieldList.add("gname");
        fieldList.add("gtid");
        fieldList.add("gspec");
        fieldList.add("unit");
        fieldList.add("provider");
        fieldList.add("storagenum");
        fieldList.add("nsum");
        fieldList.add("fsum");
        fieldList.add("id");
        fieldList.add("");
        fieldList.add("");
        fieldList.add("");
        //隐藏列

        fileName = "库存盘点表";
        System.err.println("----path-----"+request.getSession().getServletContext().getRealPath("/"));
        path = request.getSession().getServletContext().getRealPath("/")
                + "/excel/CCGL-CkStock.xls";   //excel模板

        try{
            InputStream in = new FileInputStream(new File(path));
            //XSSFWorkbook work = new XSSFWorkbook(in);
            Workbook work=null;
            Sheet sheet=null;
            try {
                work= WorkbookFactory.create(new File(path));
                sheet= work.getSheetAt(0);
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
            // 得到excel的第0张表

            sheet.createFreezePane( 0, 3, 0, 3 );
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
            cell.setCellValue("物品编号");
            cell.setCellStyle(time);

            int i = 3;// 计数器


            //分页取，数据量大的时候需要这样做
            List<Map<String, Object>> list = new ArrayList<>();
           // 先查询一下总数
            PageBean<Map<String,Object>> ps=this.checkService.downLoadCheckModel(1,10000,params);
            if(ps!=null&&ps.getTotalCount()>0){

                int pageSize = (int) ps.getTotalPageBeans();
                list.addAll(ps.getResult());
                for(int page = 2;page<=pageSize;page++){
                    ps = this.checkService.downLoadCheckModel(page,10000, params);
                    list.addAll(ps.getResult());
                }
            }


            for (Map map : list) {
                row = sheet.createRow(i);// 得到行
                row.setHeightInPoints(20);
                for (int j = 0; j < headList.size(); j++) {
                    cell = row.createCell(j);

                    if (!(map.get(fieldList.get(j)) == null)) {
                        if(fieldList.get(j).equals("gtid")){
                            String str=String.valueOf(map.get(fieldList.get(j)));
                            cell.setCellValue(str.equals("01")?"主料":"辅料");
                        }else {
                            cell.setCellValue(String.valueOf(map.get(fieldList.get(j))));
                        }
                        cell.setCellStyle(content);
                    } else {
                        cell.setCellValue("");
                    }
                }
                i++;

            }


            OutputStream os = response.getOutputStream();// 取得输出流
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition",
                    "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
            work.write(os);
            os.close();

           // this.logsService.addLogs(new Location(), new Logs().setLogs(userDetails.getUsername(), LogContant.hqModel,LogContant.exportStock(""), JSONHelper.toJSONString(list),LogContant.EXPORT));

        }catch (FileNotFoundException e) {
            System.out.println("文件路径错误");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("文件输入流错误");
            e.printStackTrace();
        }
        return null;
    }

    /*
        *导出盘点详情
        * */
    @RequestMapping(value="/exportCkStock.do", method = RequestMethod.GET)
    public String exportCkStock() throws UnsupportedEncodingException {
        String ckdate=request.getParameter("ckdate");
        String batchno=request.getParameter("batchno");
        Map<String,Object>  params=new HashMap<>();
        if(ckdate!=null&&!ckdate.equals("") ){
            params.put("ckdate",ckdate);
        }else if(batchno!=null&&!batchno.equals("") ){
            params.put("batchno",batchno);
        }
        List<String> headList = new ArrayList<String>();//表头字段
        List<String> fieldList = new ArrayList<String>();//属性字段
        String path = null;
        String fileName = null;
        headList.add("序号");
        headList.add("物品名称");
        headList.add("物品类型");
        headList.add("规格型号");
        headList.add("单位");
        headList.add("供应商");
        headList.add("库存数量");
        headList.add("盘点数量");
        headList.add("盈亏");
        headList.add("原因");
        fieldList.add("ckid");
        fieldList.add("gname");
        fieldList.add("gtid");
        fieldList.add("gspec");
        fieldList.add("unit");
        fieldList.add("provider");
        fieldList.add("csnum");
        fieldList.add("cknum");
        fieldList.add("yk");
        fieldList.add("ckreason");
        fileName="库存盘点详情表";
        path = request.getSession().getServletContext().getRealPath("/")
                + "/excel/CCGL-CkStockDetail.xls";   //excel模板
        try{
            InputStream in = new FileInputStream(new File(path));
            //XSSFWorkbook work = new XSSFWorkbook(in);
            Workbook work=null;
            Sheet sheet=null;
            try {
                work= WorkbookFactory.create(new File(path));
                sheet= work.getSheetAt(0);
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
            // 得到excel的第0张表

            sheet.createFreezePane( 0, 3, 0, 3 );
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
            cell.setCellValue("序号");
            cell.setCellStyle(time);

            int i = 3;// 计数器


            //分页取，数据量大的时候需要这样做
            List<Map<String, Object>> list = new ArrayList<>();
            //先查询一下总数
            PageBean<Map<String,Object>> ps=this.checkService.getCkDetail(1,10000,params);
            if(ps!=null&&ps.getTotalCount()>0){
                for(int n=0;n<ps.getResult().size();n++){
                    if(ps.getResult().get(n).get("ftype")=="01") {
                        // ps.getList().get(n).get("ftype")
                    }
                }
                long pageSize = ps.getTotalPageBeans();
                list.addAll(ps.getResult());
                for(int page = 2;page<=pageSize;page++){
                    ps = this.checkService.getCkDetail(page,10000, params);
                    list.addAll(ps.getResult());
                }
            }

            int n=0;
            for (Map map : list) {
                n++;
                row = sheet.createRow(i);// 得到行
                row.setHeightInPoints(20);
                for (int j = 0; j < headList.size(); j++) {
                    cell = row.createCell(j);

                    if (!(map.get(fieldList.get(j)) == null)) {
                        if(fieldList.get(j).equals("gtid")){
                            String str=String.valueOf(map.get(fieldList.get(j)));
                            cell.setCellValue(str.equals("01")?"主料":"辅料");
                        }else if(fieldList.get(j).equals("ckid")){
                            cell.setCellValue(n);
                        }else {
                            cell.setCellValue(String.valueOf(map.get(fieldList.get(j))));
                        }
                        cell.setCellStyle(content);
                    } else {
                        cell.setCellValue("");
                    }
                }
                i++;

            }


            OutputStream os = response.getOutputStream();// 取得输出流
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition",
                    "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
            work.write(os);
            os.close();

            //  this.logsService.addLogs(new Location(), new Logs().setLogs(userDetails.getUsername(), LogContant.hqModel,LogContant.exportCkDetail(ckdate), JSONHelper.toJSONString(list),LogContant.ADD));

        }catch (FileNotFoundException e) {
            System.out.println("文件路径错误");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("文件输入流错误");
            e.printStackTrace();
        }

        return null;
    }





}

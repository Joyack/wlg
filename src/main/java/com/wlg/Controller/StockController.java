package com.wlg.Controller;

import com.wlg.Model.*;
import com.wlg.Service.*;
import com.wlg.Util.JSONHelper;
import net.sf.json.JSONObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/6/15 0015.
 */
@Controller
@Scope("prototype")
@RequestMapping(value="stock")
public class StockController extends BaseController {
    @Resource
    private StockService stockService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private UserService userService;
    @Resource
    private AuditService auditService;

    @Resource
    private XhContractService xhContractService;
    /*
    * 出库前审批
    * */
    @RequestMapping(value="/outStockBefore.do" ,method= RequestMethod.POST)
    @ResponseBody
    public String outStockBefore(StockInfo stockInfo){
        User u = (User)session.getAttribute("userinfo");
        JSONObject json=new JSONObject(); Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String subuser="";
        if(u!=null){
          subuser=u.getUsername();
        }

       String ckuser=request.getParameter("auditperson");//仓库审核
       String csuser=request.getParameter("csuser");
       String lduser=request.getParameter("auditperson1");//领导审核
       // stockInfo.setSdate(dateString);
        stockInfo.setSperson(u.getUsername());
        if (stockInfo.getSopertype().equals("02")) {
            if(stockInfo.getStype1().equals("01") ||stockInfo.getStype1().equals("02") ||stockInfo.getStype1().equals("03") ||stockInfo.getStype1().equals("04")||stockInfo.getStype1().equals("05") ){
                stockInfo.setAuditstatus("01");
            }
        }
        GoodsInfo goodsInfo=goodsService.queryGoodsById(stockInfo.getGid());
        Serializable n=stockService.addStock(stockInfo);
        AuditInfo auditInfo=new AuditInfo();
        auditInfo.setSid(n.toString());
        auditInfo.setCreatetime(dateString);
        auditInfo.setSubPerson(subuser);
        auditInfo.setAuditperson(ckuser);
        auditInfo.setAuditperson1(lduser);
        auditInfo.setCsPerson(csuser);
        auditInfo.setAuditperson1(lduser);
        auditInfo.setAuditstatus("01");
        if(stockInfo.getStype1().equals("03")) {
            auditInfo.setTitle("领料出库审批");
            auditInfo.setContent("领料物品："+goodsInfo.getGname()+stockInfo.getSnum()+goodsInfo.getUnit());
            auditInfo.setAudittype("03");
        }else if(stockInfo.getStype1().equals("04")){
            auditInfo.setAudittype("04");
            auditInfo.setTitle("领用出库审批");
            auditInfo.setContent("领用物品："+goodsInfo.getGname()+stockInfo.getSnum()+goodsInfo.getUnit());
        }else if(stockInfo.getStype1().equals("01")){
            auditInfo.setAudittype("01");
            auditInfo.setTitle("发货出库审批");
            auditInfo.setContent("发货物品："+goodsInfo.getGname()+stockInfo.getSnum()+goodsInfo.getUnit());

        }else if(stockInfo.getStype1().equals("02")){
            auditInfo.setAudittype("02");
            auditInfo.setTitle("采购退货出库审批");
            auditInfo.setContent("退货物品："+goodsInfo.getGname()+stockInfo.getSnum()+goodsInfo.getUnit());

        }else if(stockInfo.getStype1().equals("05")){
            auditInfo.setAudittype("05");
            auditInfo.setTitle("成品退货出库审批");
            auditInfo.setContent("退货物品："+goodsInfo.getGname()+stockInfo.getSnum()+goodsInfo.getUnit());

        }

        if(n!=null){
            auditService.addAuditInfo(auditInfo,"loginuser","subuser","csuser,csuser1","audituser");
            json.put("msg","1");
        }else {
        json.put("msg", "0");
    }
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
        response.getWriter().write(json.toString());
    } catch (IOException e1) {
        e1.printStackTrace();
    }
        return null;
    }

    @RequestMapping(value="/addStock.do" ,method= RequestMethod.POST)
    @ResponseBody
    public String addStock(StockInfo stockInfo){
        String ckuser=request.getParameter("auditperson");//仓库审核
        JSONObject json=new JSONObject();
        User u = (User)session.getAttribute("userinfo");
        if(u==null){
            json.put("msg","2");//用户未登录
        }
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        stockInfo.setSperson(u.getUsername());
        stockInfo.setAuditstatus("01");
        Serializable n=stockService.addStock(stockInfo);
        GoodsInfo g = goodsService.queryGoodsById(stockInfo.getGid());
        AuditInfo auditInfo=new AuditInfo();
        auditInfo.setSid(stockInfo.getId());
        auditInfo.setAuditstatus("01");//审核中
        auditInfo.setUpdateauthor(u.getUsername());
        auditInfo.setUpdatetime(dateString);
        auditInfo.setAuditperson(ckuser);
        auditInfo.setSubPerson(u.getUsername());
        auditInfo.setCreatetime(dateString);
        String normal=stockInfo.getSthstate()=="01"?"故障":"正常";
        if(stockInfo.getStype().equals("01")){//21采购材料入库，22销货退货入库 ，23成品入库
            auditInfo.setTitle("采购入库");
            auditInfo.setAudittype("21");
            auditInfo.setContent("采购入库物品"+g.getGname()+stockInfo.getSnum()+g.getUnit());
        }else if(stockInfo.getStype().equals("02")){
            auditInfo.setTitle("销货退货入库");
            auditInfo.setAudittype("22");
            auditInfo.setContent("销货退货入库物品"+normal+g.getGname()+stockInfo.getSnum()+g.getUnit());
        }else if(stockInfo.getStype().equals("03")){
            auditInfo.setTitle("成品入库");
            auditInfo.setAudittype("23");
            auditInfo.setContent("成品入库物品"+normal+g.getGname()+stockInfo.getSnum()+g.getUnit());
        }else if(stockInfo.getStype().equals("04")){
            auditInfo.setTitle("领料退货入库");
            auditInfo.setAudittype("24");
            auditInfo.setContent("领料入库物品"+normal+g.getGname()+stockInfo.getSnum()+g.getUnit());
        }else if(stockInfo.getStype().equals("05")){
            auditInfo.setTitle("领用退货入库");
            auditInfo.setAudittype("25");
            auditInfo.setContent("领用退货入库物品"+normal+g.getGname()+stockInfo.getSnum()+g.getUnit());
        }

        auditService.addAuditInfo(auditInfo,u.getUsername(),u.getUsername(),ckuser,null);
        if(n!=null){
            json.put("msg","1");

        }else {
            json.put("msg", "0");
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            response.getWriter().write(json.toString());
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return null;

    }
    /*确认入库*/
    @RequestMapping(value="/sureInStock.do",method = RequestMethod.POST)
    public String sureInStock(String auditid) throws IOException{

        String agree=request.getParameter("agree");
        String type=request.getParameter("type");
        String auditstatus=request.getParameter("auditstatus");
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        User u = (User)session.getAttribute("userinfo");
        JSONObject json=new JSONObject();
        String sid="";
        AuditInfo auditInfo=auditService.getAuditByid(auditid);
        if(auditInfo!=null){
            sid=auditInfo.getSid();
        }
        StockInfo stockInfo= stockService.getStockById(sid);
        stockInfo.setAuditstatus("00");
        int i=stockService.updateStock(stockInfo);
        if(stockInfo!=null) {
            GoodsInfo g = goodsService.queryGoodsById(stockInfo.getGid());
            int snum = Integer.parseInt(stockInfo.getSnum());
            int storagenum = g.getStoragenum() == null ? 0 : g.getStoragenum();
            int fnum=g.getFaultnum() == null ? 0 : g.getStoragenum();
            if (stockInfo.getSopertype().equals("01")) {//入库
                g.setStoragenum(storagenum + snum);

            } else if (stockInfo.getSopertype().equals("02")) {//出库
                g.setStoragenum(storagenum - snum);
            }
            if (stockInfo.getSopertype().equals("01")) {//入库
                if (stockInfo.getStype().equals("02")) {//销货退货入库
                    XhContract xhContract= xhContractService.getXhContractById(stockInfo.getXhid());
                    int outednum=xhContract.getOutednum()==null?0:xhContract.getOutednum();
                    xhContract.setOutednum(outednum-snum);
                    xhContractService.updateXhContract(xhContract);
                    if (stockInfo.getSthstate() != null && stockInfo.getSthstate().equals("00")) {//物品状态故障
                        FaultInfo faultInfo = new FaultInfo();
                        faultInfo.setGid(stockInfo.getGid());
                        faultInfo.setFtype("01");
                        faultInfo.setFdate(dateString);
                        faultInfo.setFperson(u.getUsername());
                        faultInfo.setFnum(stockInfo.getSnum());
                        faultInfo.setXhid( stockInfo.getXhid());
                        g.setFaultnum(fnum+snum);
                        stockService.addFaultInfo(faultInfo);
                    }
                }

            }

            goodsService.updateGoods(g);
        }
        if (i>0){
            json.put("msg","1");
        }else{
            json.put("msg","0");
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }
    /*入库异常*/
    @RequestMapping(value="/errInStock.do",method = RequestMethod.POST)
    public String errInStock(String auditid) throws IOException{
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        User u = (User)session.getAttribute("userinfo");
        JSONObject json=new JSONObject();
        String sid="";
        AuditInfo auditInfo=auditService.getAuditByid(auditid);
        if(auditInfo!=null){
            sid=auditInfo.getSid();
        }
        StockInfo stockInfo= stockService.getStockById(sid);
        stockInfo.setAuditstatus("00");
        int i=stockService.updateStock(stockInfo);
        if(stockInfo!=null) {
            GoodsInfo g = goodsService.queryGoodsById(stockInfo.getGid());
            int snum = Integer.parseInt(stockInfo.getSnum());
            int storagenum = g.getStoragenum() == null ? 0 : g.getStoragenum();
            if (stockInfo.getSopertype().equals("01")) {//入库
                g.setStoragenum(storagenum + snum);

            } else if (stockInfo.getSopertype().equals("02")) {//出库
                g.setStoragenum(storagenum - snum);
            }
            if (stockInfo.getSopertype().equals("01")) {//入库
                if (stockInfo.getStype().equals("02")) {//销货退货入库
                    if (stockInfo.getSthstate() != null && stockInfo.getSthstate().equals("00")) {//物品状态故障
                        FaultInfo faultInfo = new FaultInfo();
                        faultInfo.setGid(stockInfo.getGid());
                        faultInfo.setFtype("01");
                        faultInfo.setFdate(dateString);
                        faultInfo.setFperson(u.getUsername());
                        faultInfo.setFnum(stockInfo.getSnum());
                        faultInfo.setXhid(stockInfo.getXhid());
                        stockService.addFaultInfo(faultInfo);
                    }
                }

            }

            goodsService.updateGoods(g);
        }
        if (i>0){
            json.put("msg","1");
        }else{
            json.put("msg","0");
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(json.toString());
        return null;
    }



    /*
    * 出库  退货、发货出库
    * */
    @RequestMapping(value="/outPutStock.do" ,method= RequestMethod.POST)
    @ResponseBody
    public String outPutStock(StockInfo stockInfo){
        User u = (User)session.getAttribute("userinfo");
        JSONObject json=new JSONObject();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);

        stockInfo=stockService.getStockById(stockInfo.getId());
        stockInfo.setAuditstatus("00");//发货出库完成

        stockInfo.setSperson(u.getUsername());
        int i=0;
         i= this.stockService.updateStock(stockInfo);


        if(i>0){
            if(stockInfo.getSopertype().equals("02") ){//出库
                if(stockInfo.getStype1().equals("02") ){//采购退货出库 退货物品为故障  增加修理操作
                    if(stockInfo.getSthstate().equals("00")) {
                        FaultInfo faultInfo = new FaultInfo();
                        faultInfo.setCid(stockInfo.getCgid());
                        faultInfo.setFnum(stockInfo.getSnum());
                        faultInfo.setFperson(u.getUsername());
                        faultInfo.setFdate(dateString);
                        faultInfo.setFtype("02");//修理
                        faultInfo.setGid(stockInfo.getGid());
                        stockService.addFaultInfo(faultInfo);

                        //退货出库  减少 故障数量 及 物品总数
                        GoodsInfo g = goodsService.queryGoodsById(stockInfo.getGid());
                        g.setMperson(u.getUsername());
                        int gfnum = g.getFaultnum();
                        int gstocknum = g.getStoragenum();
                        int snum = Integer.parseInt(stockInfo.getSnum());
                        g.setFaultnum(gfnum - snum);
                        g.setStoragenum(gstocknum - snum);
                        goodsService.updateGoods(g);
                    }else {
                        GoodsInfo g = goodsService.queryGoodsById(stockInfo.getGid());
                        g.setMperson(u.getUsername());
                        int gfnum = g.getFaultnum();
                        int gstocknum = g.getStoragenum();
                        int snum = Integer.parseInt(stockInfo.getSnum());
                        g.setStoragenum(gstocknum - snum);
                        goodsService.updateGoods(g);
                    }
                }
            }

            GoodsInfo goodsInfo=goodsService.queryGoodsById(stockInfo.getGid());
            int storagenum=goodsInfo.getStoragenum()==null?0:goodsInfo.getStoragenum();
            int snum=Integer.parseInt(stockInfo.getSnum());
            goodsInfo.setStoragenum(storagenum-snum);
            goodsService.updateGoods(goodsInfo);
                json.put("msg","1");
                response.setHeader("Access-Control-Allow-Origin", "*");
                try {
                    response.getWriter().write(json.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<StockInfo> uc = new ArrayList<>();
                uc.add(stockInfo);

        }else{
            json.put("msg","0");
        }
        return null;
    }



    @RequestMapping(value="/updateStock.do" ,method= RequestMethod.GET)
    public String updateStock(StockInfo stockInfo){

        return null;
    }
    /*
    * 查询物品库存
    * */
    @RequestMapping(value="/queryStockList.do" ,method= RequestMethod.GET)
    public String queryStockList(int page ,int pageSize){
        Map<String,Object> map=new HashMap<>();
        String opertype=request.getParameter("opertype");
        if(opertype!=null && !"".equals(opertype)){
            map.put("opertype",opertype);
        }
        PageBean p=stockService.queryStockList( page, pageSize,map);
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            response.getWriter().write(JSONHelper.bean2json(p));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return null;
    }


    /*
       * 查询物品出入库记录
       * */
    @RequestMapping(value="/queryStockOutinList.do" ,method= RequestMethod.GET)
    public String queryStockOutinList(int page ,int pageSize){
        Map<String,Object> map=new HashMap<>();
        String opertype=request.getParameter("sopertype");
        String findkey=request.getParameter("findkey");
        String begintime=request.getParameter("begintime");
        String endtime=request.getParameter("endtime");
        if(opertype!=null && !"".equals(opertype)){
            map.put("sopertype",opertype);
        } if(findkey!=null && !"".equals(findkey)){
            map.put("findkey",findkey);
        }
        if(begintime!=null && !"".equals(begintime)){
            map.put("begintime",begintime);
        }
        if(endtime!=null && !"".equals(endtime)){
            map.put("endtime",endtime);
        }
        PageBean p=stockService.queryStockOutinList(page,pageSize,map);
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            response.getWriter().write(JSONHelper.bean2json(p));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
    @RequestMapping(value="/exportStock.do" ,method= RequestMethod.GET)
    public String exportStock() throws IOException{
        Map<String,Object> parmas=new HashMap<>();
        request.setCharacterEncoding("utf-8");
        String findkey=request.getParameter("findkey");
        String flag=request.getParameter("downExcel");
        if(findkey!=null && !findkey.equals("")){
            findkey = new String(findkey.trim().getBytes("utf-8"), "utf-8");
            parmas.put("findkey",findkey);
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
            headList.add("历史出库数量");
            headList.add("历史入库数量");
            headList.add("库存数量");
            headList.add("正常数量");
            headList.add("故障数量");
            headList.add("审批数量");
            headList.add("物品id");
            fieldList.add("gid");
            fieldList.add("gname");
            fieldList.add("gtid");
            fieldList.add("gspec");
            fieldList.add("unit");
            fieldList.add("provider");
            fieldList.add("hisoutsum");
            fieldList.add("hisinsum");
            fieldList.add("nsum");
            fieldList.add("storagenum");
            fieldList.add("faultnum");
            fieldList.add("asum");
            fieldList.add("id");
            //隐藏列

            fileName = "物品库存表";
            System.err.println("----path-----"+request.getSession().getServletContext().getRealPath("/"));
            path = request.getSession().getServletContext().getRealPath("/")
                    + "/excel/CCGL-exportStock.xls";   //excel模板


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
            //先查询一下总数
            PageBean<Map<String,Object>> ps=this.stockService.queryStockByPage(1,10000,parmas);
            if(ps!=null&&ps.getTotalCount()>0){

                long pageSize = ps.getTotalPageBeans();
                list.addAll(ps.getResult());
                for(int page = 2;page<=pageSize;page++){
                    ps = this.stockService.queryStockByPage(page,10000, parmas);
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
    public String printStock(Map<String ,Object> stockInfo) throws IOException {
        User u = (User)session.getAttribute("userinfo");
        String fileName="";
        String path="";
        //XSSFWorkbook work = new XSSFWorkbook(in);
        Workbook work = null;
        Sheet sheet = null;
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d=new Date();
        String datestr=format.format(d);


        try {

        if(stockInfo.get("sopertype").equals("01")){//入库单打印
            fileName = "入库单";
            System.err.println("----path-----"+request.getSession().getServletContext().getRealPath("/"));
            path = request.getSession().getServletContext().getRealPath("/")
                    + "//excel//入库单.xlsx";   //excel模板
            InputStream in = new FileInputStream(new File(path));

            try {
                work = WorkbookFactory.create(new File(path));
                sheet = work.getSheetAt(0);
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
            //入库单打印
            Cell cell = sheet.getRow(2).getCell(10);
            cell.setCellValue(stockInfo.get("sid").toString()==null?"11111":stockInfo.get("sid").toString());//No 编号
            Cell cell2 = sheet.getRow(3).getCell(1);
            String stype=stockInfo.get("gtid").toString();
            if(stype!=null){
                if(stype.equals("01")){
                   stype="原材料（主料）" ;
                }else if(stype.equals("02")){
                    stype="原材料(辅料)";
                }else if(stype.equals("03")){
                    stype="成品";
                }
                cell2.setCellValue(stype);
            }
            Cell cell3 = sheet.getRow(3).getCell(5);
            cell3.setCellValue(stockInfo.get("stype").toString());
            Cell cell4 = sheet.getRow(5).getCell(1);//来源单位或部门
            cell4.setCellValue("单位或部门");
            Cell cell5= sheet.getRow(5).getCell(4);//入库日期
            cell5.setCellValue(datestr);
            Cell cell6= sheet.getRow(5).getCell(5);//备注
            cell6.setCellValue("");
            //物品名   规格  单位   数量   单价
            Cell cell7= sheet.getRow(8).getCell(0);
            Cell cell8= sheet.getRow(8).getCell(2);
            Cell cell11= sheet.getRow(8).getCell(4);
            Cell cell9= sheet.getRow(8).getCell(5);
            Cell cell10= sheet.getRow(8).getCell(6);
            cell7.setCellValue(stockInfo.get("gname").toString());
            cell8.setCellValue(stockInfo.get("gspec").toString());
            cell11.setCellValue(stockInfo.get("unit").toString());
            cell9.setCellValue(stockInfo.get("storagenum").toString());
           // cell10.setCellValue(stockInfo.get("price").toString());


            //操作人  制单人
            Cell cell12= sheet.getRow(14).getCell(6);

           if(stockInfo.get("sperson")!=null && !stockInfo.get("sperson").equals("")){
               String person= stockInfo.get("sperson").toString();
               cell12.setCellValue(person);
           }

            Cell cell13= sheet.getRow(14).getCell(11);
           User user=userService.getUserByUserName(u.getUsername());
            cell13.setCellValue(user.getUname());
        }else if(stockInfo.get("sopertype").equals("02")){

            if(stockInfo.get("stype1").equals("03")){//领料单打印
                fileName = "领料单";
                System.err.println("----path-----"+request.getSession().getServletContext().getRealPath("/"));
                path = request.getSession().getServletContext().getRealPath("/")
                        + "//excel//领料单.xlsx";   //excel模板
                InputStream in = new FileInputStream(new File(path));

                try {
                    work = WorkbookFactory.create(new File(path));
                    sheet = work.getSheetAt(0);
                } catch (InvalidFormatException e) {
                    e.printStackTrace();
                }
                //领料单打印
            Cell cell = sheet.getRow(2).getCell(10);
            cell.setCellValue("LL000001");//No 编号
            Cell cell2 = sheet.getRow(3).getCell(1);
            cell2.setCellValue("领用用途");
            Cell cell3 = sheet.getRow(3).getCell(5);
            cell3.setCellValue(datestr);
            //物品名   规格  单位   数量   单价
            Cell cell7= sheet.getRow(7).getCell(0);
            Cell cell8= sheet.getRow(7).getCell(2);
            Cell cell11= sheet.getRow(7).getCell(4);
            Cell cell9= sheet.getRow(7).getCell(5);
            Cell cell10= sheet.getRow(7).getCell(6);
            cell7.setCellValue(stockInfo.get("gname").toString());
            cell8.setCellValue(stockInfo.get("gspec").toString());
            cell11.setCellValue(stockInfo.get("unit").toString());
            cell9.setCellValue(stockInfo.get("storagenum").toString());
            //cell10.setCellValue(stockInfo.get("price").toString());
            //操作人  制单人
            Cell cell12= sheet.getRow(14).getCell(6);
            cell12.setCellValue(stockInfo.get("sperson").toString());
            Cell cell13= sheet.getRow(14).getCell(11);
                User user=userService.getUserByUserName(u.getUsername());
                cell13.setCellValue(user.getUname());
            }else if(stockInfo.get("stype1").equals("04")){
                fileName = "领用单";
                System.err.println("----path-----"+request.getSession().getServletContext().getRealPath("/"));
                path = request.getSession().getServletContext().getRealPath("/")
                        + "//excel//领用单.xlsx";   //excel模板
                InputStream in = new FileInputStream(new File(path));

                try {
                    work = WorkbookFactory.create(new File(path));
                    sheet = work.getSheetAt(0);
                } catch (InvalidFormatException e) {
                    e.printStackTrace();
                }
                //领用单打印
            Cell cell = sheet.getRow(2).getCell(10);
            cell.setCellValue("LY000001");//No 编号
            Cell cell2 = sheet.getRow(3).getCell(1);
            cell2.setCellValue("领用部门");
            Cell cell3 = sheet.getRow(3).getCell(5);
            cell3.setCellValue(datestr);
            //物品名   规格  单位   数量   单价
            Cell cell7= sheet.getRow(7).getCell(0);
            Cell cell8= sheet.getRow(7).getCell(2);
            Cell cell11= sheet.getRow(7).getCell(4);
            Cell cell9= sheet.getRow(7).getCell(5);
            Cell cell10= sheet.getRow(7).getCell(6);
                cell7.setCellValue(stockInfo.get("gname").toString());
                cell8.setCellValue(stockInfo.get("gspec").toString());
                cell11.setCellValue(stockInfo.get("unit").toString());
                cell9.setCellValue(stockInfo.get("storagenum").toString());
               // cell10.setCellValue(stockInfo.get("price").toString());
            //操作人  制单人
            Cell cell12= sheet.getRow(14).getCell(6);
            cell12.setCellValue("操作人");
            Cell cell13= sheet.getRow(14).getCell(11);
            cell13.setCellValue("制单人");
            }else if(stockInfo.get("stype1").equals("01")){
                fileName = "出货单";
                System.err.println("----path-----"+request.getSession().getServletContext().getRealPath("/"));
                path = request.getSession().getServletContext().getRealPath("/")
                        + "//excel//发货单.xlsx";   //excel模板
                InputStream in = new FileInputStream(new File(path));

                try {
                    work = WorkbookFactory.create(new File(path));
                    sheet = work.getSheetAt(0);
                } catch (InvalidFormatException e) {
                    e.printStackTrace();
                }
                //出货单打印（发货出库）
                Cell cell = sheet.getRow(2).getCell(10);
                cell.setCellValue("CH000001");//No 编号
                Cell cell2 = sheet.getRow(3).getCell(1);
                cell2.setCellValue("收货地址");
                Cell cell3 = sheet.getRow(3).getCell(5);
                cell3.setCellValue(datestr);
                Cell cell4 = sheet.getRow(4).getCell(5);
                cell4.setCellValue("电话");
                //物品名   规格  单位   数量   单价
                Cell cell7= sheet.getRow(8).getCell(0);
                Cell cell8= sheet.getRow(8).getCell(2);
                Cell cell11= sheet.getRow(8).getCell(4);
                Cell cell9= sheet.getRow(8).getCell(5);
                Cell cell10= sheet.getRow(8).getCell(6);
                cell7.setCellValue(stockInfo.get("gname").toString());
                cell8.setCellValue(stockInfo.get("gspec").toString());
                cell11.setCellValue(stockInfo.get("unit").toString());
                cell9.setCellValue(stockInfo.get("storagenum").toString());
                //cell10.setCellValue(stockInfo.get("price").toString());
                //操作人  制单人
                Cell cell12= sheet.getRow(14).getCell(6);
                cell12.setCellValue("操作人");
                Cell cell13= sheet.getRow(14).getCell(11);
                cell13.setCellValue("制单人");
            }
        }


        }catch (FileNotFoundException e) {
            System.out.println("文件路径错误");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("文件输入流错误");
            e.printStackTrace();
        }

        OutputStream os = response.getOutputStream();// 取得输出流
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition",
                "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
        work.write(os);
        os.close();
        return null;
    }

    /*
    * 单据导出
    * */
    @RequestMapping(value="exportOutinStock.do",method = RequestMethod.GET)
    public String exportOutinStock(String ids) throws IOException {

        request.setCharacterEncoding("utf-8");
        Map<String,Object> params=new HashMap<>();
        String findkey=request.getParameter("findkey");
        if(findkey!=null && !findkey.equals("")){
            findkey = new String(findkey.trim().getBytes("utf-8"), "utf-8");
            params.put("findkey",findkey);
        }
        List<Map<String ,Object>> list= stockService.exportOutinStock(ids);
        String sopertype=request.getParameter("sopertype");
        if(list.size()>0){
           for(Map m:list){
               printStock(m);
           }
        }

        return null;
    }


    @RequestMapping(value="getStockList.do",method = RequestMethod.GET)
    public String getStockList(int page,int pageSize)throws UnsupportedEncodingException{
        Map<String,Object> params=new HashMap<>();
        String findkey =request.getParameter("findkey");
        String begintime=request.getParameter("begintime");
        String endtime=request.getParameter("endtime");
        String sopertype=request.getParameter("sopertype");
        String sno=request.getParameter("sno");
        String emcflag=request.getParameter("emcflag");
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
        if(sopertype!=null && !sopertype.equals("")){
            params.put("sopertype",sopertype);
        } if(sno!=null && !sno.equals("")){
            params.put("sno",sno);
        }if(emcflag!=null && !"".equals(emcflag)){
            params.put("emcflag",emcflag);
        }
        PageBean<Map<String,Object>> pageBean=stockService.getStockList(page,pageSize,params);

        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(JSONHelper.bean2json(pageBean));
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/getStockGoodsList.do", method = RequestMethod.GET)
    public String getStockGoodsList(int page, int pageSize)throws UnsupportedEncodingException{
        Map<String,Object> params=new HashMap<>();
        request.setCharacterEncoding("utf-8");
        String findkey=request.getParameter("findkey");
        String gid=request.getParameter("gid");
        String gtid=request.getParameter("gtid");

        if(findkey!=null && !findkey.equals("")){
            findkey = new String(findkey.trim().getBytes("UTF-8"),"UTF-8");
            params.put("findkey",findkey);
        }if(gid!=null && !"".equals(gid)){
            params.put("gid",gid);
        }if(gtid!=null && !"".equals(gtid)){
            params.put("gtid",gtid);
        }
        PageBean<Map<String,Object>> pageBean=stockService.queryStockByPage(page,pageSize,params);
        JSONObject json=new JSONObject();
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(JSONHelper.bean2json(pageBean));
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    /*
    查询物品修理和故障记录

     */
    @RequestMapping(value="/getFlautInfo.do", method = RequestMethod.GET)
    public String getFlautInfo(int page,int pageSize) throws UnsupportedEncodingException{
        Map<String,Object> params=new HashMap<>();
        request.setCharacterEncoding("utf-8");
        String findkey=request.getParameter("findkey");
        String begintime=request.getParameter("begintime");
        String endtime=request.getParameter("endtime");
        if(findkey!=null && !findkey.equals("")){
            findkey = new String(findkey.trim().getBytes("utf-8"), "utf-8");
            params.put("findkey",findkey);
        }if(begintime!=null && !begintime.equals("")){
            params.put("begintime",begintime);
        }
        if(endtime!=null && !endtime.equals("")){
            params.put("endtime",endtime);
        }
        PageBean<Map<String,Object>> pageBean=stockService.getFlautInfo(page,pageSize,params);
        JSONObject json=new JSONObject();
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(JSONHelper.bean2json(pageBean));
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





    @RequestMapping(value = "/addFaultInfo.do",method=RequestMethod.GET)
    @ResponseBody
    public String addFaultInfo(FaultInfo faultInfo){
        JSONObject json=new JSONObject();
        User u=(User) session.getAttribute("userinfo");
        faultInfo.setFperson(u.getUsername());
        faultInfo.setFid(this.stockService.getNextFid(faultInfo.getGid()));
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        faultInfo.setFdate(dateString);
        int n=this.stockService.addFaultInfo(faultInfo);
        GoodsInfo goodsInfo=goodsService.queryGoodsById(faultInfo.getGid());
        if(n==0){
            json.put("msg","0");
        }
        else{
            int storagenum=goodsInfo.getStoragenum()==null?0:goodsInfo.getStoragenum();
            int faultnum=goodsInfo.getFaultnum()==null?0:goodsInfo.getFaultnum();
            if(faultInfo.getFtype().equals("01")){//报故障
               // goodsInfo.setStoragenum(storagenum-Integer.parseInt(faultInfo.getFnum()));
                goodsInfo.setFaultnum(faultnum+Integer.parseInt(faultInfo.getFnum()));
                goodsService.updateGoods(goodsInfo);
            }else{//修理
                //goodsInfo.setStoragenum(goodsInfo.getStoragenum()+Integer.parseInt(faultInfo.getFnum()));
                goodsInfo.setFaultnum(goodsInfo.getFaultnum()-Integer.parseInt(faultInfo.getFnum()));
                goodsService.updateGoods(goodsInfo);
            }
            json.put("msg","1");

            response.setHeader("Access-Control-Allow-Origin", "*");
            try {
                response.getWriter().write(json.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
           // List<FaultInfo> uc = new ArrayList<>();
          //  uc.add(faultInfo);
          //  GoodsInfo goodsInfo=this.goodsService.queryGoodsById(faultInfo.getGid());
           // this.logsService.addLogs(new Location(), new Logs().setLogs(userDetails.getUsername(), LogContant.hqModel,LogContant.addFault(goodsInfo.getGname()), JSONHelper.toJSONString(uc),LogContant.ADD));

        }

        return null;
    }


    @RequestMapping(value = "/setHqWarning.do",method=RequestMethod.POST)
    @ResponseBody
    public String setHqWarning(String jsonData){
        JSONObject jsonObject=new JSONObject();
        List<Map<String,Object>> list=JSONHelper.parseJSON2List(jsonData);
        int c=0;
        if(list.size()>0){
            for(int i=0;i<list.size();i++){
                int n=this.stockService.setHqWarning(list.get(i).get("id").toString(),Integer.parseInt(list.get(i).get("swnum")==null ||list.get(i).get("swnum").equals("")?"0":list.get(i).get("swnum").toString()));
                if(n<=0){
                    jsonObject.put("msg","0");
                }else{
                    c++;
                }
            }if(c==list.size()){
                jsonObject.put("msg","1");
                // GoodsInfo goodsInfo=this.goodsService.queryGoodsById(faultInfo.getGid());
              //  this.logsService.addLogs(new Location(), new Logs().setLogs(userDetails.getUsername(), LogContant.hqModel,LogContant.setWarn(), JSONHelper.toJSONString(list),LogContant.UPDATE));

            }else if(c<list.size()){
                jsonObject.put("msg","2");
            }else{
                jsonObject.put("msg","0");
            }
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            response.getWriter().write(JSONHelper.bean2json(jsonObject));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}

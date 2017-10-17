import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by LvLiangFeng on 2016/11/21.
 */
public class getControllerSB {
    public static String getController(String name,String caname) {
        Calendar dayc1 = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        StringBuffer sb = new StringBuffer("");
        sb.append("package com.wlg.Controller;\n");
        sb.append("import com.wlg.Model."+name+";\n");
        sb.append("import com.wlg.Service."+name+"Service;\n");
        sb.append("import net.sf.json.JSONObject;\n");
        sb.append("import org.springframework.stereotype.Controller;\n");
        sb.append("import org.apache.log4j.Logger;\n");
        sb.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
        sb.append("import org.springframework.context.annotation.Scope;\n");
        sb.append("import java.util.List;\n");
        sb.append("import com.wlg.Util.JSONHelper;\n");
        sb.append("import org.springframework.web.bind.annotation.RequestMethod;;\n");
        sb.append("import org.springframework.web.bind.annotation.ResponseBody;\n");
        sb.append("import java.io.IOException;\n");
        sb.append("import com.wlg.Model.PageBean;\n");
        sb.append("import com.wlg.Util.JSONHelper;\n");
        sb.append("import javax.annotation.Resource;\n\n");

        sb.append("/**\n");
        sb.append(" * Created by LvLiangFeng on "+df.format(dayc1.getTime())+".\n");
        sb.append(" */\n\n");

        sb.append("@Controller\n");
        sb.append("@Scope(\"prototype\")\n");
        sb.append("@RequestMapping(value=\""+caname+"\")\n");
        sb.append("public class "+name+"Controller extends BaseController{\n");
        sb.append("    @Resource\n");
        sb.append("    private "+name+"Service "+caname+"Service;\n\n");

        sb.append("    /**\n");
        sb.append("    * 初始化Log4j的一个实例\n");
        sb.append("    */\n");
        sb.append("    private static final Logger logger = Logger\n");
        sb.append("        .getLogger("+name+"Controller.class);\n\n");

        sb.append("    @RequestMapping(value=\"/add_"+name+".do\",method = RequestMethod.POST)\n");
        sb.append("    @ResponseBody\n");
        sb.append("    public String add_"+name+"("+name+" "+caname+") throws IOException {\n");
        sb.append("        JSONObject json = new JSONObject();\n");
        sb.append("        int i = 0;\n");
        sb.append("        try{\n");
        sb.append("            i = this."+caname+"Service.save"+name+"("+caname+");\n");
        sb.append("        }catch (RuntimeException e){\n");
        sb.append("            logger.info(\"******保存异常******：\"+e);\n");
        sb.append("        }\n");
        sb.append("        json.put(\"status\",i);\n");
        sb.append("        response.setHeader(\"Access-Control-Allow-Origin\", \"*\");\n");
        sb.append("        response.getWriter().write(json.toString());\n");
        sb.append("        return null;\n");
        sb.append("    }\n\n");

        sb.append("    @RequestMapping(value=\"/delete_"+name+".do\",method = RequestMethod.POST)\n");
        sb.append("    @ResponseBody\n");
        sb.append("    public String delete_"+name+"("+name+" "+caname+") throws IOException {\n");
        sb.append("        JSONObject json = new JSONObject();\n");
        sb.append("        int i = 0;\n");
        sb.append("        try{\n");
        sb.append("            i = this."+caname+"Service.delete"+name+"("+caname+");\n");
        sb.append("        }catch (RuntimeException e){\n");
        sb.append("            logger.info(\"******删除异常******：\"+e);\n");
        sb.append("        }\n");
        sb.append("        json.put(\"status\",i);\n");
        sb.append("        response.setHeader(\"Access-Control-Allow-Origin\", \"*\");\n");
        sb.append("        response.getWriter().write(json.toString());\n");
        sb.append("        return null;\n");
        sb.append("    }\n\n");

        sb.append("    @RequestMapping(value=\"/update_"+name+".do\",method = RequestMethod.POST)\n");
        sb.append("    @ResponseBody\n");
        sb.append("    public String update_"+name+"("+name+" "+caname+") throws IOException {\n");
        sb.append("        JSONObject json = new JSONObject();\n");
        sb.append("        int i = 0;\n");
        sb.append("        try{\n");
        sb.append("            i = this."+caname+"Service.update"+name+"("+caname+");\n");
        sb.append("        }catch (RuntimeException e){\n");
        sb.append("            logger.info(\"******更新异常******：\"+e);\n");
        sb.append("        }\n");
        sb.append("        json.put(\"status\",i);\n");
        sb.append("        response.setHeader(\"Access-Control-Allow-Origin\", \"*\");\n");
        sb.append("        response.getWriter().write(json.toString());\n");
        sb.append("        return null;\n");
        sb.append("    }\n\n");

        sb.append("    @RequestMapping(value=\"/check_"+name+"List.do\",method = RequestMethod.GET)\n");
        sb.append("    @ResponseBody\n");
        sb.append("    public String check_"+name+"List("+name+" "+caname+") throws IOException {\n");
        sb.append("        JSONObject json = new JSONObject();\n");
        sb.append("        List<"+name+"> s = this."+caname+"Service.get"+name+"List("+caname+");\n");
        sb.append("        response.setHeader(\"Access-Control-Allow-Origin\", \"*\");\n");
        sb.append("        response.getWriter().write(JSONHelper.toJSONStringDateOfCustom(s));\n");
        sb.append("        return null;\n");
        sb.append("    }\n\n");

        sb.append("    @RequestMapping(value=\"/check_"+name+"ForPageBean.do\",method = RequestMethod.GET)\n");
        sb.append("    @ResponseBody\n");
        sb.append("    public String check_"+name+"ForPageBean(Integer pageno,"+name+" "+caname+") throws IOException {\n");
        sb.append("        PageBean PageBean = this."+caname+"Service.sreach"+name+"ForPageBean(pageBean,"+caname+");\n");
        sb.append("        response.setHeader(\"Access-Control-Allow-Origin\", \"*\");\n");
        sb.append("        response.getWriter().write(JSONHelper.bean2json(pageBean));\n");
        sb.append("        return null;\n");
        sb.append("    }\n");
        sb.append("}\n\n");


        return sb.toString();
    }
}

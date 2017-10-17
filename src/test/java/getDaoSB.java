import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by LvLiangFeng on 2016/11/21.
 */
public class getDaoSB {
    public static String getDao(String name,String caname){
        Calendar dayc1 = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        StringBuffer sb = new StringBuffer("");
        sb.append("package com.wlg.Dao;\n\n");
        sb.append("import com.wlg.Model."+name+";\n");
        sb.append("import java.io.Serializable;\n\n");

        sb.append("/**\n");
        sb.append(" * Created by LvLiangFeng on "+df.format(dayc1.getTime())+".\n");
        sb.append(" */\n\n");

        sb.append("public interface "+name+"Dao {\n");
        sb.append("    <T> Serializable save"+name+"("+name+" "+caname+");\n\n");
        sb.append("    <T> void update"+name+"("+name+" "+caname+");\n\n");
        sb.append("    <T> void delete"+name+"("+name+" "+caname+");\n\n");
        sb.append("}\n");

        return sb.toString();
    }

    public static String getDaoImpl(String name,String caname){
        Calendar dayc1 = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        StringBuffer sb = new StringBuffer("");
        sb.append("package com.wlg.Dao.Impl;\n\n");
        sb.append("import com.wlg.Dao."+name+"Dao;\n");
        sb.append("import com.wlg.Model."+name+";\n");
        sb.append("import com.wlg.Util.HiSession;\n");
        sb.append("import org.apache.log4j.Logger;\n");
        sb.append("import org.springframework.stereotype.Repository;\n");
        sb.append("import javax.annotation.Resource;\n");
        sb.append("import java.io.Serializable;\n\n");
        sb.append("/**\n");
        sb.append(" * Created by LvLiangFeng on "+df.format(dayc1.getTime())+".\n");
        sb.append(" */\n\n");
        sb.append("@Repository\n");
        sb.append("public class "+name+"DaoImpl implements "+name+"Dao{\n");
        sb.append("    @Resource\n");
        sb.append("    private HiSession hiSession;\n\n");

        sb.append("    /**\n");
        sb.append("    * 初始化Log4j的一个实例\n");
        sb.append("    */\n");
        sb.append("    private static final Logger logger = Logger\n");
        sb.append("        .getLogger("+name+"DaoImpl.class);\n\n");

        sb.append("    @Override\n");
        sb.append("    public <T> Serializable save"+name+"("+name+" "+caname+") {\n");
        sb.append("        try {\n");
        sb.append("            Serializable id = this.hiSession.getSession().save("+caname+");\n");
        sb.append("            this.hiSession.getSession().flush();\n");
        sb.append("            if (logger.isDebugEnabled()) {\n");
        sb.append("                logger.debug(\"保存实体成功,\" + "+caname+".getClass().getName());\n");
        sb.append("            }\n");
        sb.append("             return id;\n");
        sb.append("        } catch (RuntimeException e) {\n");
        sb.append("            logger.error(\"保存实体异常\", e);\n");
        sb.append("            throw e;\n");
        sb.append("        }\n");
        sb.append("   }\n\n");

        sb.append("    @Override\n");
        sb.append("    public <T> void update"+name+"("+name+" "+caname+") {\n");
        sb.append("        this.hiSession.getSession().update("+caname+");\n");
        sb.append("        this.hiSession.getSession().flush();\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public <T> void delete"+name+"("+name+" "+caname+") {\n");
        sb.append("        try {\n");
        sb.append("            this.hiSession.getSession().delete("+caname+");\n");
        sb.append("            this.hiSession.getSession().flush();\n");
        sb.append("            if (logger.isDebugEnabled()) {\n");
        sb.append("                logger.debug(\"删除成功,\" + "+caname+".getClass().getName());\n");
        sb.append("            }\n");
        sb.append("        } catch (RuntimeException e) {\n");
        sb.append("            logger.error(\"删除异常\", e);\n");
        sb.append("            throw e;\n");
        sb.append("        }\n");
        sb.append("    }\n");
        sb.append("}\n\n");


        return sb.toString();
    }


}

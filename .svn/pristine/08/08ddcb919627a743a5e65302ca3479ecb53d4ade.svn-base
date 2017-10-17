import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by LvLiangFeng on 2016/11/21.
 */
public class getServiceSB {
    public static String getService(String name,String caname){
        Calendar dayc1 = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        StringBuffer sb = new StringBuffer("");
        sb.append("package com.wlg.Service;\n\n");
        sb.append("import com.wlg.Model.PageBean;\n");
        sb.append("import java.util.List;\n");
        sb.append("import com.wlg.Model."+name+";\n\n");

        sb.append("/**\n");
        sb.append(" * Created by LvLiangFeng on "+df.format(dayc1.getTime())+".\n");
        sb.append(" */\n\n");

        sb.append("public interface "+name+"Service {\n");

        sb.append("    /**\n");
        sb.append("     * 保存"+name+"实体\n");
        sb.append("     * @param "+caname+"\n");
        sb.append("     * @return\n");
        sb.append("     */\n");
        sb.append("    int save"+name+"("+name+" "+caname+");\n\n");

        sb.append("    /**\n");
        sb.append("     * 更新"+name+"实体\n");
        sb.append("     * @param "+caname+"\n");
        sb.append("     * @return\n");
        sb.append("     */\n");
        sb.append("    int update"+name+"("+name+" "+caname+");\n\n");

        sb.append("    /**\n");
        sb.append("     * 删除"+name+"实体\n");
        sb.append("     * @param "+caname+"\n");
        sb.append("     * @return\n");
        sb.append("     */\n");
        sb.append("    int delete"+name+"("+name+" "+caname+");\n\n");

        sb.append("    /**\n");
        sb.append("     * 根据实体类对象查找所有记录\n");
        sb.append("     * @param "+caname+"\n");
        sb.append("     * @return\n");
        sb.append("     */\n");
        sb.append("    List<"+name+"> get"+name+"List("+name+" "+caname+");\n\n");

        sb.append("    /**\n");
        sb.append("     * 分页查询"+name+"列表\n");
        sb.append("     * @param PageBean 页数\n");
        sb.append("     * @param "+caname+" 实体对象\n");
        sb.append("     * @return PageBean\n");
        sb.append("     */\n");
        sb.append("    PageBean sreach"+name+"ForPageBean(Integer pageno, "+name+" "+caname+");\n\n");
        sb.append("}");
        return sb.toString();
    }

    public static String getServiceImpl(String name,String caname) {
        Calendar dayc1 = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        StringBuffer sb = new StringBuffer("");
        sb.append("package com.wlg.Service.Impl;\n\n");

        sb.append("import com.wlg.Dao.BaseDao;\n");
        sb.append("import com.wlg.Dao."+name+"Dao;\n");
        sb.append("import com.wlg.Model.PageBean;\n");
        sb.append("import java.util.List;\n");
        sb.append("import com.wlg.Util.HqlParamUtil;\n");
        sb.append("import com.wlg.Model."+name+";\n");
        sb.append("import com.wlg.Service.MemberService;\n");
        sb.append("import com.wlg.Service."+name+"Service;\n");
        sb.append("import org.apache.log4j.Logger;\n");
        sb.append("import org.springframework.stereotype.Service;\n");
        sb.append("import org.springframework.transaction.annotation.Propagation;\n");
        sb.append("import org.springframework.transaction.annotation.Transactional;\n");
        sb.append("import javax.annotation.Resource;\n\n");

        sb.append("/**\n");
        sb.append(" * Created by LvLiangFeng on "+df.format(dayc1.getTime())+".\n");
        sb.append(" */\n\n");

        sb.append("@Service\n");
        sb.append("public class "+name+"ServiceImpl implements "+name+"Service{\n");
        sb.append("    @Resource\n");
        sb.append("    private "+name+"Dao "+caname+"Dao;\n");
        sb.append("    @Resource\n");
        sb.append("    private BaseDao baseDao;\n");
        sb.append("    @Resource\n");
        sb.append("    private MemberService memberService;\n\n");

        sb.append("    /**\n");
        sb.append("    * 初始化Log4j的一个实例\n");
        sb.append("    */\n");
        sb.append("    private static final Logger logger = Logger\n");
        sb.append("        .getLogger("+name+"ServiceImpl.class);\n\n");

        sb.append("    @Override\n");
        sb.append("    @Transactional(propagation=Propagation.REQUIRED)\n");
        sb.append("    public int save"+name+"("+name+" "+caname+") {\n");
        sb.append("        this."+caname+"Dao.save"+name+"("+caname+");\n");
        sb.append("        return 1;\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    @Transactional(propagation=Propagation.REQUIRED)\n");
        sb.append("    public int update"+name+"("+name+" "+caname+") {\n");
        sb.append("        this."+caname+"Dao.update"+name+"("+caname+");\n");
        sb.append("        return 1;\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    @Transactional(propagation=Propagation.REQUIRED)\n");
        sb.append("    public int delete"+name+"("+name+" "+caname+") {\n");
        sb.append("        "+name+" "+caname.substring(0,1)+" = this.baseDao.findUniqueByID("+name+".class,"+caname+".getId());\n");
        sb.append("        if("+caname.substring(0,1)+"==null)return 2;\n");
        sb.append("        this."+caname+"Dao.delete"+name+"("+caname.substring(0,1)+");\n");
        sb.append("        return 1;\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)\n");
        sb.append("    public PageBean sreach"+name+"ForPageBean(Integer pageno, "+name+" "+caname+") {\n");
        sb.append("        if(pageBean==null)PageBean=1;\n");
        sb.append("        return memberService.queryForPageBean(10,PageBean,\"FROM "+name+" WHERE 1=1\","+caname+",null);\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly=true)\n");
        sb.append("    public List<"+name+"> get"+name+"List("+name+" "+caname+") {\n");
        sb.append("        return this.baseDao.findByQueryString(\"FROM "+name+" WHERE 1=1 \"+ HqlParamUtil.getFieldValue("+caname+"));\n");
        sb.append("    }\n");
        sb.append("}\n\n");

        return sb.toString();
    }
}

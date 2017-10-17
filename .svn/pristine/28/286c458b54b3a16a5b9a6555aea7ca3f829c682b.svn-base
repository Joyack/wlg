package com.wlg.Interceptor;

import com.wlg.Model.Resources;
import com.wlg.Service.PermissionRoleService;
import com.wlg.Service.ResourcesRoleService;
import org.apache.shiro.config.Ini;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lvliagnfeng on 2016/11/19.
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {



    private String filterChainDefinitions;

    @Resource
    private ResourcesRoleService resourcesRoleService;
    @Resource
    PermissionRoleService permissionRoleService;

    /**
     * 默认premission字符串
     */
    public static final String PREMISSION_STRING="perms[\"{0}\"]";

    public Ini.Section getObject() throws BeansException {

        //获取所有Resource
        List<Resources> list = null;

        Ini ini = new Ini();
        //加载默认的url
        ini.load(filterChainDefinitions);

        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        //循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,
        //里面的键就是链接URL,值就是存在什么条件才能访问该链接
//        for (Iterator<Resources> it = list.iterator(); it.hasNext();) {
//
//            Resources resource = it.next();
//            //如果不为空值添加到section中
//            if(StringUtils.isNotEmpty(resource.getValue()) && StringUtils.isNotEmpty(resource.getPermission())) {
//                section.put(resource.getValue(),  MessageFormat.format(PREMISSION_STRING,resource.getPermission()));
//            }
//
//        }

//        section.put("/PageBean/home.jsp*",MessageFormat.format(PREMISSION_STRING,"admin2"));
//        section.put("/*/home.jsp*",MessageFormat.format(PREMISSION_STRING,"admin2"));
//        section.put("/main.jsp*",MessageFormat.format(PREMISSION_STRING,"admin2"));
//        section.put("/main.jsp",MessageFormat.format(PREMISSION_STRING,"admin2"));

        return section;
    }

    /**
     * 通过filterChainDefinitions对默认的url过滤定义  不用上面就改下面
     *
     * @param filterChainDefinitions 默认的url过滤定义
     */
    public void setFilterChainDefinitions(String filterChainDefinitions) {
        StringBuffer fiter = new StringBuffer("");
        fiter.append(DefaultMetaSource.getDefaultMetaSourceForAnon());
//        fiter.append(this.resourcesRoleService.getFilterChainDefinitionsForRole());
        fiter.append("\n");
//        fiter.append(this.permissionRoleService.getFilterChainDefinitionsForPermission());
        fiter.append("\n");
        fiter.append(DefaultMetaSource.getDefaultMetaSourceForJspAction());
        System.out.println(fiter.toString());
//        System.out.println("========================="+filterChainDefinitions+fiter.toString());
        this.filterChainDefinitions = filterChainDefinitions+fiter.toString();
    }

    public Class<?> getObjectType() {
        return this.getClass();
    }

    public boolean isSingleton() {
        return false;
    }

}

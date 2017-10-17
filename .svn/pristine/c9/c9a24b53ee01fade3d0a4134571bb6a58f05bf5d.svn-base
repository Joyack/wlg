package com.wlg.Interceptor.Impl;

import com.wlg.Interceptor.DefaultMetaSource;
import com.wlg.Interceptor.CustomAuthorizationService;
import com.wlg.Service.PermissionRoleService;
import com.wlg.Service.ResourcesRoleService;
import org.apache.log4j.Logger;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

/**
 * Created by lvliagnfeng on 2016/11/19.
 */
@Service
public class CustomAuthorizationServiceImpl implements CustomAuthorizationService {

        private static final Logger log= Logger.getLogger(CustomAuthorizationServiceImpl.class);

        @Resource
        private ShiroFilterFactoryBean shiroFilterFactoryBean;

        @Resource
        private ResourcesRoleService resourcesRoleService;
        @Resource
        PermissionRoleService permissionRoleService;


        @Override
        public String loadFilterChainDefinitions() {
                StringBuffer sb = new StringBuffer("");
                sb.append(DefaultMetaSource.getDefaultMetaSourceForAnon());
                sb.append(getRoleFilter());
                sb.append("\n");
                sb.append(getPermsFilter());
                sb.append("\n");
                sb.append(DefaultMetaSource.getDefaultMetaSourceForJspAction());
                System.out.println(sb.toString());
                return sb.toString();
        }


        //根据用户查询角色与资源对应关系
        public String getRoleFilter(){
                StringBuffer fiter = new StringBuffer("");
                fiter.append(this.resourcesRoleService.getFilterChainDefinitionsForRole());
                return fiter.toString();
        }

        //根据用户查询权限与用户对应关系
        public String getPermsFilter(){
                StringBuffer fiter = new StringBuffer("");
                fiter.append(this.permissionRoleService.getFilterChainDefinitionsForPermission());
                return fiter.toString();
        }

        @Override
        //此方法加同步锁
        public synchronized void reCreateFilterChains() {

                AbstractShiroFilter shiroFilter = null;
                try{
                        shiroFilter = (AbstractShiroFilter)shiroFilterFactoryBean.getObject();
                } catch(Exception e) {
                        log.error("getShiroFilter from shiroFilterFactoryBean error!", e);
                        throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
                }

                PathMatchingFilterChainResolver filterChainResolver =(PathMatchingFilterChainResolver)shiroFilter.getFilterChainResolver();
                DefaultFilterChainManager manager =(DefaultFilterChainManager)filterChainResolver.getFilterChainManager();

                //清空老的权限控制
                manager.getFilterChains().clear();
                shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
                shiroFilterFactoryBean.setFilterChainDefinitions(loadFilterChainDefinitions());
                //重新构建生成
                Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
                for(Map.Entry<String, String> entry :chains.entrySet()) {
                        String url = entry.getKey();
                        String chainDefinition =entry.getValue().trim().replace(" ", "");
                        manager.createChain(url,chainDefinition);
                }

        }

}
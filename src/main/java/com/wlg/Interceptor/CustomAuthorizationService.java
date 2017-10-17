package com.wlg.Interceptor;

/**
 * 权限管理相关方法
 * @author lvliagnfeng
 *
 */
public interface CustomAuthorizationService {
        /**
         * 加载过滤配置信息
         * @return
         */
        public String loadFilterChainDefinitions();

        /**
         * 重新构建权限过滤器
         *
         */
        public void reCreateFilterChains();

    }

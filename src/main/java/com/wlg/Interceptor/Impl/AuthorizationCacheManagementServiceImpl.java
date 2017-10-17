package com.wlg.Interceptor.Impl;

import com.wlg.Interceptor.AuthorizationCacheManagementService;
import com.wlg.Interceptor.CustomMonitorRealm;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by LvLiangFeng on 2016/11/28.
 */
@Service
public class AuthorizationCacheManagementServiceImpl implements AuthorizationCacheManagementService {
    @Resource
    private CacheManager cacheManager;

    @Override
    public void removeAuthorizationCache(String cacheKey){
        Cache<Object, Object> authorizationCache = cacheManager.getCache(CustomMonitorRealm.class.getName()+".authorizationCache");
        if (authorizationCache != null) {
            for (Object key : authorizationCache.keys()) {
                if(key.toString().equals(cacheKey))
                    authorizationCache.remove(key);
            }
        }

    }
}

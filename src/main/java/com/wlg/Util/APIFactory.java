package com.wlg.Util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 每一个服务类在这里配置，请求端通过该类获取服务调用
 */
public class APIFactory {
	private static final Log log = LogFactory.getLog(APIFactory.class);
	/**
	 * 不能直接引用factory去getbean,通过getFactory()方法获取factory再getBean
	 * 原因是factory在spring容器初始化前为null
	 */
    private static WebApplicationContext factory;

	private static WebApplicationContext getFactory(){
		if(factory == null){
			factory = ContextLoader.getCurrentWebApplicationContext();
		}
		return factory;
	}

	/**
	 * 获取服务，只适用于只有一个接口实现的服务类
	 * @param serviceInterfaceClass 服务接口class
	 * @param <T>
	 * @return
     */
	public static <T> T getService(Class<T> serviceInterfaceClass){
		T service = getFactory().getBean(serviceInterfaceClass);
		if(service == null){
		}
		return service;
	}
	public static <T> T getServiceById(String serviceId, Class<T> serviceInterfaceClass){
		T service = getFactory().getBean(serviceId,serviceInterfaceClass);
		if(service == null){
		}
		return service;
	}

	
}

package com.hh.improve.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 全局properties
 * @author lilongshun
 * @date 2013-9-22
 */
public class AppPropertiesUtil extends PropertyPlaceholderConfigurer {
	private static Map<String, Object> ctxPropertiesMap;  
	  
    @Override  
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,
            Properties props)throws BeansException {
        super.processProperties(beanFactory, props);
        ctxPropertiesMap = new HashMap<String, Object>();  
        for (Object key : props.keySet()) {  
            String keyStr = key.toString();  
            String value = props.getProperty(keyStr);  
            ctxPropertiesMap.put(keyStr, value);  
        }  
    }  
   
    /**
     * 获取spring托管的properties内容 
     * @author lilongshun
     * @date 2013-9-14
     * @param name
     * @return Object
     */
    public static Object getProperty(String name) {  
        return ctxPropertiesMap.get(name);  
    }
    /**
     * 获取spring托管的properties内容 
     * @author lilongshun
     * @date 2013-9-14
     * @param name
     * @return String
     */
    public static String getPropertyString(String name) {  
    	Object obj=ctxPropertiesMap.get(name);
    	return obj==null?"":obj.toString();		
    }  
    /**
     * 设置spring托管的properties内容 
     * @author lilongshun
     * @date 2013-9-14
     * @param name
     * @return String
     */
    public static void setPropertyString(String name,String value) {  
    	ctxPropertiesMap.put(name, value);
    }  
}

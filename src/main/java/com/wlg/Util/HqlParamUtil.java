package com.wlg.Util;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by wlg on 2016/6/14.
 */
public class HqlParamUtil {
    public static <T> Object getFieldValue(T entity){
        Field[] field = entity.getClass().getDeclaredFields();
        StringBuffer stringBuffer = new StringBuffer();
        Field fi = null;
        for(Field fd : field){
            try {
                fi = entity.getClass().getDeclaredField(fd.getName());//获取属性
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            fi.setAccessible(true);//设置当前对象对model私有属性的访问权限
            Object fieldValue = null;
            try {
                fieldValue = fi.get(entity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(fieldValue!=null&&!"".equals(fieldValue)){
                if(fieldValue instanceof Date){
                    fieldValue = DateUtil.DateToStr_YMDHMS((Date)fieldValue);
                }
                stringBuffer.append(" AND " + fd.getName() + " = '" + fieldValue + "' ");
            }
        }
        //输出属性值
        return stringBuffer.toString();
    }
}

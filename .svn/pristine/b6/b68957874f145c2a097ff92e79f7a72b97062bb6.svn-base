package com.wlg.Model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于定义主键
 * Created by chenqi on 2016/4/21.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Key {
    /**
     * 保存将要作为主键的属性字段列表,多个属性作为主键用英文","分隔
     * @return
     */
    public String fields();
}

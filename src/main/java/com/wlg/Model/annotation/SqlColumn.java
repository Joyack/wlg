package com.wlg.Model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于定义表字段
 * @author chenqi
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlColumn {
    /**
     * 列明，默认为对应的对象属性名
     * @return
     */
    public String name();

    //是否允许空
    public boolean notNull() default false;
}

/**
 * 文件名：PropertyFilter.java
 * 创建日期： 2012-6-12
 * Copyright (c) 2011-2011 广电运通
 * All rights reserved.
 
 * 修改记录：
 * 1.修改时间, 修改人：
 *   修改内容：
 */
package com.wlg.Dao.Impl.hibernate;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;


/**
 * 与具体ORM实现无关的属性过滤条件封装类.
 * 
 * PropertyFilter主要记录页面中简单的搜索过滤条件,比Hibernate的Criterion要简单.
 * 
 * @author calvin
 */
public class PropertyFilter {

	/**
	 * 多个属性间OR关系的分隔符.
	 */
	public static final String OR_SEPARATOR = "_OR_";

	/**
	 * 属性比较类型. EQ等于， LIKE， LT小于， GT大于，LE小于等于 GE大于等于
	 */
	public enum MatchType {
		/** 等于 */
		EQ,

		/** LIKE */
		LIKE,

		/** 小于 */
		LT,

		/** 大于 */
		GT,

		/** 大于等于 */
		LE,

		/** 小于等于 */
		GE;
	}

	/**
	 * 属性比较类型. S:String，I:Integer，L:Long，N:Double，D:Date，B:Boolean
	 */
	public enum PropertyType {
		/**
		 * String, Integer, Long, Double, Date, Boolean
		 */
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(
				Date.class), B(Boolean.class);

		private Class<?> clazz;

		PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		/**
		 * 
		 * @return 比较类型
		 */
		public Class<?> getValue() {
			return clazz;
		}
	}

	private String[] propertyNames;
	private Class<?> propertyType;
	/**
	 * 
	 */
	private Object propertyValue;
	private MatchType matchType = MatchType.EQ;

	/** 构造函数 */
	public PropertyFilter() {
	}

	/**
	 * @param filterName
	 *            比较属性字符串,含待比较的比较类型、属性值类型及属性列表. eg. LIKES_NAME_OR_LOGIN_NAME
	 * @param value
	 *            待比较的值.
	 */
	public PropertyFilter(final String filterName, final Object value) {

		String matchTypeStr = StringUtils.substringBefore(filterName, "_");
		int length = matchTypeStr.length();
		String matchTypeCode = StringUtils.substring(matchTypeStr, 0,
				length - 1);
		String propertyTypeCode = StringUtils.substring(matchTypeStr,
				length - 1, length);
		matchType = Enum.valueOf(MatchType.class, matchTypeCode);
		propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode)
				.getValue();

		String propertyNameStr = StringUtils.substringAfter(filterName, "_");
		propertyNames = StringUtils.split(propertyNameStr,
				PropertyFilter.OR_SEPARATOR);

		Assert.isTrue(propertyNames.length > 0, "filter name" + filterName
				+ "is not according rule to write,can not get field name.");
		// 按entity property中的类型将字符串转化为实际类型.
		this.propertyValue = ReflectionUtils.convertValue(value, propertyType);
	}

	/**
	 * 是否有多个属性.
	 * 
	 * @return 是否多个属性
	 */
	public boolean isMultiProperty() {
		return (propertyNames.length > 1);
	}

	/**
	 * 获取比较属性名称列表.
	 * 
	 * @return 属性名称
	 */
	public String[] getPropertyNames() {
		return propertyNames;
	}

	/**
	 * 获取唯一的属性名称.
	 * 
	 * @return 属性名称
	 */
	public String getPropertyName() {
		if (propertyNames.length > 1)
			throw new IllegalArgumentException(
					"There are not only one property");
		return propertyNames[0];
	}

	/**
	 * 获取比较值.
	 * 
	 * @return 属性值
	 */
	public Object getPropertyValue() {
		return propertyValue;
	}

	/**
	 * 获取比较值的类型.
	 * 
	 * @return 属性类型
	 */
	public Class<?> getPropertyType() {
		return propertyType;
	}

	/**
	 * 获取比较类型.
	 * 
	 * @return 比较类型
	 */
	public MatchType getMatchType() {
		return matchType;
	}
}

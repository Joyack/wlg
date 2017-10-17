package com.wlg.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static SimpleDateFormat symd = new SimpleDateFormat(YYYY_MM_DD);
	public static SimpleDateFormat symdhms = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);

	/**
	 * 字符串转date
	 * @param str
	 * @return
     */
	public static Date strToDate_YMD(String str){
		try {
			return symd.parse(str);
		} catch (ParseException e) {
			System.out.println("日期格式错误");
			return null;
		}
	}


	/**
	 * date转字符串
	 * @param date
	 * @return
	 */
	public static String DateToStr_YMDHMS(Date date){
			return symdhms.format(date);
	}

	/**
	 * 字符串转date
	 * @param str
	 * @return
	 */
	public static Date strToDate_YMDHMS(String str) {
		try {
			return symdhms.parse(str);
		} catch (ParseException e) {
			System.out.println("日期格式错误");
			return null;
		}
	}

	/**
	 * 获取当天日期
	 * @return
	 */
	public static String today(){
		Calendar day = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		return df.format(day.getTime());
	}


	/**
	 * 获取昨天日期
	 * @return
	 */
	public static String yesterday(){
		Calendar dayc1 = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		dayc1.add(dayc1.DAY_OF_MONTH, -1);
		return df.format(dayc1.getTime());

	}

	/**
	 * 获取下一分钟
	 * @return
	 */
	public static String tomorrow(){
		Calendar dayc1 = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		dayc1.add(dayc1.DAY_OF_MONTH, +1);
		return df.format(dayc1.getTime());

	}

	/**
	 * 获取下一分钟
	 * @return
	 */
	public static String nextMin(String time){
		Calendar dayc1 = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		try {
			dayc1.setTime(df.parse(time));
			dayc1.add(dayc1.MINUTE, +1);
			return df.format(dayc1.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取上一分钟
	 * @return
	 */
	public static String upMin(String time){
		Calendar dayc1 = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		try {
			dayc1.setTime(df.parse(time));
			dayc1.add(dayc1.MINUTE, -1);
			return df.format(dayc1.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}

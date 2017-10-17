package com.wlg.Test;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LvLiangFeng on 2016/11/24.
 */
public class Main{
    public static void main(String[] args) throws UnsupportedEncodingException, ParseException {
//        String data = HttpUtil.sendGet("http://job.hunnu.edu.cn/detail/career?id=34786","start=0&count=11&keyword=&type=inner&day=");
//        System.out.println(data);
//
//        String str = "AGUZ12107016Q000214E ***";
//        str.trim();
//        System.out.println(str);
//        str.replace(" ","");
//        System.out.println(str);
//
//        str = "AGUZ12107016Q000214E ***";
//
//        str = str.trim();
//        System.out.println(str);
//        str = str.replace("　","");
//        System.out.println(str);

        String date = "2017-02";
        String beforebegindate = "2017-02-01 00:00:00";
        String beforeenddate = "2017-02-01 23:59:59";


        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM");
        Date d= null;
        try {
            d = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(calendar.getTime()));

    }
}

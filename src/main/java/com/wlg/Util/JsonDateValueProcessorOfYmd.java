package com.wlg.Util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by LvLiangFeng on 2016/11/30.
 */
public class JsonDateValueProcessorOfYmd implements JsonValueProcessor {

    public Object processArrayValue(Object value, JsonConfig config) {
        return process(value);
    }

    public Object processObjectValue(String key, Object value, JsonConfig config) {
        return process(value);
    }

    private Object process(Object value){
        if(value instanceof Date){
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YYYY_MM_DD, Locale.UK);
            return sdf.format(value);
        }
        if(value instanceof Float){
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(value);
        }
        return value == null ? "" : value.toString();
    }
}
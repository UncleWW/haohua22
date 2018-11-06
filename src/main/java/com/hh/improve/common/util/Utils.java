package com.hh.improve.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 工具类
 * Created by jay on 2017/4/28.
 */
public final class Utils {
    //获取guid
    public static String getGUID(){
        String guid= UUID.randomUUID().toString();
        String value[]=guid.split("-");
        StringBuffer valString= new StringBuffer();
        for(int i=0;i<value.length;i++){
            valString.append(value[i]);
        }
        return valString.toString();
    }
    //获取当前时间
    public static String getNowDate(){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date   curDate   =   new   Date(System.currentTimeMillis());
        return sdf.format(curDate);
    }

    public static Timestamp getTimestamp(){
        return Timestamp.valueOf(getNowDate());
    }
}

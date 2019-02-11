package com.cloud.zuul.util;

import java.text.SimpleDateFormat;

public class DateTimeUtil {

    public static final String dateformat(long timestamp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }
}

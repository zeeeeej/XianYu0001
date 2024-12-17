package com.yangxinyu.smkt.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class StringUtil {

    public static String datetime2str(long timestamp,String pattern){
        // 将时间戳转换为Instant对象
        Instant instant = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            instant = Instant.ofEpochMilli(timestamp);
            // 将Instant对象转换为特定时区的LocalDateTime对象
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

            // 定义一个时间格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            // 使用DateTimeFormatter格式化LocalDateTime对象
            return dateTime.format(formatter);
        }else{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
            return simpleDateFormat.format(new Date(timestamp));
        }
    }

    public static final String PATTERN_MM_DD = "MM.dd";
    public static final String PATTERN_EEEE= "EEEE";
    public static final String PATTERN_ACTIVITY = "MM.dd  HH:mm  EEEE";
    public static final String PATTERN_ACTIVITY_PUBLISH = "YYYY-MM-dd  HH:mm";
}

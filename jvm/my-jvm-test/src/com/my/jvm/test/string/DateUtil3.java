package com.my.jvm.test.string;

import org.springframework.core.NamedThreadLocal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 为提升性能，自定义时间转化工具
 *
 * @author Zijian Liao
 * @since 1.0.0
 */
public final class DateUtil3 {

    private DateUtil3() {
    }

    /**
     * 定义本地线程池，达到DateFormat复用目的
     */
    private static final ThreadLocal<Map<String, DateFormat>> DATE_FORMAT_CONTEXT = new NamedThreadLocal<>("date format context");

    public static DateFormat getDf(String pattern) {
        Map<String, DateFormat> dateFormatMap = DATE_FORMAT_CONTEXT.get();
        if(dateFormatMap == null){
            dateFormatMap = new HashMap<>(4);
            DATE_FORMAT_CONTEXT.set(dateFormatMap);
        }
        DateFormat dateFormat = dateFormatMap.get(pattern);
        if(dateFormat == null){
            dateFormat = new SimpleDateFormat(pattern);
            dateFormatMap.put(pattern, dateFormat);
        }
        return dateFormat;
    }

    public static String format(Date date, String pattern) {
        return getDf(pattern).format(date);
    }

    public static Date parse(String date, String pattern) {
        try {
            return getDf(pattern).parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("时间解析失败 data:" + date + " pattern:" + pattern);
        }
    }
}

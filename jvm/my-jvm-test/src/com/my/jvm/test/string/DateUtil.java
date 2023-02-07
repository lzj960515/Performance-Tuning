package com.my.jvm.test.string;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 为提升性能，自定义时间转化工具
 *
 * @author Zijian Liao
 * @since 1.0.0
 */
public final class DateUtil {

    private DateUtil() {
    }

    /**
     * 定义本地线程池，达到DateFormat复用目的
     */
    private static final ThreadLocal<DateFormat> DATE_FORMAT_CONTEXT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM"));

    public static DateFormat getDf() {
        return DATE_FORMAT_CONTEXT.get();
    }

    public static String format(Date date) {
        return getDf().format(date);
    }

    public static Date parse(String date) {
        try {
            return getDf().parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("时间解析失败 data:" + date);
        }
    }
}

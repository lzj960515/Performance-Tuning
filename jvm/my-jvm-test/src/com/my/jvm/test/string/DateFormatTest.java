package com.my.jvm.test.string;

import java.util.Date;

/**
 * @author Zijian Liao
 * @since 1.0.0
 */
public class DateFormatTest {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(15000);
        // 今天
        Date date1 = new Date();
        System.out.println(date1);
        // 上个月
        Date date2 = new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
        System.out.println(date2);
        System.gc();
        Thread.sleep(4000);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                // 这里应该输出thread-1:2023-02
                System.out.println(Thread.currentThread().getName() + ":" +  DateUtil3.format(date1, "yyyy-MM"));
            }
        }, "thread-1");
        t1.start();

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                // 这里应该输出thread-2:2023-01
                System.out.println(Thread.currentThread().getName() + ":" +   DateUtil3.format(date2, "yyyy-MM"));
            }
        }, "thread-2");
        t2.start();

        t1.join();
        t2.join();
        System.out.println("gc");
        Thread.sleep(5000L);
        System.gc();
        Thread.sleep(5000L);
        System.out.println(DateUtil3.format(date2, "yyyy-MM"));
        Thread.sleep(5000L);
    }
}

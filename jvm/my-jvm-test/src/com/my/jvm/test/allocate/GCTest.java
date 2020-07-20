package com.my.jvm.test.allocate;

/**
 * 测试GC -XX:+PrintGCDetails -Xmx256m -Xms256m
 * 大对象直接进入老年代 -XX:PretenureSizeThreshold=1000000 -XX:+UseSerialGC
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class GCTest {

    public static void main(String[] args) throws InterruptedException {
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6;
        allocation1 = new byte[60000 * 1024];
//
//        allocation2 = new byte[8000 * 1024];
//
//        allocation3 = new byte[1000 * 1024];
//        allocation4 = new byte[1000 * 1024];
//        allocation5 = new byte[1000 * 1024];
//        allocation6 = new byte[1000 * 1024];
    }
}

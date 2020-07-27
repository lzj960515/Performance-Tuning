package com.my.jvm.test.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试OOM
 * -Xms10M -Xmx10M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\jvm.dump
 * @author Zijian Liao
 * @since 1.0
 */
public class OOMTest {
    static List<Object> list = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            list.add(new User());
        }
    }

    static class User {
        byte[] bytes = new byte[1024];
    }
}
package com.my.jvm.test.gc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Zijian Liao
 * @since 1.0.0
 */
public class Test {

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder("bbbb");
        String s = sb.toString();
        System.out.println(s.equals(s.intern()));
        System.out.println(s == s.intern());

        StringBuilder b = new StringBuilder("aaa").append("a");
        String s1 = b.toString();
        System.out.println(s1.equals(s1.intern()));
        System.out.println(s1 == s1.intern());
//        method();
//        System.gc();
    }

    private static void method(){
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName());
        });
        System.out.println(executorService);
    }
}

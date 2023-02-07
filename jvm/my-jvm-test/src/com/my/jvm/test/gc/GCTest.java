package com.my.jvm.test.gc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 调优作业一
 */
public class GCTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        while (true) {
            executorService.execute(GCTest::alloc);
        }
    }

    private static void alloc() {
        User user = new User();
        user.id = 1;
        user.name = "zhangsan";
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
class User {

    /**
     * 4m对象
     */
    private Byte[] bytes = new Byte[1024 * 1024];

    Integer id;
    String name;
}


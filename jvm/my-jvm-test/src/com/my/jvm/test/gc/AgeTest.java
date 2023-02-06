package com.my.jvm.test.gc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zijian Liao
 * @since 1.0.0
 */
public class AgeTest {

    private static List<User> list = new ArrayList<>(1);

    public static void main(String[] args) throws InterruptedException, IOException {
        // 禁止逃逸分析，年轻代50m，eden40m, survivor10m
        // -Xmx100m -Xmn50m -XX:-DoEscapeAnalysis -XX:MaxTenuringThreshold=1
        // 只有一个对象被list引用，其他全是垃圾
        list.add(new User());
        for (;;){
            // 500毫秒产生4m
            Thread.sleep(500L);
            alloc();
        }
    }

    private static void alloc(){
        new User();
    }

    static class User {
        /**
         * 4m对象
         */
        private Byte[] bytes = new Byte[1024 * 1024];
    }
}

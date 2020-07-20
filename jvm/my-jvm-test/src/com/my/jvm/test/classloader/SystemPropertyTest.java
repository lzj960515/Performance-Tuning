package com.my.jvm.test.classloader;

import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 测试系统变量
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class SystemPropertyTest {

    public static void main(String[] args) {
        String s = SystemPropertyTest.class.getResource("/").toString();
        System.out.println(new String(s.getBytes(), StandardCharsets.UTF_8));
        URL resource = Thread.currentThread().getContextClassLoader().getResource("");
        String path = resource.getPath();
        System.out.println(path);
    }
}

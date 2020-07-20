package com.my.jvm.test.classloader;

/**
 * @author Zijian Liao
 * @date 2020/6/13 10:48
 * @description
 */
public class Son extends Father {

    static {
        System.out.println("init son");
    }

}

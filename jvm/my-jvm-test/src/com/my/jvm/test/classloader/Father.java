package com.my.jvm.test.classloader;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Zijian Liao
 * @date 2020/6/13 10:48
 * @description
 */
public class Father {

    public static final String NAME = "zhangsan";
    public static String JOB = "java development engineer";
    public static final Random MONEY = new Random();
    static {
        System.out.println("init father");
    }

    public static void print(){
        System.out.println("Father.print");
    }

}

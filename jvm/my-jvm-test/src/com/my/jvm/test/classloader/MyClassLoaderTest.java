package com.my.jvm.test.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 测试自定义类加载器
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class MyClassLoaderTest {

    public static void main(String[] args) throws Exception {
//        MyClassLoader myClassLoader = new MyClassLoader();
        String path = "D:\\学习笔记\\架构师学习之路_笔记\\性能调优\\jvm\\my-jvm-test\\out\\production\\my-jvm-test";
        MyClassLoaderSample myClassLoader = new MyClassLoaderSample(path);

        Class<?> math = myClassLoader.loadClass("com.my.jvm.test.classloader.Math");
        Method compute = math.getDeclaredMethod("compute");
        Object result = compute.invoke(math.newInstance());
        System.out.println(result);


    }
}

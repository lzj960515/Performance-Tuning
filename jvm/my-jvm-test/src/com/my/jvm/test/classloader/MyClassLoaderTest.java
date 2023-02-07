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
        String path = "/Users/liaozijian/work/kq-project/kq-generator-demo/pig-keep/target/classes";
        MyClassLoaderSample myClassLoader = new MyClassLoaderSample(path);

        Class<?> math = myClassLoader.loadClass("com.lzj.pig.controller.BillRecordController");
        Method compute = math.getDeclaredMethod("compute");
        Object result = compute.invoke(math.newInstance());
        System.out.println(result);


    }
}

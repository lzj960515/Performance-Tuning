package com.my.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Zijian Liao
 * @date 2020/6/13 10:37
 * @description
 */
public class Test {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader m = new MyClassLoader();
        Class<?> aClass1 = m.loadClass("com.my.launcher.Math");
//        System.out.println(aClass1.getClassLoader());
//        Class<?> aClass = Test.class.getClassLoader().loadClass("com.my.launcher.Math");
//        System.out.println(aClass.getClassLoader());
        Object o1 = aClass1.newInstance();
//        Object o1 = aClass.newInstance();
//        try{
//            com.my.launcher.Math math = (com.my.launcher.Math) o;
//            com.my.launcher.Math math2 = (com.my.launcher.Math) o1;
//        }catch (Throwable e){
//            e.printStackTrace();
//        }
//        Method declaredMethod = aClass.getDeclaredMethod("test");
//        System.out.println(declaredMethod.invoke(o));

        Method declaredMethod2 = aClass1.getDeclaredMethod("test");
        declaredMethod2.invoke(o1);
//        System.out.println(aClass == aClass1);
    }
}

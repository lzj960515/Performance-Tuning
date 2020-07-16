package com.my.parent;

/**
 * @author Zijian Liao
 * @date 2020/6/13 12:12
 * @description
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //System.out.println(Son.NAME);//调用final修饰的静态变量 不初始化
        //System.out.println(Son.JOB);//调用静态变量，初始化父类，不初始化子类
        //System.out.println(Son.MONEY);//调用final修饰的成员类变量，初始父类，不初始化子类
        //Son.print();//调用静态方法，初始化父类，不初始化子类
        //Class.forName("com.my.parent.Son");//初始化父类，再初始化子类
//        Class<?> aClass = ClassLoader.getSystemClassLoader()
//                .loadClass("com.my.parent.Son");//获得class，不初始化
//        aClass.newInstance();//初始化父类，再初始子类

    }
}

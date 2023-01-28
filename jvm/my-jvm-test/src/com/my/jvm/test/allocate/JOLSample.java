package com.my.jvm.test.allocate;

import org.openjdk.jol.info.ClassLayout;

/**
 * 测试对象指针压缩
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class JOLSample {

    public static void main(String[] args) {
        ClassLayout layout = ClassLayout.parseInstance(new Object());
        System.out.println(layout.toPrintable());

        System.out.println();
        ClassLayout layout1 = ClassLayout.parseInstance(new int[]{});
        System.out.println(layout1.toPrintable());

        System.out.println();
        ClassLayout layout2 = ClassLayout.parseInstance(new A());
        System.out.println(layout2.toPrintable());
        System.out.println();
        ClassLayout layout3 = ClassLayout.parseInstance(new B());
        System.out.println(layout3.toPrintable());
    }
    // -XX:+UseCompressedOops           默认开启的压缩所有指针
    // -XX:+UseCompressedClassPointers  默认开启的压缩对象头里的类型指针Klass Pointer
    // Oops : Ordinary Object Pointers
    public static class A {
        //8B mark word
        //4B Klass Pointer   如果关闭压缩-XX:-UseCompressedClassPointers或-XX:-UseCompressedOops，则占用8B
        int id;        //4B
        String name;   //4B  如果关闭压缩-XX:-UseCompressedOops，则占用8B
        byte b;        //1B  byte类型会自动填充到4字节，比如这个会填充3字节，如果后面还跟着byte a, 则填充2字节
        Object o;      //4B  如果关闭压缩-XX:-UseCompressedOops，则占用8B
    }

    public static class B {
        //8B mark word
        //4B Klass Pointer   如果关闭压缩-XX:-UseCompressedClassPointers或-XX:-UseCompressedOops，则占用8B
        int id;        //4B
        String name;   //4B  如果关闭压缩-XX:-UseCompressedOops，则占用8B
    }
}

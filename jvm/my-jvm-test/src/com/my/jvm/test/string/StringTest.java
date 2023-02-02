package com.my.jvm.test.string;

/**
 * @author Zijian Liao
 * @since 1.0.0
 */
public class StringTest {

    public static void main(String[] args) {
        String aaa = "aaa";
        // true
        System.out.println(aaa == aaa.intern());

        String aaa2 = new String("aa") + new String("a");
        // true
        System.out.println(aaa == aaa2.intern());

        String bbb = new String("bb") + new String("b");
        // true
        System.out.println(bbb == bbb.intern());

        StringBuilder cc = new StringBuilder();
        String ccc = cc.append("c").append("cc").toString();
        // true ccc不在常量池，返回他自己
        // 改一下，把ccc放入常量池
        String fff = "ccc";
        // 变成false
        System.out.println(ccc == ccc.intern());

        String ddd = new String("ddd");
        // false
        // "ddd"在常量池，返回常量池的
        System.out.println(ddd == ddd.intern());
    }
}

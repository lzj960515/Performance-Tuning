package com.my.jvm.test.memory;

/**
 * 测试栈溢出 -Xss128k 默认为1m
 * @author Zijian Liao
 */
public class StackOverflowTest {
    
    static int count = 0;

    static void redo() {
        count++;
        redo();
    }

    public static void main(String[] args) {
        try {
            redo();
        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println(count);
        }
    }
}
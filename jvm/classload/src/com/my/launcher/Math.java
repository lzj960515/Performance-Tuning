package com.my.launcher;

/**
 * @author Zijian Liao
 * @date 2020/6/12 22:14
 * @description javap -c
 */
public class Math {

    public int add(){
        int a = 3;
        int b = 5;
        return a + b;
    }

    public void test(){
        User user= new User();
        user.bye();
        user.print();
        int add = add();
        System.out.println(add);
    }

    public static void main(String[] args) {
        Math math = new Math();
        int result = math.add();
        System.out.println(result);
    }
}

package com.my.jvm.test.allocate;

import java.util.ArrayList;
import java.util.List;

public class OOMTest {
    static List<Object> list = new ArrayList<>();

    public static void main(String[] args) {
        int i = 0;
        int j = 0;
        while (true) {
            list.add(new User(i++));
            new User(j--);
        }
    }

    static class User {
        int id;

        public User(int id) {
            this.id = id;
        }

        @Override
        protected void finalize() throws Throwable {
            list.add(this);
            System.err.println("user id =" + id + "的对象执行了finalize的方法自救");
        }
    }
}
package com.my.launcher;

import java.io.File;
import java.util.StringTokenizer;

/**
 * @author Zijian Liao
 * @date 2020/6/12 23:30
 * @description
 */
public class ExtFile {
    public static void main(String[] args) {
        String var0 = System.getProperty("java.ext.dirs");
        File[] var1;
        if (var0 != null) {
            StringTokenizer var2 = new StringTokenizer(var0, File.pathSeparator);
            int var3 = var2.countTokens();
            var1 = new File[var3];

            for(int var4 = 0; var4 < var3; ++var4) {
                var1[var4] = new File(var2.nextToken());
            }
        } else {
            var1 = new File[0];
        }
        System.out.println(var1);
    }
}

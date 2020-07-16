package com.my.classloader;

import sun.misc.Launcher;
import sun.net.www.ParseUtil;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * @author Zijian Liao
 * @date 2020/6/12 22:11
 * @description
 */
public class MyClassLoader extends URLClassLoader {

    private static String classPath = System.getProperty("java.class.path");

    public MyClassLoader() {
        super(pathToURLs(getClassPath()), getSystemClassLoader().getParent());
    }

    public static File[] getClassPath() {
        String var0 = classPath;
        File[] var1;
        if (var0 != null) {
            int var2 = 0;
            int var3 = 1;
            boolean var4 = false;

            int var5;
            int var7;
            for(var5 = 0; (var7 = var0.indexOf(File.pathSeparator, var5)) != -1; var5 = var7 + 1) {
                ++var3;
            }

            var1 = new File[var3];
            var4 = false;

            for(var5 = 0; (var7 = var0.indexOf(File.pathSeparator, var5)) != -1; var5 = var7 + 1) {
                if (var7 - var5 > 0) {
                    var1[var2++] = new File(var0.substring(var5, var7));
                } else {
                    var1[var2++] = new File(".");
                }
            }

            if (var5 < var0.length()) {
                var1[var2++] = new File(var0.substring(var5));
            } else {
                var1[var2++] = new File(".");
            }

            if (var2 != var3) {
                File[] var6 = new File[var2];
                System.arraycopy(var1, 0, var6, 0, var2);
                var1 = var6;
            }
        } else {
            var1 = new File[0];
        }

        return var1;
    }

    private static URL[] pathToURLs(File[] var0) {
        URL[] var1 = new URL[var0.length];

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = getFileURL(var0[var2]);
        }

        return var1;
    }

    static URL getFileURL(File var0) {
        try {
            var0 = var0.getCanonicalFile();
        } catch (IOException var3) {
        }
        try {
            return ParseUtil.fileToEncodedURL(var0);
        } catch (MalformedURLException var2) {
            throw new InternalError(var2);
        }
    }

}

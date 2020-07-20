package com.my.jvm.test.classloader;

import java.io.FileInputStream;

/**
 * 自定义类加载器
 *
 * @author Zijian Liao
 * @since 1.0
 */
public class MyClassLoaderSample extends ClassLoader {

    private String classPath;

    public MyClassLoaderSample(String classPath) {
        this.classPath = classPath;
    }

    private byte[] loadByte(String name) throws Exception {
        name = name.replaceAll("\\.", "/");
        FileInputStream fis = new FileInputStream(classPath + "/" + name
                + ".class");
        int len = fis.available();
        byte[] data = new byte[len];
        fis.read(data);
        fis.close();
        return data;

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] data = loadByte(name);
            return defineClass(name, data, 0, data.length);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    /**
     * 重写类加载方法，实现自己的加载逻辑，不委派给双亲加载
     *
     * @param name
     * @param resolve
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                try {
                    //先用扩展类加载器加载
                    c = getSystemClassLoader().getParent().loadClass(name);
                } catch (ClassNotFoundException e) {
                }
            }
            if (c == null) {
                //再用自己的类加载器加载
                try {
                    c = findClass(name);
                } catch (ClassNotFoundException e) {
                }
            }
            if (c == null) {
                //加载不到则用系统类加载器加载
                c = getSystemClassLoader().loadClass(name);
            }
            return c;
        }
    }
}

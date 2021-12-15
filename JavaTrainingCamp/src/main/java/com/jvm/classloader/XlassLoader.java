package com.jvm.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 高一平
 * @date 2021/9/21
 * @description 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件
 */
public class XlassLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> xlass = new XlassLoader().loadClass("Hello");
        Method helloMethod = xlass.getDeclaredMethod("hello");
        helloMethod.invoke(xlass.getDeclaredConstructor().newInstance());
    }

    @Override
    protected Class<?> findClass(String name) {
        File file = new File("com/jvm/classloader/" + name + ".xlass");
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = in.readAllBytes();
            data = decode(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return defineClass(name, data, 0, data.length);
    }

    private byte[] decode(byte[] bytes) {
        byte[] data = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            data[i] = (byte) (255 - bytes[i]);
        }
        return data;
    }

}

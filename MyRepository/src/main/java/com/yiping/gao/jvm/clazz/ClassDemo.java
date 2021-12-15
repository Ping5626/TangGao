package com.yiping.gao.jvm.clazz;

import com.yiping.gao.pojo.Result;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 * @Author: 高一平
 * @Date: 2019/9/2 20:57
 * @Description: Java 反射
 **/
public class ClassDemo {

    public static void run() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Result result = new Result();
        result.success();

        /**
         * 获取Class对象方式1
         */
        Class clazz1 = Result.class;
        /**
         * 获取Class对象方式2
         */
        Class clazz2 = Class.forName("com.yiping.gao.pojo.Result");

        /**
         * 获取类名，含包名
         */
        String clazzName = clazz1.getName();
        /**
         * 获取类名，不含包名
         */
        String clazzSimpleName = clazz1.getSimpleName();
        System.out.println("getName：" + clazzName + "\tgetSimpleName：" + clazzSimpleName);

        /**
         * 获取类修饰符
         * 判断类修饰符
         */
        int mod = clazz1.getModifiers();
        System.out.println("Modifier.isPublic:" + Modifier.isPublic(mod));
        System.out.println("Modifier.isProtected:" + Modifier.isProtected(mod));

        /**
         * 获取包
         */
        Package p = clazz1.getPackage();
        System.out.println("getPackage：" + p);

        /**
         * 获取父类
         */
        Class superClass = clazz1.getSuperclass();
        System.out.println("getSuperclass：" + superClass);

        /**
         * 获取实现接口
         */
        Class[] interfaces = clazz1.getInterfaces();
        System.out.println("getInterfaces:" + interfaces.length);

        /**
         * 构造方法
         */
        Constructor[] cons = clazz1.getConstructors();
        System.out.println("getConstructors:" + cons.length);

        /**
         * 获取所有方法
         */
        Method[] methods = clazz1.getMethods();
		System.out.println("getMethods:" + methods.length);
		for (Method method : methods) {
		    System.out.println("method.getName:" + method);
		}
        /**
         * 获取私有方法
         */
        Method[] pMethods = clazz1.getMethods();
        System.out.println("getMethods:" + pMethods.length);
        for (Method method : pMethods) {
            System.out.println("method.getName:" + method.getName());
        }
        /**
         * 获取指定方法
         */
        Method method = clazz1.getMethod("getProp1", null);
         System.out.println("getMethod(,):" + method);
        /**
         * 调用方法
         */
        Object methodVlaue = method.invoke(result, null);
        System.out.println("method.invoke(,):" + methodVlaue);
        /**
         * 获取指定方法
         */
        Method method3 = clazz1.getMethod("setProp3", Double.class);
        System.out.println("getMethod(,):" + method3);
        /**
         * 调用setter方法，该方法没有返回值，所以methodValue3为null；此处注意参数2.0 ，不能用null
         */
        Object methodValue3 = method3.invoke(result, 2.0);
		System.out.println("method.invoke(,):" + methodValue3);

        /**
         * 获取变量
         */
        Field[] fields = clazz1.getDeclaredFields();
        System.out.println("getDeclaredFields:" + fields.length);
        for (Field field : fields) {
            field.setAccessible(true);
            /**
             * 设置字段的值
             */
            field.set(result, null);
            /**
             * 获取实例属性名和值
             */
            System.out.println("field.getAnnotations:" + field.getAnnotations().length + "\tfield.getName:" + field.getName() + "\tfield.get:" + field.get(result));

        }

        /**
         * 获取类注解
         */
        Annotation[] annos = clazz1.getAnnotations();
        System.out.println("getAnnotations:" + annos.length);

    }

}

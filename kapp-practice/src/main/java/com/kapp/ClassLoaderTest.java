package com.kapp;

public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException {
        /**
         * Class.forName,通过传入类全限定名获取
         */
        Class<?> aClass = Class.forName("com.kapp.ClassLoaderTest");
        ClassLoader classLoader = aClass.getClassLoader();
        System.out.println(classLoader);
        /**
         * 通过当前线程的上下文获取
         * Thread.get
         */
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader);
        /**
        *通过系统来加载器来获取
        */
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);
    }
}
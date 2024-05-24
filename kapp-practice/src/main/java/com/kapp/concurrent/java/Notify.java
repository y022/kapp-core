package com.kapp.concurrent.java;

import java.util.concurrent.locks.LockSupport;

public class Notify {

    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                synchronized (LOCK) {
                    System.out.println("thread-1");
                    System.out.println("线程1尝试唤醒锁！");
                    LOCK.notify();
                    System.out.println("线程1运行已经结束");
                }
            }
        };


        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                synchronized (LOCK) {
                    System.out.println("线程2获取到lock锁");
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("线程2被唤醒");
                    System.out.println("thread-2");
                }
            }
        };


        Runnable r3 = new Runnable() {
            @Override
            public void run() {
                synchronized (LOCK) {
                    System.out.println("线程3获取到lock锁");
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("线程3被唤醒");
                    System.out.println("thread-3");
                }
            }
        };
        new Thread(r2).start();
        new Thread(r3).start();
        LockSupport.parkNanos(100*1000000);
        new Thread(r1).start();
    }
}

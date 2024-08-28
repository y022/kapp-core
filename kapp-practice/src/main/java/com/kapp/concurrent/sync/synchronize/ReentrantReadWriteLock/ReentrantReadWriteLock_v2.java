package com.kapp.concurrent.sync.synchronize.ReentrantReadWriteLock;

/**
 * Author:Heping
 * Date: 2024/8/28 16:38
 * 交替打印递增数字
 */
public class ReentrantReadWriteLock_v2 {
    private static int counter = 0;
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            while (counter < 30) {
                incrementAndPrint(Thread.currentThread().getName());
            }
        }, "thread-1").start();
        new Thread(() -> {
            while (counter < 30) {
                incrementAndPrint(Thread.currentThread().getName());
            }
        }, "thread-2").start();
    }

    private static void incrementAndPrint(String name) {
        synchronized (LOCK) {
            System.out.println(name + ":" + ++counter);
            LOCK.notify(); // 唤醒其他等待在此锁上的线程
            try {
                if (counter < 30) {
                    LOCK.wait(); //释放锁并进入睡眠等待唤醒
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 设置中断标志
                throw new RuntimeException(e);
            }
        }
    }
}

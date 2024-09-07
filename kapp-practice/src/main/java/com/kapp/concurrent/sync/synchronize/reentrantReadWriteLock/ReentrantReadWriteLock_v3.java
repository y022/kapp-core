package com.kapp.concurrent.sync.synchronize.reentrantReadWriteLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author:Heping
 * Date: 2024/8/28 18:44
 */
public class ReentrantReadWriteLock_v3 {
    private static int COUNTER = 0;
    private static Lock LOCK = new ReentrantLock(false);
    private static Condition CONDITION = LOCK.newCondition();

    public static void main(String[] args) {
        Thread a = new Thread(() -> {
            while (COUNTER < 30) {
                incrementAndPrint(Thread.currentThread().getName());
            }
        }, "thread-1");
        Thread b = new Thread(() -> {
            while (COUNTER < 30) {
                incrementAndPrint(Thread.currentThread().getName());
            }
        }, "thread-2");
        a.start();
        b.start();
    }

    private static void incrementAndPrint(String name) {
        System.out.println(name + ":" + ++COUNTER);
        LOCK.lock();
        try {
            CONDITION.signal();
            if (COUNTER < 30) {
                CONDITION.await(); //释放锁并进入睡眠等待唤醒
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            LOCK.unlock();
        }
    }
}

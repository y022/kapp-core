package com.kapp.concurrent.sync.synchronize;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Author:Heping
 * Date: 2024/8/21 20:49
 */
public class Reentrancy {
    private static final String LOCK = "LOCK";

    public static void main(String[] args) {

        ReentrantLock reentrantLock = new ReentrantLock();
        synchronized (LOCK) {
            System.out.println("first get lock!");
            synchronized (LOCK) {
                System.out.println("second get lock!");
            }
        }
    }
}

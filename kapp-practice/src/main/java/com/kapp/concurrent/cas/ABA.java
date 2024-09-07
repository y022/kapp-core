package com.kapp.concurrent.cas;

import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.LockSupport;

/**
 * Author:Heping
 * Date: 2024/8/22 21:01
 */
public class ABA {
    public static int _a = 1;

    public static void main(String[] args) {
        AtomicStampedReference<Integer> ref = new AtomicStampedReference<>(_a, 1);
        Thread t2 = new Thread(() ->
        {
            _a = 3;
            _a = 1;
        });
        Thread t1 = new Thread(() -> {
            LockSupport.parkNanos(1000000 * 1000);
            boolean updated = ref.compareAndSet(1, 2, 1, 1);
            System.out.println("update res:" + updated);
        });
        t2.start();
        t1.start();
    }
}

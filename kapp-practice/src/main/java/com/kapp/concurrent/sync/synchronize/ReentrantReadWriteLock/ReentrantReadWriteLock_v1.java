package com.kapp.concurrent.sync.synchronize.ReentrantReadWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Author:Heping
 * Date: 2024/8/28 16:15
 */
public class ReentrantReadWriteLock_v1 {
    private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);
    private static final Map<String, Object> map = new HashMap<>();
    private static final Random random = new Random();
    private volatile static boolean cycle = true;

    public static void main(String[] args) {

        Runnable read = () -> {
            while (cycle) {
                try {
                    boolean require = readWriteLock.readLock().tryLock(200, TimeUnit.MILLISECONDS);
                    if (require) {
                        System.out.println("map.size() = " + map.size());
                        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(200));
                        readWriteLock.readLock().unlock();
                    } else {
                        System.out.println("请求读锁超时");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Runnable write = () -> {
            while (cycle) {
                try {
                    boolean require = readWriteLock.writeLock().tryLock(200, TimeUnit.MILLISECONDS);
                    if (require) {
                        int i = random.nextInt();
                        map.putIfAbsent(String.valueOf(random.nextInt()), i);
                        LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(200));
                        readWriteLock.writeLock().unlock();
                        System.out.println("写入完毕");
                    } else {
                        System.out.println("请求写锁超时");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };


        new Thread(read).start();
        new Thread(write).start();
        long start = System.currentTimeMillis();

        new Thread(() -> {
            while (true) {
                if (System.currentTimeMillis() - start > 10 * 1000) {
                    cycle = false;
                    break;
                }
            }
        }).start();

    }
}

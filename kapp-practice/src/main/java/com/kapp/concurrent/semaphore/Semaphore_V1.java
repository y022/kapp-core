package com.kapp.concurrent.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Logger;

/**
 * Author:Heping
 * Date: 2024/8/29 14:11
 * 启动十个线程，控制每个线程隔两秒打印信息
 */
public class Semaphore_V1 {
    private static final Logger log = Logger.getLogger(Semaphore_V1.class.getName());
    private static final int concurrentCount = 10;
    private static final Semaphore semaphore = new Semaphore(1);


    public static void main(String[] args) {
        for (int i = 0; i < concurrentCount; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    log.info(Thread.currentThread().getName());
                    LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release();
                }
            }, "thread-" + i).start();
        }
    }
}

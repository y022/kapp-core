package com.kapp.base.asm.concurrent;

/**
 * 即使使用了volatile,也无法保证最终结果的正确性。
 */
public class VolatileTest {
    public static volatile int i = 0;

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        for (int k = 0; k < threads.length; k++) {
            threads[k] = new Thread(() ->
            {
                for (int j = 0; j < 100; j++) {
                    i++;
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        System.out.println(i);
    }
}

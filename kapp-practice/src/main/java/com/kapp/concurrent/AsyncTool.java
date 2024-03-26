package com.kapp.concurrent;

import cn.hutool.core.date.SystemClock;
import com.jd.platform.async.executor.Async;
import com.jd.platform.async.wrapper.WorkerWrapper;

import java.util.concurrent.ExecutionException;

public class AsyncTool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ParWorker1 w1 = new ParWorker1();

        WorkerWrapper<String, String> workerWrapper =  new WorkerWrapper.Builder<String, String>()
                .worker(w1)
                .callback(w1)
                .param("1")
                .build();


        long now = SystemClock.now();
        System.out.println("begin-" + now);
        Async.beginWork(1500, workerWrapper);
        System.out.println("end-" + SystemClock.now());
        System.err.println("cost-" + (SystemClock.now() - now));
        System.out.println(Async.getThreadCount());
        System.out.println(workerWrapper.getWorkResult());
        Async.shutDown();
    }
}

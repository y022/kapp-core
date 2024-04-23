package com.kapp.kappcore.task.thread;

import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 不进行自定义线程池
 */
//@Configuration
public class ThreadConfiguration {

    //    @Bean
    public AsyncTaskExecutor asyncTaskExecutor(TaskExecutorBuilder taskExecutorBuilder) {

        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();

        executor.setThreadFactory(Thread::new);
        executor.setTaskDecorator(runnable -> runnable);
        return executor;
    }

}

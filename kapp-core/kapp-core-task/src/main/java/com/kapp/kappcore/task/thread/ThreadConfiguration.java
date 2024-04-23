package com.kapp.kappcore.task.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadConfiguration {

//    @Bean
    public AsyncTaskExecutor asyncTaskExecutor() {

        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("async");

        executor.setThreadFactory(Thread::new);
        executor.setTaskDecorator(runnable -> runnable);
        return executor;
    }

}

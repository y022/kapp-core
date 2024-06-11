package com.kapp.kappcore.support.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class ThreadConfiguration implements AsyncConfigurer {
    @Data
    @Configuration
    @ConfigurationProperties("spring.task.execution.pool")
    public static class CustomizePoolProperties {
        private int queueCapacity;
        private int coreSize;
        private int maxSize;
        private String threadNamePrefix = "Async-Task-";
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler(CustomizePoolProperties properties) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(properties.getCoreSize());
        scheduler.setThreadNamePrefix(properties.getThreadNamePrefix());
        scheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.initialize();
        return scheduler;
    }

    @Bean
    public Executor asyncExecutor(ThreadPoolTaskScheduler scheduler) {
        return scheduler;
    }


}
package com.kapp.kappcore.job.service;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.TimeUnit;

public class LocalJobScheduledService implements ScheduledService {
    public LocalJobScheduledService(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.scheduler = threadPoolTaskScheduler;
    }

    private final ThreadPoolTaskScheduler scheduler;


    @Override
    public void schedule(Runnable runnable, long delay, TimeUnit unit) {
        scheduler.getScheduledExecutor().schedule(runnable, delay, unit);
    }
}

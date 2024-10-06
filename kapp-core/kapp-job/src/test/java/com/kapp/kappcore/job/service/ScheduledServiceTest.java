package com.kapp.kappcore.job.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.TimeUnit;

class ScheduledServiceTest {

    private LocalJobScheduledService localJobScheduledService;

    @BeforeEach
    public void setup() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        localJobScheduledService = new LocalJobScheduledService(threadPoolTaskScheduler);
    }

    @Test
    void schedule() {
        localJobScheduledService.schedule(() -> {
            System.out.println(".xxxxx");
        }, 10, TimeUnit.SECONDS);
    }
}
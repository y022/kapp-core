package com.kapp.kappcore.job.service;

import java.util.concurrent.TimeUnit;

public interface ScheduledService {

    void schedule(Runnable runnable, long delay, TimeUnit unit);
}

package com.kapp.kappcore.task.job;

import com.kapp.kappcore.task.support.produce.LineMsProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.quartz.SimpleThreadPoolTaskExecutor;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(value = Scope.Benchmark)
public class TransferDbV1Test {

    private final AsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
    private final LineMsProducer lineMsProducer = new LineMsProducer("/Users/y022/魔天记.txt");
    private final TransferDbV1 transferDbV1 =
            new TransferDbV1(asyncTaskExecutor, lineMsProducer, null, null);

    @BeforeEach
    void setUp() {

    }

    @Test
    @Benchmark
    public void transfer() {
        for (int i = 0; i < 10; i++) {
            transferDbV1.transfer("fr");
        }
    }
}
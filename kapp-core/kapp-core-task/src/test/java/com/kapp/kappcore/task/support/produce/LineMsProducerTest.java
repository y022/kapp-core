package com.kapp.kappcore.task.support.produce;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class LineMsProducerTest {

    @Benchmark
    @Test
    public void prepare() {
        LineMsProducer lineMsProducer = new LineMsProducer("/Users/y022/魔天记.txt");
        lineMsProducer.prepareItem("mtj");

    }
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(LineMsProducerTest.class.getSimpleName()) // 要导入的测试类
                .build();
        new Runner(opt).run(); // 执行测试
    }
}
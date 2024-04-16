package com.kapp.kappcore.task.support;

@FunctionalInterface
public interface ExecutePoint {
    <T> void execute(T t);
}

package com.kapp.kappcore.model.task;

import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
public class TimeDefinition {
    private int interval;
    private TimeUnit unit;
}

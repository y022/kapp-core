package com.kapp.kappcore.task.support.produce;

import com.kapp.kappcore.entity.ExecuteItem;

import java.util.List;

public interface Producer<T extends ExecuteItem> {
    T produce();

    List<T> produce(int capacity);

    default void prepare() {
    }
}

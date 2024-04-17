package com.kapp.kappcore.task.support.produce;

import com.kapp.kappcore.model.entity.ExecuteItem;

import java.util.List;

public interface Producer<T extends ExecuteItem> {
    T produce();

    List<T> produce(int capacity);

    /**
     * 准备所需元素
     */
    default void prepareItem() {
        throw new UnsupportedOperationException();
    }
}

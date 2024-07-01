package com.kapp.kappcore.search.support.builder;

/**
 * Author:Heping
 * Date: 2024/6/25 20:09
 */
public interface SearchFactory<T, R> {
    R create(T t);
}

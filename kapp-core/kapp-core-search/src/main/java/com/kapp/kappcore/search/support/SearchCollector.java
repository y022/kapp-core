package com.kapp.kappcore.search.support;

/**
 * Author:Heping
 * Date: 2024/8/12 14:43
 */
public interface SearchCollector<T,R> {
    R collect(T t);
}

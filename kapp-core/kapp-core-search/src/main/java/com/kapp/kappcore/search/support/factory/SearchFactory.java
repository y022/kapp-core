package com.kapp.kappcore.search.support.factory;

import com.kapp.kappcore.search.support.model.param.SearchMetrics;

/**
 * Author:Heping
 * Date: 2024/6/25 20:09
 */
public interface SearchFactory<T extends SearchMetrics, R> {
    R create(T t);

}

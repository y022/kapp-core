package com.kapp.kappcore.search.core;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.support.Collector;
import com.kapp.kappcore.search.support.model.param.SearchParam;

/**
 * Author:Heping
 * Date: 2024/6/26 15:49
 */
public interface SearchActor {
    <R> R search(SearchParam searchParam, Collector<R> resultCollector) throws SearchException;
}

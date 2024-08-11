package com.kapp.kappcore.search.core.search;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.support.Collector;
import com.kapp.kappcore.search.support.model.param.SearchParam;

import javax.validation.constraints.NotNull;

/**
 * Author:Heping
 * Date: 2024/6/26 15:49
 */
public interface SearchActor {
    <R> R search(SearchParam searchParam, Collector<R> resultCollector) throws SearchException;

    <R> R scrollSearch(@NotNull String scrollId, Collector<R> resultCollector) throws SearchException;
}

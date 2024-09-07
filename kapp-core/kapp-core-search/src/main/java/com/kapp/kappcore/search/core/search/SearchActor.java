package com.kapp.kappcore.search.core.search;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.support.SearchCallBack;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import org.elasticsearch.action.search.SearchResponse;

import javax.validation.constraints.NotNull;

/**
 * Author:Heping
 * Date: 2024/6/26 15:49
 */
public interface SearchActor {
    <R> R search(@NotNull SearchParam searchParam, @NotNull SearchCallBack<SearchResponse, R> callBack) throws SearchException;
    <R> R scroll(@NotNull String scrollId, @NotNull SearchCallBack<SearchResponse, R> callBack) throws SearchException;
}

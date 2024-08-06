package com.kapp.kappcore.search.endpoint;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.common.ExtSearchRequest;
import com.kapp.kappcore.search.common.SearchResult;

/**
 * Author:Heping
 * Date: 2024/7/26 17:33
 */
public interface KappDocSearcher {
    SearchResult<?> search(ExtSearchRequest extSearchRequest) throws SearchException;
    SearchResult<?> scroll(ExtSearchRequest extSearchRequest) throws SearchException;
}

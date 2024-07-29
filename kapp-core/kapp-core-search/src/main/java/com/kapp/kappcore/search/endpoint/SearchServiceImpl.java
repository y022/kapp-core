package com.kapp.kappcore.search.endpoint;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.common.ExtSearchRequest;
import com.kapp.kappcore.search.common.SearchResult;
import com.kapp.kappcore.search.core.search.SearchActor;
import com.kapp.kappcore.search.support.factory.impl.ParamFactory;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import lombok.extern.slf4j.Slf4j;

/**
 * Author:Heping
 * Date: 2024/6/25 17:58
 */
@Slf4j
public class SearchServiceImpl {
    private final SearchActor searchActor;

    public SearchServiceImpl(SearchActor searchActor) {
        this.searchActor = searchActor;
    }

    public SearchResult<?> search(ExtSearchRequest extSearchRequest) throws SearchException {
        SearchParam searchParam = ParamFactory.instance().create(extSearchRequest);
        return searchActor.search(searchParam, response -> new SearchResult<>());
    }
}

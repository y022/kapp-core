package com.kapp.kappcore.search.endpoint;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.common.ExtSearchRequest;
import com.kapp.kappcore.search.common.SearchResult;
import com.kapp.kappcore.search.core.search.SearchActor;
import com.kapp.kappcore.search.support.factory.impl.ParamFactory;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import com.kapp.kappcore.search.support.model.response.SearchBody;
import lombok.extern.slf4j.Slf4j;

/**
 * Author:Heping
 * Date: 2024/6/25 17:58
 */
@Slf4j
public class SearcherServiceImpl implements KappDocSearcher {
    private final ParamFactory paramFactory;
    private final SearchActor searchActor;

    public SearcherServiceImpl(SearchActor searchActor) {
        this.searchActor = searchActor;
        this.paramFactory = ParamFactory.instance();
    }

    public SearchResult<?> search(ExtSearchRequest extSearchRequest) throws SearchException {
        SearchParam searchParam = paramFactory.create(extSearchRequest);
        SearchResult<SearchBody> searchResult = searchActor.search(searchParam, defaultSearchCollector());
        searchResult.pageParam(extSearchRequest.getPageNum(), extSearchRequest.getPageSize());
        searchResult.searchParam(searchParam);
        return searchResult;
    }


}

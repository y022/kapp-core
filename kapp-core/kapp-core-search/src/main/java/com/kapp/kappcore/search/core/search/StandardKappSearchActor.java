package com.kapp.kappcore.search.core.search;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.core.interceptor.SearchInterceptorRegistry;
import com.kapp.kappcore.search.support.SearchCollector;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * Author:Heping
 * Date: 2024/6/26 15:52
 */
public class StandardKappSearchActor extends AbstractKappSearchActor {

    public StandardKappSearchActor(RestHighLevelClient restHighLevelClient, SearchInterceptorRegistry searchInterceptorRegistry) {
        super(restHighLevelClient, searchInterceptorRegistry);
    }

    @Override
    public <R> R search(SearchParam searchParam, SearchCollector<SearchResponse, R> resultCollector) throws SearchException {
        searchParam.startTime();
        SearchRequest searchRequest = searchTemplate(searchParam);
        R r = searchParam.continueScroll()
                ? scroll(searchParam.getScrollId(), resultCollector)
                : doSearch(searchRequest, resultCollector);
        searchParam.endTime();
        return r;
    }

    @Override
    public <R> R scroll(String scrollId, SearchCollector<SearchResponse,R> resultCollector) throws SearchException {
        return scroll(new SearchScrollRequest(scrollId), resultCollector);
    }

}

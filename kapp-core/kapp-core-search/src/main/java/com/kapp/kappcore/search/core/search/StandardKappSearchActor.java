package com.kapp.kappcore.search.core.search;

import com.kapp.kappcore.model.exception.SearchException;
import com.kapp.kappcore.search.core.interceptor.SearchInterceptorRegistry;
import com.kapp.kappcore.search.support.Collector;
import com.kapp.kappcore.search.support.model.param.SearchParam;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;

import java.io.IOException;

/**
 * Author:Heping
 * Date: 2024/6/26 15:52
 */
public class StandardKappSearchActor extends AbstractKappSearchActor {

    public StandardKappSearchActor(RestHighLevelClient restHighLevelClient, SearchInterceptorRegistry searchInterceptorRegistry) {
        super(restHighLevelClient, searchInterceptorRegistry);
    }

    @Override
    public <R> R search(SearchParam searchParam, Collector<R> resultCollector) throws SearchException {
        SearchRequest searchRequest = searchTemplate(searchParam);
        return searchParam.continueScroll()
                ? scrollSearch(searchParam.getScrollId(), resultCollector)
                : doSearch(searchRequest, resultCollector);
    }

    @Override
    public <R> R scrollSearch(String scrollId, Collector<R> resultCollector) throws SearchException {
        SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
        searchScrollRequest.scroll(TimeValue.timeValueMinutes(1));
        try {
            SearchResponse scroll = restHighLevelClient.scroll(searchScrollRequest, RequestOptions.DEFAULT);
            return resultCollector.collect(scroll);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
